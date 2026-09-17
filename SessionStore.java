
package com.iamlegendz.hitforge.memory;

import android.content.Context;
import org.json.*;
import java.io.*;

public final class SessionStore {
    private final Context c;
    public SessionStore(Context c){this.c=c.getApplicationContext();}

    public void save(String title,String prompt,String result,String mode,String contentMode){
        try {
            File f=new File(c.getFilesDir(),"sessions.json");
            JSONArray a=f.exists()?new JSONArray(new String(java.nio.file.Files.readAllBytes(f.toPath()))):new JSONArray();
            JSONObject x=new JSONObject();
            x.put("title",title==null?"":title);
            x.put("prompt",prompt==null?"":prompt);
            x.put("result",result==null?"":result);
            x.put("mode",mode==null?"":mode);
            x.put("contentMode",contentMode==null?"":contentMode);
            x.put("time",System.currentTimeMillis());
            a.put(x);
            try(FileOutputStream o=new FileOutputStream(f)){o.write(a.toString().getBytes());}
        }catch(Exception ignored){}
    }
}
