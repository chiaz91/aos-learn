package aos.learn.todo.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import aos.learn.todo.data.AppDatabase;
import aos.learn.todo.data.Task;
import aos.learn.todo.data.TaskRepository;

/* Some notes
 * [1] if need context, uses AndroidViewModel which contain application context
 * [2] never save view/context to prevent memory leak
 */
public class TaskViewModel extends AndroidViewModel {
    private static final String TAG = "todo.vm.task";

    private TaskRepository repo;
    private final LiveData<List<Task>> tasks;

    public TaskViewModel(@NonNull @org.jetbrains.annotations.NotNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getDatabase(application);
        repo = new TaskRepository(database);
        tasks = repo.getAllTasks();
    }

    public LiveData<List<Task>> getTasks() {
        return tasks;
    }


    public void addTask(Task task){
        repo.addTask(task);
    }

    public void removeTask(Task task){
        repo.deleteTask(task);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "onCleared");
        // should be call before ViewModel being destroyed
    }
}
