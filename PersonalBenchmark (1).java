
package com.iamlegendz.hitforge.ai;

import java.util.*;

public final class PersonalBenchmark {
    public static final class Result {
        public final long elapsedMs;
        public final int chars;
        public final double charsPerSecond;
        public Result(long e,int c){elapsedMs=e;chars=c;charsPerSecond=e>0?(c*1000.0/e):0;}
        @Override public String toString(){return "Latency: "+elapsedMs+" ms | Output: "+chars+
                " chars | Throughput: "+String.format(java.util.Locale.US,"%.1f",charsPerSecond)+" chars/sec";}
    }

    public static Result run(String model,String prompt,int maxTokens,int context,float temp,int seed){
        long start=System.currentTimeMillis();
        String out=LocalLlmBridge.nativeGenerate(model,prompt,maxTokens,context,temp,seed);
        return new Result(System.currentTimeMillis()-start,out==null?0:out.length());
    }
    private PersonalBenchmark(){}
}
