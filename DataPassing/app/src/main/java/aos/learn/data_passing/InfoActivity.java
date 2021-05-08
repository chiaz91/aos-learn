package aos.learn.data_passing;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class InfoActivity extends AppCompatActivity {
    private static final String TAG = "cy.act.info";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, String.format("%s.%s",this.getClass().getSimpleName(), "onCreate"));
        if (getIntent().getExtras() != null){
            Log.i(TAG, String.format("%s.%s",this.getClass().getSimpleName(), "onCreate::has data"));
            Utility.logBundle(TAG, getIntent().getExtras());
        } else {
            Log.i(TAG, String.format("%s.%s",this.getClass().getSimpleName(), "onCreate::no data"));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, String.format("%s.%s",this.getClass().getSimpleName(), "onStart"));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, String.format("%s.%s",this.getClass().getSimpleName(), "onRestart"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, String.format("%s.%s",this.getClass().getSimpleName(), "onResume"));
    }

    @Override
    protected void onPause() {
        Log.i(TAG, String.format("%s.%s",this.getClass().getSimpleName(), "onPause"));
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, String.format("%s.%s",this.getClass().getSimpleName(), "onStop"));
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, String.format("%s.%s",this.getClass().getSimpleName(), "onDestroy"));
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, String.format("%s.%s",this.getClass().getSimpleName(), "onActivityResult"));
        if (data!=null){
            Log.i(TAG, String.format("%s.%s",this.getClass().getSimpleName(), "onActivityResult::has data"));
            if (data.getExtras() != null){
                Utility.logBundle(TAG, data.getExtras());
            }
        } else {
            Log.i(TAG, String.format("%s.%s",this.getClass().getSimpleName(), "onActivityResult::no data"));
        }

    }
}
