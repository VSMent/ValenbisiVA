package es.uv.and.vas.valenbisi;

import android.util.Log;

public class LongLogMessage{
    public static void log(String TAG, String message){
        if (message.length() > 4000) {
            Log.v(TAG, "message.length = " + message.length());
            int chunkCount = message.length() / 4000;     // integer division
            for (int i = 0; i <= chunkCount; i++) {
                int max = 4000 * (i + 1);
                if (max >= message.length()) {
                    Log.v(TAG, "chunk " + i + " of " + chunkCount + ":" + message.substring(4000 * i));
                } else {
                    Log.v(TAG, "chunk " + i + " of " + chunkCount + ":" + message.substring(4000 * i, max));
                }
            }
        }
    }
}