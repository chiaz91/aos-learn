package aos.learn.notify_user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;


public class InfoActivity extends AppCompatActivity {
    private static final String TAG = "cy.act.info";
    TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        // init view
        tvInfo = findViewById(R.id.tv_info);

        // check data
        Bundle data = getIntent().getExtras();
        if (data != null){
            StringBuilder sb = new StringBuilder("intent.data {\n");
            for (String key: data.keySet() ) {
                sb.append( key+": "+data.get(key).toString()+"\n");
            }
            sb.append("}");
            tvInfo.setText(sb.toString());
        }
    }
}