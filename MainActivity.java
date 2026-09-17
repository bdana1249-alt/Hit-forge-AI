package com.iamlegendz.hitforge;
import android.app.Activity;import android.os.Bundle;import android.content.Intent;import com.iamlegendz.hitforge.studio.HitforgeStudioActivity;
public final class MainActivity extends Activity { @Override public void onCreate(Bundle b){super.onCreate(b);startActivity(new Intent(this,HitforgeStudioActivity.class));finish();} }
