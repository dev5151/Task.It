package com.dev5151.taskit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev5151.taskit.R;
import com.dev5151.taskit.models.Tasks;

import java.util.List;

public class GivenTasksAdapter extends RecyclerView.Adapter<GivenTasksAdapter.GivenTasksViewHolder> {
    private Context context;
    private List<Tasks> tasksList;

    public GivenTasksAdapter(Context context, List<Tasks> tasksList) {
        this.context = context;
        this.tasksList = tasksList;
    }

    @NonNull
    @Override
    public GivenTasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GivenTasksViewHolder(LayoutInflater.from(context).inflate(R.layout.task_card_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GivenTasksViewHolder holder, int position) {
        Tasks task=tasksList.get(position);
        holder.tvTitle.setText(task.getTitle());
        holder.tvBudget.setText(task.getService_amt());
        holder.tvDate.setText(task.getCreation_date());
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    public class GivenTasksViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle, tvBudget, tvDate;

        public GivenTasksViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.tv_title);
            tvBudget=itemView.findViewById(R.id.tv_budget);
            tvDate=itemView.findViewById(R.id.tv_date);
        }
    }
}
