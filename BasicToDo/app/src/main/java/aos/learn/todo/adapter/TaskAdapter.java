package aos.learn.todo.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.text.SimpleDateFormat;
import java.util.List;

import aos.learn.todo.R;
import aos.learn.todo.entity.Task;


public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{
    public interface OnTaskClickedListener {
        void onTaskClicked(View itemView, int position);
    }
    static final String  FORMAT_DATE = "dd/MMM";
    SimpleDateFormat sdf;
    List<Task> taskList;
    OnTaskClickedListener listener;

    public TaskAdapter(List<Task> taskList, OnTaskClickedListener listener){
        super();
        this.taskList = taskList;
        this.listener = listener;
        this.sdf = new SimpleDateFormat(FORMAT_DATE);
    }

    @Override
    public int getItemCount() {
        return this.taskList.size();
    }

    public void setTaskList(List<Task> taskList){
        this.taskList = taskList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_task_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {
        Task curTask = taskList.get(position);
        holder.updateAs(curTask);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvMonth, tvDay, tvTaskName, tvTaskDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMonth = itemView.findViewById(R.id.lbl_month);
            tvDay = itemView.findViewById(R.id.lbl_day);
            tvTaskName = itemView.findViewById(R.id.lbl_task_name);
            tvTaskDesc = itemView.findViewById(R.id.lbl_task_desc);
            itemView.setOnClickListener(this);
        }

        public void updateAs(Task task){
            String[] strDate = sdf.format(task.getDueDate()).split("/");
            tvDay.setText(strDate[0]);
            tvMonth.setText(strDate[1]);
            tvTaskName.setText(task.getName());;
            tvTaskDesc.setText(task.getDesc());
        }

        @Override
        public void onClick(View v) {
            if(listener!=null){
                listener.onTaskClicked(itemView, getBindingAdapterPosition());
            }
        }
    }
}
