package com.iamlegendz.hitforge.install;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.core.content.FileProvider;
import java.io.File;

/**
 * Launches the Android system package installer for a user-selected APK.
 * Android may require user confirmation; ordinary apps cannot silently bypass it.
 */
public final class ApkInstaller {
  private ApkInstaller() {}

  public static void openSystemInstaller(Context context, File apk) {
    if (apk == null || !apk.isFile()) throw new IllegalArgumentException("APK not found");
    Uri uri = FileProvider.getUriForFile(
        context,
        context.getPackageName() + ".fileprovider",
        apk);
    Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
    intent.setData(uri);
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    intent.putExtra(Intent.EXTRA_RETURN_RESULT, true);
    context.startActivity(intent);
  }
}
