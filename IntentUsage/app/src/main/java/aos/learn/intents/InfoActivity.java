package aos.learn.intents;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class InfoActivity extends AppCompatActivity {
    private static final String TAG = "cy.act.info";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.i(TAG, "onActivityResult");
        if (data!=null){
            Log.i(TAG, data.toString());
            if (data.getExtras()!=null){
                Bundle bundle = data.getExtras();
                Log.i(TAG, "data.extras {");
                for(String key: bundle.keySet())
                    Log.i(TAG, key+": "+bundle.get(key).toString());
                Log.i(TAG, "}");
            }
            if (data.getData()!=null){
                Log.i(TAG, "data.data {");
                Log.i(TAG, "uri.toString: "+data.getData());
                Log.i(TAG, "uri.getPath: "+data.getData().getPath());
                Log.i(TAG, "}");
            }
            if (data.getClipData()!=null){
                ClipData clipData = data.getClipData();
                Log.i(TAG, "data.getClipData: "+clipData);
                Log.i(TAG, "data.getClipData.items {");
                for (int i = 0; i <clipData.getItemCount() ; i++) {
                    Log.i(TAG, clipData.getItemAt(i).toString());
                }
                Log.i(TAG, "}");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
