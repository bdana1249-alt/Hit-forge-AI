package com.iamlegendz.hitforge.ai;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.util.*;

/** Offline long-term memory. Stores only local songwriting preferences and examples. */
public final class MemoryStore {
    private static final String FILE = "hitforge_memory.json";
    private final File file;
    private final ArrayList<Memory> memories = new ArrayList<>();

    public static final class Memory {
        public String type, text, tags;
        public int rating;
        public long time;
        Memory(String type,String text,String tags,int rating,long time){this.type=type;this.text=text;this.tags=tags;this.rating=rating;this.time=time;}
    }
    public MemoryStore(Context c){file=new File(c.getFilesDir(),FILE);load();}
    private void load(){
        memories.clear(); if(!file.exists()) return;
        try(BufferedReader r=new BufferedReader(new FileReader(file))){StringBuilder s=new StringBuilder();String l;while((l=r.readLine())!=null)s.append(l);JSONArray a=new JSONArray(s.toString());
            for(int i=0;i<a.length();i++){JSONObject o=a.getJSONObject(i);memories.add(new Memory(o.optString("type"),o.optString("text"),o.optString("tags"),o.optInt("rating"),o.optLong("time")));}
        }catch(Exception ignored){}
    }
    private void persist(){
        try{JSONArray a=new JSONArray();for(Memory m:memories){JSONObject o=new JSONObject();o.put("type",m.type);o.put("text",m.text);o.put("tags",m.tags);o.put("rating",m.rating);o.put("time",m.time);a.put(o);}try(FileWriter w=new FileWriter(file)){w.write(a.toString());}}catch(Exception ignored){}
    }
    public synchronized void add(String type,String text,String tags,int rating){
        if(text==null||text.trim().isEmpty())return; memories.add(new Memory(type,text,tags==null?"":tags,rating,System.currentTimeMillis()));
        while(memories.size()>250) memories.remove(0); persist();
    }
    public synchronized List<Memory> retrieve(String query,int limit){
        String[] q=normalize(query).split(" "); ArrayList<Scored> scored=new ArrayList<>();
        for(Memory m:memories){String all=normalize(m.text+" "+m.tags);double score=0;for(String t:q)if(t.length()>2&&all.contains(t))score+=1.0;score += Math.max(0,m.rating)*0.35; if(m.type.equals("accepted"))score+=0.5; if(score>0)scored.add(new Scored(m,score));}
        Collections.sort(scored,(a,b)->Double.compare(b.score,a.score)); ArrayList<Memory> out=new ArrayList<>();for(int i=0;i<Math.min(limit,scored.size());i++)out.add(scored.get(i).m);return out;
    }
    private static String normalize(String x){return x.toLowerCase(Locale.US).replaceAll("[^a-z0-9 ]"," ").replaceAll("\\s+"," ").trim();}
    private static class Scored{Memory m;double score;Scored(Memory m,double s){this.m=m;score=s;}}
}
