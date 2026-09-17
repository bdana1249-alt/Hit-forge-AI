package com.iamlegendz.hitforge.adapters;

import android.content.Context;
import org.json.*;
import java.io.*;

/** Local, versioned registry for GGUF LoRA adapters. */
public final class AdapterRegistry {
    private final Context c;
    private final File file;
    public AdapterRegistry(Context c){ this.c=c.getApplicationContext(); file=new File(this.c.getFilesDir(),"adapters.json"); }
    private JSONArray load(){ if(!file.exists()) return new JSONArray(); try{return new JSONArray(new String(java.nio.file.Files.readAllBytes(file.toPath()), java.nio.charset.StandardCharsets.UTF_8));}catch(Exception e){return new JSONArray();} }
    private void save(JSONArray a){ try(FileOutputStream o=new FileOutputStream(file)){o.write(a.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8));}catch(Exception ignored){} }
    public synchronized void register(String name,String path,String baseModel,String benchmark,String status){ JSONArray a=load(); JSONObject x=new JSONObject(); try{x.put("name",name);x.put("path",path);x.put("baseModel",baseModel);x.put("benchmark",benchmark);x.put("status",status);x.put("active",false);x.put("time",System.currentTimeMillis());a.put(x);save(a);}catch(Exception ignored){} }
    public synchronized JSONArray all(){return load();}
    public synchronized String activePath(){ JSONArray a=load(); for(int i=0;i<a.length();i++) try{JSONObject x=a.getJSONObject(i); if(x.optBoolean("active",false)) return x.optString("path","");}catch(Exception ignored){} return ""; }
    public synchronized String activeName(){ JSONArray a=load(); for(int i=0;i<a.length();i++) try{JSONObject x=a.getJSONObject(i); if(x.optBoolean("active",false)) return x.optString("name","");}catch(Exception ignored){} return ""; }
    public synchronized boolean setActive(String name){ JSONArray a=load(); boolean found=false; for(int i=0;i<a.length();i++)try{JSONObject x=a.getJSONObject(i); boolean on=name.equals(x.optString("name")); x.put("active",on); if(on)found=true;}catch(Exception ignored){} save(a); return found; }
    public synchronized boolean remove(String name){ JSONArray a=load(); JSONArray out=new JSONArray(); boolean removed=false; for(int i=0;i<a.length();i++)try{JSONObject x=a.getJSONObject(i); if(name.equals(x.optString("name"))){removed=true;continue;} out.put(x);}catch(Exception ignored){} save(out); return removed; }
}
