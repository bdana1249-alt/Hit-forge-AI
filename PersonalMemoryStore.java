
package com.iamlegendz.hitforge.memory;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.util.*;

public final class PersonalMemoryStore {
    private final Context c;
    private final File dir;
    public PersonalMemoryStore(Context c) {
        this.c = c.getApplicationContext();
        dir = new File(this.c.getFilesDir(), "memory");
        if (!dir.exists()) dir.mkdirs();
    }

    private File file(String name) { return new File(dir, name); }

    private JSONArray load(String name) {
        File f=file(name);
        if(!f.exists()) return new JSONArray();
        try {
            byte[] b=java.nio.file.Files.readAllBytes(f.toPath());
            return new JSONArray(new String(b));
        } catch(Exception e) { return new JSONArray(); }
    }

    private void save(String name, JSONArray a) {
        try(FileOutputStream out=new FileOutputStream(file(name))) {
            out.write(a.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8));
        } catch(Exception ignored) {}
    }

    public synchronized void addExample(String kind, String prompt, String output, String note) {
        JSONArray a=load("examples.json");
        JSONObject x=new JSONObject();
        try {
            x.put("id", UUID.randomUUID().toString());
            x.put("kind", kind);
            x.put("prompt", prompt==null?"":prompt);
            x.put("output", output==null?"":output);
            x.put("note", note==null?"":note);
            x.put("time", System.currentTimeMillis());
            a.put(x); save("examples.json", a);
        } catch(Exception ignored) {}
    }

    public synchronized void teach(String instruction, int strength) {
        JSONArray a=load("preferences.json");
        JSONObject x=new JSONObject();
        try {
            x.put("instruction", instruction);
            x.put("strength", Math.max(1, Math.min(10, strength)));
            x.put("time", System.currentTimeMillis());
            a.put(x); save("preferences.json", a);
        } catch(Exception ignored) {}
    }

    public synchronized String retrieve(String query, int limit) {
        JSONArray a=load("examples.json");
        String q=(query==null?"":query).toLowerCase();
        ArrayList<String> hits=new ArrayList<>();
        for(int i=a.length()-1;i>=0 && hits.size()<limit;i--) {
            try {
                JSONObject x=a.getJSONObject(i);
                String blob=x.toString().toLowerCase();
                if(q.isEmpty() || blob.contains(q.split("\\s+")[0])) hits.add(x.toString());
            } catch(Exception ignored) {}
        }
        return String.join("\n", hits);
    }

    public synchronized String preferenceContext() {
        JSONArray a=load("preferences.json");
        ArrayList<String> p=new ArrayList<>();
        for(int i=a.length()-1;i>=0 && p.size()<20;i--) {
            try { p.add(a.getJSONObject(i).toString()); } catch(Exception ignored) {}
        }
        return String.join("\n", p);
    }

    public synchronized org.json.JSONArray approvedExamples() {
        org.json.JSONArray src = load("examples.json");
        org.json.JSONArray out = new org.json.JSONArray();
        for (int i = 0; i < src.length(); i++) {
            try {
                org.json.JSONObject x = src.getJSONObject(i);
                if ("accepted".equals(x.optString("kind"))) out.put(x);
            } catch (Exception ignored) {}
        }
        return out;
    }

    public synchronized int exampleCount() { return load("examples.json").length(); }
    public synchronized int preferenceCount() { return load("preferences.json").length(); }

    public synchronized void clearAll() {
        for(String n:new String[]{"examples.json","preferences.json"}) {
            File f=file(n); if(f.exists()) f.delete();
        }
    }
}
