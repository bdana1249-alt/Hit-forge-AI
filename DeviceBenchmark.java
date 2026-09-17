package com.iamlegendz.hitforge.ai;
import android.content.Context;
import android.os.Build;
public final class DeviceBenchmark {
 public static String run(Context c){
  android.app.ActivityManager am=(android.app.ActivityManager)c.getSystemService(Context.ACTIVITY_SERVICE);
  android.app.ActivityManager.MemoryInfo m=new android.app.ActivityManager.MemoryInfo(); am.getMemoryInfo(m);
  long total=m.totalMem/1048576L, avail=m.availMem/1048576L, free=c.getFilesDir().getUsableSpace()/1048576L;
  String abi=Build.SUPPORTED_ABIS.length>0?Build.SUPPORTED_ABIS[0]:"unknown";
  String rec=total<=4096?"Small 1B–3B quantized model; short context":
             total<=6144?"3B–4B quantized model; benchmark context":
             "Larger quantized model may be possible; benchmark first";
  return "RAM "+total+"MB total / "+avail+"MB available\\nStorage "+free+"MB free\\nABI "+abi+"\\nRecommendation: "+rec;
 }
 private DeviceBenchmark(){}
}