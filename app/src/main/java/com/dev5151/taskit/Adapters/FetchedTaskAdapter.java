package com.dev5151.taskit.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev5151.taskit.Activities.DashboardActivity;
import com.dev5151.taskit.R;
import com.dev5151.taskit.models.Tasks;

import java.util.ArrayList;

public class FetchedTaskAdapter extends RecyclerView.Adapter<FetchedTaskAdapter.FetchedTaskViewHolder> {
    private Context context;
    private ArrayList<Tasks> taskList;


    public FetchedTaskAdapter(Context context, ArrayList<Tasks> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public FetchedTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FetchedTaskViewHolder(LayoutInflater.from(context).inflate(R.layout.fetched_task_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FetchedTaskAdapter.FetchedTaskViewHolder holder, int position) {
        final Tasks task = taskList.get(position);
        holder.title.setText(task.getTitle());
        /*holder.amount.setText(task.getBasePrice());
        holder.extra.setText(task.getAmount());
*/
       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DashboardActivity.getBottomNavBarControlInterface().goToTask(task.getFlag(),task.getCreatorId());
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class FetchedTaskViewHolder extends RecyclerView.ViewHolder {
        TextView title, amount, extra;

        public FetchedTaskViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            amount = itemView.findViewById(R.id.amount);
            extra = itemView.findViewById(R.id.extra);
        }
    }


}
