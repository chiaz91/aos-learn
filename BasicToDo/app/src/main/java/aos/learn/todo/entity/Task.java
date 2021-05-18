package aos.learn.todo.entity;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {
    private String name;
    private String desc;
    private Date dueDate;

    public Task(String name, String desc, Date dueDate) {
        this.name = name;
        this.desc = desc;
        this.dueDate = dueDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "ToDoTask{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", dueDate=" + dueDate +
                '}';
    }
}
