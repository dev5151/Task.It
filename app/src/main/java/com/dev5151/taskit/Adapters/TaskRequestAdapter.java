package com.dev5151.taskit.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dev5151.taskit.R;
import com.dev5151.taskit.models.TaskRequestModel;
import com.dev5151.taskit.models.Tasks;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class TaskRequestAdapter extends RecyclerView.Adapter<TaskRequestAdapter.TaskRequestViewHolder> {
    private List<TaskRequestModel> taskRequestList;
    private Context context;
    private String viewType;
    private final int VIEW_HORIZONTAL = 1;
    private final int VIEW_VERTICAL = 0;
    String date;
    String budget;

    public TaskRequestAdapter(List<TaskRequestModel> taskRequestList, Context context, String viewType) {
        this.taskRequestList = taskRequestList;
        this.context = context;
        this.viewType = viewType;
    }

    @NonNull
    @Override
    public TaskRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_HORIZONTAL) {
            return new TaskRequestViewHolder(LayoutInflater.from(context).inflate(R.layout.task_card_item, parent, false));
        } else {
            return new TaskRequestViewHolder(LayoutInflater.from(context).inflate(R.layout.task_request_ltem, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final TaskRequestViewHolder holder, int position) {
        final TaskRequestModel taskRequestModel = taskRequestList.get(position);
        final String title = taskRequestModel.getTitle();
        String imgUrl = taskRequestModel.getImgUrl();
        Float rating = taskRequestModel.getRating();
        final String name = taskRequestModel.getName();

        //holder.ratingBar.setRating(rating);

      /*  if (imgUrl != null) {
            Glide.with(context).load(imgUrl).into(holder.img);
        } else {
            holder.img.setImageResource(R.drawable.ic_location_illustration);
        }*/
        switch (holder.getItemViewType()) {
            case VIEW_HORIZONTAL:
                FirebaseDatabase.getInstance().getReference().child("tasks").child(taskRequestModel.getTaskId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Tasks task = dataSnapshot.getValue(Tasks.class);
                        date = task.getCreation_date();
                        budget = task.getService_amt();

                        holder.tvName.setVisibility(View.VISIBLE);
                        holder.tvName.setText(name);
                        holder.tvTitle.setText(title);
                        holder.tvDate.setText(date);
                        holder.tvBudget.setText(budget);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            case VIEW_VERTICAL:
                holder.tvTitle.setText(title);
                holder.tvName.setText(name);
        }

    }

    @Override
    public int getItemCount() {
        return taskRequestList.size();
    }

    public class TaskRequestViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvTitle, tvDate, tvBudget;
        //        ImageView img;
        RatingBar ratingBar;

        public TaskRequestViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvTitle = itemView.findViewById(R.id.tv_title);
            //ratingBar = itemView.findViewById(R.id.rating);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvBudget = itemView.findViewById(R.id.tv_budget);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (viewType.equals("horizontal_recycler_view")) {
            return VIEW_HORIZONTAL;

        } else {
            return VIEW_VERTICAL;
        }
    }
}
