package org.githubissue.ext;

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created on 9/21/2018.
 */
public class SLog {

    public static void i(@NonNull final String pTag, @NonNull final Object... pObjects) {
        StringBuilder sb = getString(pObjects);
        Log.i(pTag, sb.toString());
    }

    public static void d(@NonNull final String pTag, @NonNull final Object... pObjects) {
        StringBuilder sb = getString(pObjects);
        Log.d(pTag, sb.toString());
    }

    public static void e(@NonNull final String pTag, @NonNull final Object... pObjects) {
        StringBuilder sb = getString(pObjects);
        Log.e(pTag, sb.toString());
    }

    private static StringBuilder getString(@NonNull Object... pObjects) {
        StringBuilder sb = new StringBuilder();
        if (pObjects != null) {
            for (Object object : pObjects) {
                if (object != null) {
                    sb.append(object.toString());
                }
            }
        }
        return sb;
    }

}
