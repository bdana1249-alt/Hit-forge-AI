
package com.iamlegendz.hitforge.library;

import android.content.Context;
import org.json.*;
import java.io.*;
import java.util.*;

public final class SongLibrary {
    private final Context c;
    private final File file;
    public SongLibrary(Context c){this.c=c.getApplicationContext();file=new File(this.c.getFilesDir(),"song_library.json");}

    private JSONArray load(){
        if(!file.exists())return new JSONArray();
        try{return new JSONArray(new String(java.nio.file.Files.readAllBytes(file.toPath())));}
        catch(Exception e){return new JSONArray();}
    }
    private void save(JSONArray a){try(FileOutputStream o=new FileOutputStream(file)){o.write(a.toString().getBytes());}catch(Exception ignored){}}

    public synchronized String saveSong(String title,String idea,String text,String mode,String content){
        String id=UUID.randomUUID().toString();
        JSONObject x=new JSONObject();
        try{
            x.put("id",id);x.put("title",title);x.put("idea",idea);x.put("text",text);
            x.put("mode",mode);x.put("contentMode",content);x.put("time",System.currentTimeMillis());
            JSONArray a=load();a.put(x);save(a);
        }catch(Exception ignored){}
        return id;
    }
    public synchronized JSONArray all(){return load();}
    public synchronized int count(){return load().length();}
    public synchronized void delete(String id){
        JSONArray a=load(),b=new JSONArray();
        for(int i=0;i<a.length();i++)try{if(!id.equals(a.getJSONObject(i).optString("id")))b.put(a.getJSONObject(i));}catch(Exception ignored){}
        save(b);
    }
    public synchronized void clear(){if(file.exists())file.delete();}
}
