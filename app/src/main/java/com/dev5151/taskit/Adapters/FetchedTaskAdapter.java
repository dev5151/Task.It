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
import com.dev5151.taskit.Interfaces.ItemClickListener;
import com.dev5151.taskit.R;
import com.dev5151.taskit.models.Tasks;
import com.dev5151.taskit.models.User;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FetchedTaskAdapter extends RecyclerView.Adapter<FetchedTaskAdapter.FetchedTaskViewHolder> {
    private Context context;
    private List<Tasks> taskList;
    private ItemClickListener itemClickListener;
    String employerUid;
    String employeeUid;
    User employee;
    User employer;
    Integer distance;

    public FetchedTaskAdapter(Context context, List<Tasks> taskList, ItemClickListener itemClickListener) {
        this.context = context;
        this.taskList = taskList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public FetchedTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FetchedTaskViewHolder(LayoutInflater.from(context).inflate(R.layout.fetched_task_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FetchedTaskAdapter.FetchedTaskViewHolder holder, int position) {
        final Tasks task = taskList.get(position);
        employerUid = task.getUid();
        employeeUid = FirebaseAuth.getInstance().getUid();

        fetchEmployer();
        fetchEmployee();


        holder.title.setText(task.getTitle());
        holder.time.setText(task.getTill_time());
        holder.amount.setText(task.getService_amt());
        holder.tvDistance.setText(String.valueOf(distance));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onClickTask(task);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class FetchedTaskViewHolder extends RecyclerView.ViewHolder {
        TextView title, amount, time, tvDistance;

        public FetchedTaskViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            amount = itemView.findViewById(R.id.extra_amt);
            time = itemView.findViewById(R.id.time_left);
            tvDistance = itemView.findViewById(R.id.distance);

        }
    }

    public int calculateDistanceInKilometer(double userLat, double userLng,
                                            double venueLat, double venueLng) {

        double latDistance = Math.toRadians(userLat - venueLat);
        double lngDistance = Math.toRadians(userLng - venueLng);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(venueLat))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (int) (Math.round(DashboardActivity.AVERAGE_RADIUS_OF_EARTH_KM * c));
    }

    private void fetchEmployer() {
        FirebaseDatabase.getInstance().getReference().child("users").child(employerUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshot1 = dataSnapshot;
                employer = dataSnapshot1.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void fetchEmployee() {
        FirebaseDatabase.getInstance().getReference().child("users").child(employeeUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshot1 = dataSnapshot;
                employee = dataSnapshot1.getValue(User.class);
                distance = calculateDistanceInKilometer(employee.getLatLng().getLatitude(), employer.getLatLng().getLongitude(), employee.getLatLng().getLatitude(), employee.getLatLng().getLongitude());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
