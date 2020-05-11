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
import com.google.android.gms.tasks.Task;

import java.util.List;

public class TaskRequestAdapter extends RecyclerView.Adapter<TaskRequestAdapter.TaskRequestViewHolder> {
    private List<TaskRequestModel> taskRequestList;
    private Context context;

    public TaskRequestAdapter(List<TaskRequestModel> taskRequestList, Context context) {
        this.taskRequestList = taskRequestList;
        this.context = context;
    }

    @NonNull
    @Override
    public TaskRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskRequestViewHolder(LayoutInflater.from(context).inflate(R.layout.task_request_ltem, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull TaskRequestViewHolder holder, int position) {
        final TaskRequestModel taskRequestModel = taskRequestList.get(position);
        String title = taskRequestModel.getTitle();
        String imgUrl = taskRequestModel.getImgUrl();
        Float rating = taskRequestModel.getRating();
        String name = taskRequestModel.getName();

        holder.tvTitle.setText(title);
        holder.tvName.setText(name);
        holder.ratingBar.setRating(rating);

      /*  if (imgUrl != null) {
            Glide.with(context).load(imgUrl).into(holder.img);
        } else {
            holder.img.setImageResource(R.drawable.ic_location_illustration);
        }*/

    }

    @Override
    public int getItemCount() {
        return taskRequestList.size();
    }

    public class TaskRequestViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvTitle;
        //        ImageView img;
        RatingBar ratingBar;

        public TaskRequestViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvTitle = itemView.findViewById(R.id.tv_title);
            ratingBar = itemView.findViewById(R.id.rating);

        }
    }
}
