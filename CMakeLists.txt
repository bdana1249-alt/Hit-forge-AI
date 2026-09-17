
cmake_minimum_required(VERSION 3.22.1)
project(hitforge_llm)

if(NOT EXISTS "${CMAKE_CURRENT_SOURCE_DIR}/llama.cpp/include/llama.h")
    message(FATAL_ERROR "llama.cpp not present. Run tools/fetch_llama_cpp.sh before building.")
endif()

set(GGML_NATIVE OFF CACHE BOOL "" FORCE)
set(GGML_OPENMP OFF CACHE BOOL "" FORCE)
set(GGML_LLAMAFILE OFF CACHE BOOL "" FORCE)
set(LLAMA_BUILD_TESTS OFF CACHE BOOL "" FORCE)
set(LLAMA_BUILD_EXAMPLES OFF CACHE BOOL "" FORCE)
set(LLAMA_BUILD_SERVER OFF CACHE BOOL "" FORCE)
set(LLAMA_OPENSSL OFF CACHE BOOL "" FORCE)

add_subdirectory(llama.cpp llama-build EXCLUDE_FROM_ALL)

add_library(hitforge_llm SHARED hitforge_llm.cpp)
target_include_directories(hitforge_llm PRIVATE
    llama.cpp/include
    llama.cpp/ggml/include
)
target_link_libraries(hitforge_llm PRIVATE llama)
