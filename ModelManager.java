package com.iamlegendz.hitforge.model;

import android.content.Context;
import android.net.Uri;
import java.io.*;

/** Offline model registry. Files are copied into app-private storage and never downloaded by the app. */
public final class ModelManager {
  private static final String PREF="hitforge_models";
  private static final String ACTIVE="active_model";
  private ModelManager() {}

  public static File modelDir(Context c){
    File d=new File(c.getFilesDir(),"models");
    if(!d.exists() && !d.mkdirs()) throw new IllegalStateException("Unable to create model directory");
    return d;
  }

  public static String[] installed(Context c){
    File[] files=modelDir(c).listFiles();
    if(files==null)return new String[0];
    java.util.ArrayList<String> out=new java.util.ArrayList<>();
    for(File f:files) if(f.isFile()&&f.getName().toLowerCase().endsWith(".gguf"))out.add(f.getName());
    java.util.Collections.sort(out);
    return out.toArray(new String[0]);
  }

  public static File importUri(Context c, Uri uri, String displayName) throws IOException {
    String safe=displayName==null?"model.gguf":displayName.replaceAll("[^A-Za-z0-9._-]","_");
    if(!safe.toLowerCase().endsWith(".gguf")) safe += ".gguf";
    File dir=modelDir(c);
    File out=new File(dir,safe);
    File tmp=new File(dir,safe+".partial");

    try(InputStream in=c.getContentResolver().openInputStream(uri)){
      if(in==null)throw new IOException("Unable to open selected file");
      try(BufferedInputStream bin=new BufferedInputStream(in);
          FileOutputStream fos=new FileOutputStream(tmp)){
        byte[] magic=new byte[4];
        int got=0;
        while(got<4){
          int n=bin.read(magic,got,4-got);
          if(n<0)break;
          got+=n;
        }
        if(got!=4 || magic[0]!='G' || magic[1]!='G' || magic[2]!='U' || magic[3]!='F'){
          throw new IOException("Selected file is not a valid GGUF model.");
        }
        fos.write(magic);
        byte[] b=new byte[1024*1024];
        int n;
        while((n=bin.read(b))!=-1) fos.write(b,0,n);
        fos.getFD().sync();
      }
    }

    if(!tmp.isFile() || tmp.length()<1024L*1024L){
      if(tmp.exists()) tmp.delete();
      throw new IOException("Model import was incomplete.");
    }
    if(out.exists() && !out.delete()){
      tmp.delete();
      throw new IOException("Could not replace existing model.");
    }
    if(!tmp.renameTo(out)){
      tmp.delete();
      throw new IOException("Could not finalize model import.");
    }
    return out;
  }

  public static void setActive(Context c, File f){
    c.getSharedPreferences(PREF,Context.MODE_PRIVATE).edit()
      .putString(ACTIVE,f==null?"":f.getAbsolutePath()).apply();
  }

  public static File active(Context c){
    String p=c.getSharedPreferences(PREF,Context.MODE_PRIVATE).getString(ACTIVE,"");
    if(p.isEmpty())return null;
    File f=new File(p);
    return f.isFile()?f:null;
  }
}
