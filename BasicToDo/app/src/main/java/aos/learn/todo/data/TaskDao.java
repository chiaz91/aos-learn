package aos.learn.todo.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM tasks")
    LiveData<List<Task>> getAll();

    @Query("SELECT * FROM tasks WHERE id = :id")
    LiveData<Task> loadById(int id);

    // for testing
    @Insert(onConflict = OnConflictStrategy.ABORT)
    long insert(Task task);

//    @Insert
//    void insertAll(Task... tasks);
//
//    @Update
//    void update(Task task);

    @Delete
    void delete(Task task);
}
