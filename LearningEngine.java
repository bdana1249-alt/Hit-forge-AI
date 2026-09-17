package com.iamlegendz.hitforge.ai;

import android.content.Context;
import java.util.*;

/**
 * Continual personalization without destructive per-session weight updates.
 * It learns through retrieval memory, explicit feedback, and accepted edits.
 */
public final class LearningEngine {
    private final MemoryStore memory;
    public LearningEngine(Context c){memory=new MemoryStore(c);}
    public void recordGeneration(String concept,String mode,String output){memory.add("generation",output,concept+" "+mode,0);}
    public void teach(String note,String tags,int rating){memory.add("preference",note,tags,rating);}
    public void accept(String song,String tags){memory.add("accepted",song,tags,5);}
    public void reject(String song,String reason){memory.add("rejected",reason,song,0);}
    public String context(String query){
        List<MemoryStore.Memory> ms=memory.retrieve(query,6);if(ms.isEmpty())return "";StringBuilder b=new StringBuilder("\nPERSONALIZATION MEMORY — use as guidance, not as facts:\n");
        for(MemoryStore.Memory m:ms){b.append("- ").append(m.type).append(": ").append(trim(m.text,900)).append("\n");}return b.toString();
    }
    public int count(){return memory.retrieve("",9999).size();}
    private String trim(String s,int n){return s.length()>n?s.substring(0,n):s;}
}
