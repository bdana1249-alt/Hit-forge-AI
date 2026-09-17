
package com.iamlegendz.hitforge.quality;

import java.util.*;

public final class PersonalBenchmark {
    public static final String[] TESTS={
        "Write a memorable chorus about surviving adversity.",
        "Write an 8-line hook with internal rhyme and a cinematic image.",
        "Rewrite a generic love-song idea into a distinctive concept."
    };
    public static double score(String output){
        if(output==null||output.trim().isEmpty())return 0;
        String[] w=output.trim().split("\\s+");
        double length=Math.min(1,w.length/120.0);
        double punctuation=output.matches("(?s).*[,.!?].*")?.15:0;
        double structure=output.toLowerCase(Locale.US).matches("(?s).*(chorus|verse|hook).*")?.2:0;
        return Math.min(1,.35*length+punctuation+structure+.3);
    }
    private PersonalBenchmark(){}
}
