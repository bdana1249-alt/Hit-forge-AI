
package com.iamlegendz.hitforge.adapters;

import java.util.*;

public final class TrainingPlan {
    public final List<String> examples=new ArrayList<>();
    public String adapterName="IAML3G3NDZ-Personal-v1";
    public int rank=8;
    public float alpha=16f;
    public int epochs=2;
    public boolean requireApproval=true;
    public String toJsonLike(){
        return "{adapterName:"+adapterName+", examples:"+examples.size()+", rank:"+rank+
               ", alpha:"+alpha+", epochs:"+epochs+", approvalRequired:"+requireApproval+"}";
    }
}
