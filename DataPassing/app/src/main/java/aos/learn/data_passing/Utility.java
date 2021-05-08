package aos.learn.data_passing;

import android.os.Bundle;
import android.util.Log;

public class Utility {
    public static void logBundle(String TAG, Bundle bundle){
        Log.d(TAG, "{");
        for (String key: bundle.keySet() ) {
            Log.d(TAG, String.format("%s: %s", key, bundle.get(key).toString() ));
        }
        Log.d(TAG, "}");
    }
}
