package aos.learn.data_passing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class MainActivity extends InfoActivity {
    private static final int REQ_USER_INPUT = 1001;
    Button btnInput1, btnInput2;
    TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // init view;
        btnInput1 = findViewById(R.id.btn_to_input);
        btnInput2 = findViewById(R.id.btn_to_input2);
        tvInfo = findViewById(R.id.tv_info);

        // set up listener
        btnInput1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvInfo.setText("start activity");
                // open new activity
                Intent toInput = new Intent(MainActivity.this, InputActivity.class);

                // add optional data
                toInput.putExtra("activate_by", "button1");
                startActivity(toInput);

            }
        });

        btnInput2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvInfo.setText("start activity for result");
                // open new activity
                Intent toInput = new Intent(MainActivity.this, InputActivity.class);
                // add optional data
                toInput.putExtra("activate_by", "button2");
                startActivityForResult(toInput, REQ_USER_INPUT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQ_USER_INPUT:
                if (resultCode == Activity.RESULT_OK){
                    if (data.getExtras()!=null){
                        StringBuilder strBuilder = new StringBuilder("received data:\n");
                        for (String key:data.getExtras().keySet()) {
                            strBuilder.append(key+": "+data.getExtras().get(key).toString()+"\n");
                        }
                        tvInfo.setText(strBuilder.toString());
                    } else {
                        tvInfo.setText("did not receive data");
                    }
                } else {
                    tvInfo.setText("result not ok");
                }
                break;
        }

    }
}