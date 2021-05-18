package aos.learn.todo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import aos.learn.todo.Constants;
import aos.learn.todo.R;
import aos.learn.todo.entity.Task;


public class InputActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private static final String TAG = "todo.act.input";
    TextView tvDate, tvTime;
    EditText etTitle, etDesc;
    Calendar dueDate;
    SimpleDateFormat dateFormat, timeFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        // init view
        etTitle = findViewById(R.id.et_title);
        etDesc  = findViewById(R.id.et_desc);
        tvDate = findViewById(R.id.tv_date_picker);
        tvDate.setOnClickListener(this);
        tvTime = findViewById(R.id.tv_time_picker);
        tvTime.setOnClickListener(this);

        // set default due date
        dueDate = Calendar.getInstance(Locale.getDefault());
        dueDate.add(Calendar.DAY_OF_YEAR, 1);
        dueDate.set(Calendar.SECOND, 0);
        dueDate.set(Calendar.MILLISECOND, 0);
        dateFormat = new SimpleDateFormat(Constants.FORMAT_DATE, Locale.getDefault());
        timeFormat = new SimpleDateFormat(Constants.FORMAT_TIME, Locale.getDefault());
        formatDateTime();
    }

    private void formatDateTime(){
        tvDate.setText(dateFormat.format(dueDate.getTime()));
        tvTime.setText(timeFormat.format(dueDate.getTime()));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.input, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                doSave();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_date_picker:
                showDatePicker();
                break;

            case R.id.tv_time_picker:
                showTimerPicker();
                break;

            default:
                Toast.makeText(this, "in progress", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDatePicker(){
        int year = dueDate.get(Calendar.YEAR);
        int month = dueDate.get(Calendar.MONTH);
        int day = dueDate.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, year, month, day);
        datePickerDialog.show();
    }

    private void showTimerPicker(){
        int hour = dueDate.get(Calendar.HOUR_OF_DAY);
        int min = dueDate.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, this, hour, min, false);
        timePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        dueDate.set(Calendar.YEAR, year);
        dueDate.set(Calendar.MONTH, month);
        dueDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        formatDateTime();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        dueDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
        dueDate.set(Calendar.MINUTE, minute);
        formatDateTime();
    }

    private void doSave(){
        try {
            String title = etTitle.getText().toString();
            String desc  =  etDesc.getText().toString();
            Task task = new Task(title, desc, dueDate.getTime());

            Log.d(TAG, task.toString());

            // Set result and close activity
            Intent intent = new Intent();
            intent.putExtra(Constants.EXTRA_TASK, task);
            setResult(Activity.RESULT_OK, intent);
            finish();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
