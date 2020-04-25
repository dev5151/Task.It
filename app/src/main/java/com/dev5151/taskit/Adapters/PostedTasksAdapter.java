package com.dev5151.taskit.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev5151.taskit.Activities.DashboardActivity;
import com.dev5151.taskit.R;
import com.dev5151.taskit.models.Tasks;
import java.util.ArrayList;

public class PostedTasksAdapter extends RecyclerView.Adapter<PostedTasksAdapter.PostedTaskHolder> {

    private Context context;
    private ArrayList<Tasks> taskList;

    public PostedTasksAdapter(Context context, ArrayList<Tasks> taskList) {
        this.context=context;
        this.taskList=taskList;
    }

    @NonNull
    @Override
    public PostedTaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostedTaskHolder(LayoutInflater.from(context).inflate(R.layout.task_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PostedTaskHolder holder, final int position) {
        final Tasks task=taskList.get(position);
        holder.title.setText(task.getTitle());
        holder.location.setText(task.getLocation());
        holder.amount.setText(task.getAmount());
        holder.description.setText(task.getDescription());
        holder.basePrice.setText(task.getBasePrice());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),taskList.get(position).getUnixTime().toString(),Toast.LENGTH_LONG).show();
                //DashboardActivity.getBottomNavBarControlInterface().goToTask(1,taskList.get(position).getUnixTime().toString());
                Log.d("TIME", taskList.get(position).getUnixTime().toString());
            }
        });

    }


    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class PostedTaskHolder extends RecyclerView.ViewHolder {
        TextView title, location, amount, basePrice, description;
        ImageView imageView;

        public PostedTaskHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            location=itemView.findViewById(R.id.location);
            amount=itemView.findViewById(R.id.amount);
            imageView=itemView.findViewById(R.id.imageView);
            basePrice=itemView.findViewById(R.id.basePrice);
            description=itemView.findViewById(R.id.description);

        }
    }
}
