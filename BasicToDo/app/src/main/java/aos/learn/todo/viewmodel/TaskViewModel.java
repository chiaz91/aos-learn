package aos.learn.todo.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import aos.learn.todo.entity.Task;

/* Some notes
 * [1] if need context, uses AndroidViewModel which contain application context
 * [2] never save view/context to prevent memory leak
 */
public class TaskViewModel extends ViewModel {
    private static final String TAG = "todo.vm.task";
    private MutableLiveData<List<Task>> tasks;

    public LiveData<List<Task>> getTasks() {
        if (tasks == null) {
            tasks = new MutableLiveData<>();
            loadTasks();
        }
        return tasks;
    }

    private void loadTasks() {
        // TODO: implement asynchronous loading later
        tasks.setValue( new ArrayList<>());
    }

    public void addTask(Task task){
        List list = tasks.getValue();
        list.add(task);
        tasks.setValue(list);
    }

    public void removeTask(Task task){
        List list = tasks.getValue();
        list.remove(task);
        tasks.setValue(list);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "onCleared");
        // should be call before ViewModel being destroyed
    }
}
