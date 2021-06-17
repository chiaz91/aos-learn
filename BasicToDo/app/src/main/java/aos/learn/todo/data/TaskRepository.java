package aos.learn.todo.data;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;

public class TaskRepository {
    private TaskDao taskDao;
    private LiveData<List<Task>> tasks;

    public TaskRepository(AppDatabase database){
        taskDao = database.taskDao();
        tasks = taskDao.getAll();
    }

    public LiveData<List<Task>> getAllTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        // running db operation on background thread
        AppDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.insert(task);
        });
    }

    public void deleteTask(Task task){
        // running db operation on background thread
        AppDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.delete(task);
        });
    }
}
