package aos.learn.data_passing;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;

public class InputActivity extends InfoActivity {
    private static final String TAG = "cy.act.input";
    EditText etName;
    CheckBox cbCitizen;
    RadioGroup rgGender;
    Spinner spinnerSchool;
    SeekBar sbHappiness;
    Button btnDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        // init views
        etName = findViewById(R.id.et_name);
        cbCitizen = findViewById(R.id.cb_citizen);
        rgGender = findViewById(R.id.rg_gender);
        spinnerSchool = findViewById(R.id.spinner_school);
        sbHappiness = findViewById(R.id.sb_happiness);
        btnDone = findViewById(R.id.btn_done);

        // bind with listener
//      etName.addTextChangedListener(new TextWatcher() { ... });
        cbCitizen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "Citizen: "+isChecked);
            }
        });
        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_male:
                        Log.d(TAG, "Male selected");
                        break;
                    case R.id.rb_female:
                        Log.d(TAG, "Female selected");
                        break;
                }
            }
        });
        spinnerSchool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String school = parent.getItemAtPosition(position).toString();
                Log.d(TAG, school+" clicked");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sbHappiness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(TAG, "happiness: "+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // read data from each control
                String name = etName.getText().toString();
                boolean isCitizen = cbCitizen.isChecked();
                String gender = "male";
                if (rgGender.getCheckedRadioButtonId() == R.id.rb_female){
                    gender = "female";
                }
                String school = spinnerSchool.getSelectedItem().toString();
                int happiness = sbHappiness.getProgress();

                // pass data back to prev activity
                Intent data = new Intent();
                data.putExtra("name", name);
                data.putExtra("isCitizen", isCitizen);
                data.putExtra("gender", gender);
                data.putExtra("school", school);
                data.putExtra("happiness", happiness);
                setResult(RESULT_OK, data);
                // close current activity
                finish();
            }
        });
    }
}