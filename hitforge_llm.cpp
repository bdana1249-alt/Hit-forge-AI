#include <jni.h>
#include <string>
#include <vector>
#include <mutex>
#include <algorithm>
#include <thread>
#include "llama.h"

static std::once_flag g_init;
static void init_backend() { std::call_once(g_init, [](){ llama_backend_init(); }); }
static std::string jstr(JNIEnv* env, jstring s) {
    if (!s) return {};
    const char* p = env->GetStringUTFChars(s, nullptr);
    std::string out = p ? p : "";
    env->ReleaseStringUTFChars(s, p);
    return out;
}
static void emit(JNIEnv* env, jobject listener, jmethodID mid, const std::string& s) {
    if (!listener || !mid || s.empty()) return;
    jstring js = env->NewStringUTF(s.c_str());
    env->CallVoidMethod(listener, mid, js);
    env->DeleteLocalRef(js);
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_iamlegendz_hitforge_ai_LocalLlmBridge_nativeRuntimeInfo(JNIEnv* env, jclass) {
    init_backend();
    return env->NewStringUTF(llama_print_system_info());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_iamlegendz_hitforge_ai_LocalLlmBridge_nativeModelInfo(JNIEnv* env, jclass, jstring path) {
    init_backend();
    std::string model_path = jstr(env, path);
    llama_model_params mp = llama_model_default_params();
    mp.use_mmap = true;
    llama_model* model = llama_model_load_from_file(model_path.c_str(), mp);
    if (!model) return env->NewStringUTF("MODEL_LOAD_FAILED");
    char desc[512] = {};
    llama_model_desc(model, desc, sizeof(desc));
    std::string out = std::string(desc)
        + "\nparams=" + std::to_string(llama_model_n_params(model))
        + "\nsize_bytes=" + std::to_string(llama_model_size(model))
        + "\ntrain_context=" + std::to_string(llama_model_n_ctx_train(model));
    llama_model_free(model);
    return env->NewStringUTF(out.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_iamlegendz_hitforge_ai_LocalLlmBridge_nativeAdapterInfo(JNIEnv* env, jclass, jstring modelPath, jstring adapterPath) {
    init_backend();
    std::string model_path = jstr(env, modelPath);
    std::string adapter_path = jstr(env, adapterPath);
    llama_model_params mp = llama_model_default_params();
    mp.use_mmap = true;
    llama_model* model = llama_model_load_from_file(model_path.c_str(), mp);
    if (!model) return env->NewStringUTF("MODEL_LOAD_FAILED");
    llama_adapter_lora* adapter = llama_adapter_lora_init(model, adapter_path.c_str());
    if (!adapter) {
        llama_model_free(model);
        return env->NewStringUTF("ADAPTER_LOAD_FAILED");
    }
    char buf[256] = {};
    std::string out = "adapter_loaded";
    if (llama_adapter_meta_val_str(adapter, "adapter.type", buf, sizeof(buf)) >= 0)
        out += "\ntype=" + std::string(buf);
    if (llama_adapter_meta_val_str(adapter, "adapter.lora.task_name", buf, sizeof(buf)) >= 0)
        out += "\ntask=" + std::string(buf);
    llama_adapter_lora_free(adapter);
    llama_model_free(model);
    return env->NewStringUTF(out.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_iamlegendz_hitforge_ai_LocalLlmBridge_nativeGenerate(
        JNIEnv* env, jclass clazz, jstring path, jstring prompt,
        jint max_tokens, jint context_size, jfloat temperature, jint seed) {
    return Java_com_iamlegendz_hitforge_ai_LocalLlmBridge_nativeGenerateStreaming(
        env, clazz, path, nullptr, prompt, max_tokens, context_size, temperature, seed, nullptr);
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_iamlegendz_hitforge_ai_LocalLlmBridge_nativeGenerateStreaming(
        JNIEnv* env, jclass, jstring path, jstring adapterPath, jstring prompt,
        jint max_tokens, jint context_size, jfloat temperature, jint seed, jobject listener) {
    init_backend();
    std::string model_path = jstr(env, path);
    std::string adapter_path = jstr(env, adapterPath);
    std::string text_prompt = jstr(env, prompt);

    jmethodID mid = nullptr;
    if (listener) {
        jclass cls = env->GetObjectClass(listener);
        mid = env->GetMethodID(cls, "onToken", "(Ljava/lang/String;)V");
        env->DeleteLocalRef(cls);
    }

    llama_model_params mp = llama_model_default_params();
    mp.use_mmap = true;
    llama_model* model = llama_model_load_from_file(model_path.c_str(), mp);
    if (!model) return env->NewStringUTF("[HITFORGE] Unable to load GGUF model.");

    llama_adapter_lora* adapter = nullptr;
    if (!adapter_path.empty()) {
        adapter = llama_adapter_lora_init(model, adapter_path.c_str());
        if (!adapter) {
            llama_model_free(model);
            return env->NewStringUTF("[HITFORGE] LoRA adapter could not be loaded. Check that it matches the base model architecture.");
        }
    }

    const llama_vocab* vocab = llama_model_get_vocab(model);
    int32_t n_prompt = -llama_tokenize(vocab, text_prompt.c_str(), (int32_t) text_prompt.size(), nullptr, 0, true, true);
    if (n_prompt <= 0) {
        if (adapter) llama_adapter_lora_free(adapter);
        llama_model_free(model);
        return env->NewStringUTF("[HITFORGE] Prompt tokenization failed.");
    }
    std::vector<llama_token> prompt_tokens(n_prompt);
    if (llama_tokenize(vocab, text_prompt.c_str(), (int32_t) text_prompt.size(), prompt_tokens.data(), n_prompt, true, true) < 0) {
        if (adapter) llama_adapter_lora_free(adapter);
        llama_model_free(model);
        return env->NewStringUTF("[HITFORGE] Prompt tokenization failed.");
    }

    int requested_ctx = std::max(512, std::min((int) context_size, 8192));
    int max_new = std::max(1, std::min((int) max_tokens, 2048));
    int train_ctx = (int) llama_model_n_ctx_train(model);
    int n_ctx = std::min(requested_ctx, train_ctx > 0 ? train_ctx : requested_ctx);

    // Never allow a prompt + generation request to exceed the allocated KV cache.
    // On lower-memory phones this turns an otherwise fatal decode into a clean error.
    if (n_prompt + 32 >= n_ctx) {
        llama_model_free(model);
        return env->NewStringUTF("[HITFORGE] Prompt is too large for the selected context. Reduce the prompt or increase context.");
    }
    max_new = std::min(max_new, n_ctx - n_prompt - 32);
    max_new = std::max(1, max_new);

    llama_context_params cp = llama_context_default_params();
    cp.n_ctx = n_ctx;
    cp.n_batch = std::min(n_ctx, 256);
    cp.n_ubatch = std::min(cp.n_batch, 256);
    // Avoid creating an excessive native thread count on low/mid-range phones.
    cp.n_threads = std::max(1u, std::min(6u, std::thread::hardware_concurrency()));
    cp.n_threads_batch = cp.n_threads;

    llama_context* ctx = llama_init_from_model(model, cp);
    if (!ctx) {
        if (adapter) llama_adapter_lora_free(adapter);
        llama_model_free(model);
        return env->NewStringUTF("[HITFORGE] Context creation failed; reduce context size.");
    }

    if (adapter) {
        llama_adapter_lora* arr[1] = { adapter };
        float scales[1] = { 1.0f };
        if (llama_set_adapters_lora(ctx, arr, 1, scales) != 0) {
            llama_free(ctx);
            llama_adapter_lora_free(adapter);
            llama_model_free(model);
            return env->NewStringUTF("[HITFORGE] LoRA adapter could not be applied.");
        }
    }

    llama_sampler_chain_params sp = llama_sampler_chain_default_params();
    llama_sampler* sampler = llama_sampler_chain_init(sp);
    if (!sampler) {
        llama_free(ctx);
        if (adapter) llama_adapter_lora_free(adapter);
        llama_model_free(model);
        return env->NewStringUTF("[HITFORGE] Sampler initialization failed.");
    }

    float temp = (float) temperature;
    if (temp <= 0.01f) {
        llama_sampler_chain_add(sampler, llama_sampler_init_greedy());
    } else {
        llama_sampler_chain_add(sampler, llama_sampler_init_top_k(40));
        llama_sampler_chain_add(sampler, llama_sampler_init_top_p(0.95f, 1));
        llama_sampler_chain_add(sampler, llama_sampler_init_min_p(0.05f, 1));
        llama_sampler_chain_add(sampler, llama_sampler_init_temp(temp));
        llama_sampler_chain_add(sampler, llama_sampler_init_dist((uint32_t) seed));
    }

    llama_batch batch = llama_batch_get_one(prompt_tokens.data(), (int32_t) prompt_tokens.size());
    std::string output;
    if (llama_decode(ctx, batch) != 0) {
        llama_sampler_free(sampler);
        llama_free(ctx);
        if (adapter) llama_adapter_lora_free(adapter);
        llama_model_free(model);
        return env->NewStringUTF("[HITFORGE] Prompt decode failed; model/context may be too large.");
    }

    for (int i = 0; i < max_new; ++i) {
        llama_token tok = llama_sampler_sample(sampler, ctx, -1);
        llama_sampler_accept(sampler, tok);
        if (llama_vocab_is_eog(vocab, tok)) break;
        char piece[1024];
        int n = llama_token_to_piece(vocab, tok, piece, sizeof(piece), 0, true);
        if (n > 0) {
            std::string part(piece, n);
            output += part;
            emit(env, listener, mid, part);
            if (env->ExceptionCheck()) env->ExceptionClear();
        }
        batch = llama_batch_get_one(&tok, 1);
        if (llama_decode(ctx, batch) != 0) break;
    }

    llama_sampler_free(sampler);
    llama_free(ctx);
    if (adapter) llama_adapter_lora_free(adapter);
    llama_model_free(model);
    return env->NewStringUTF(output.c_str());
}
