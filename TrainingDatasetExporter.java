
package com.iamlegendz.hitforge.export;

import android.content.Context;
import org.json.*;
import java.io.*;

public final class TrainingDatasetExporter {
    public static File exportApproved(Context c, JSONArray examples) throws IOException {
        File out=new File(c.getExternalFilesDir(null),"hitforge_training_dataset.jsonl");
        try(FileOutputStream f=new FileOutputStream(out)){
            for(int i=0;i<examples.length();i++){
                JSONObject x=examples.optJSONObject(i);
                if(x==null || !"accepted".equals(x.optString("kind")))continue;
                JSONObject row=new JSONObject();
                row.put("instruction",x.optString("prompt"));
                row.put("output",x.optString("output"));
                f.write((row.toString()+"\n").getBytes());
            }
        }
        return out;
    }
}
