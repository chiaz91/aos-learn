package aos.learn.todo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import aos.learn.todo.Constants;
import aos.learn.todo.R;
import aos.learn.todo.adapter.TaskAdapter;
import aos.learn.todo.entity.Task;
import aos.learn.todo.viewmodel.TaskViewModel;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener, TaskAdapter.OnTaskClickedListener {
    private static final String TAG = "todo.act.main";
    private View vEmpty;
    private RecyclerView rvTodo;
    private TaskAdapter adapter;
    private FloatingActionButton fabAdd;
    TaskViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // retrieving ViewModel
        model = new ViewModelProvider(this).get(TaskViewModel.class);
        model.getTasks().observe(this, tasks -> {
            Log.d(TAG, "observed task list changes");
            adapter.setTaskList(tasks);
            vEmpty.setVisibility(tasks.size()==0?View.VISIBLE: View.GONE);
        });

        // init views
        vEmpty = findViewById(R.id.view_empty);
        fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(this);

        rvTodo = findViewById(R.id.rv_todos);
        adapter = new TaskAdapter(model.getTasks().getValue(), this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvTodo.setAdapter(adapter);
        rvTodo.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_add:
                addTask();
                break;
            default:
                Toast.makeText(this, "in progress", Toast.LENGTH_SHORT).show();
        }

    }

    private void addTask(){
        Intent toInput = new Intent(this, InputActivity.class);
        startActivityForResult(toInput, Constants.REQ_ADD_TASK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case Constants.REQ_ADD_TASK:
                if (resultCode==Activity.RESULT_OK && data!=null) {
                    try{
                        // retrieve the task
                        Task task = (Task) data.getSerializableExtra(Constants.EXTRA_TASK);
                        Log.d(TAG, "received task: "+task.toString());
                        // update recycler view
                        model.addTask(task);
                    }catch (Exception e){
                        Log.e(TAG, "Something wrong: "+e.getMessage());
                        e.printStackTrace();
                    }
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onTaskClicked(View itemView, int position) {
        final Task task = model.getTasks().getValue().get(position);
        Log.d(TAG, task.toString());

        AlertDialog deleteDialog = new AlertDialog.Builder(this)
                .setTitle("Delete Task")
                .setMessage(String.format("Do you want to delete '%s'?\nThis action cannot be undone", task.getName()))
                .setPositiveButton("Delete", (dialog, which)->{
                    model.removeTask(task);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
