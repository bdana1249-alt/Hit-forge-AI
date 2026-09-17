
package com.iamlegendz.hitforge.quality;

import java.util.*;
import java.util.regex.*;

public final class HookAnalyzer {
    public static final class Report {
        public int words, uniqueWords, lines, repeatedWords, questionMarks, exclamations;
        public double repetition, concision, imagery, hookPotential;
        public String advice;
        @Override public String toString(){
            return "Hook analysis\n"+
                "Words: "+words+" | Unique: "+uniqueWords+" | Lines: "+lines+"\n"+
                "Repetition: "+pct(repetition)+" | Concision: "+pct(concision)+
                " | Imagery: "+pct(imagery)+" | Hook potential: "+pct(hookPotential)+
                "\nAdvice: "+advice;
        }
        private String pct(double x){return String.format(Locale.US,"%.0f%%",x*100);}
    }

    public static Report analyze(String hook){
        Report r=new Report();
        String h=hook==null?"":hook.trim();
        if(h.isEmpty()){r.advice="Write a clear central hook.";return r;}
        String[] lines=h.split("\\R+");
        r.lines=lines.length;
        String[] words=h.toLowerCase(Locale.US).split("[^a-z0-9']+");
        Map<String,Integer> counts=new HashMap<>();
        for(String w:words)if(!w.isEmpty())counts.put(w,counts.getOrDefault(w,0)+1);
        r.words=counts.values().stream().mapToInt(Integer::intValue).sum();
        r.uniqueWords=counts.size();
        int reps=0; for(int n:counts.values()) if(n>1) reps+=n-1;
        r.repeatedWords=reps;
        r.repetition=r.words==0?0:Math.min(1,reps/(double)Math.max(1,r.words/2));
        r.concision=Math.max(0,1-Math.max(0,r.words-80)/120.0);
        String[] imagery={"fire","rain","blood","night","light","shadow","road","smoke","heart","sky","ghost","neon","dust","storm","river","home"};
        int hits=0; for(String i:imagery) if(h.contains(i))hits++;
        r.imagery=Math.min(1,hits/4.0);
        r.hookPotential=Math.min(1,.35*r.repetition+.25*r.concision+.25*r.imagery+.15*(r.lines<=8?1:0));
        r.advice = r.hookPotential>.72 ? "Strong foundation. Test the first line and final phrase for instant recall."
                : "Strengthen one unforgettable phrase, reduce filler, and repeat the central idea strategically.";
        return r;
    }
    private HookAnalyzer(){}
}
