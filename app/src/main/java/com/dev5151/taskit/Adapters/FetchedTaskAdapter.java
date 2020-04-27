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
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FetchedTaskAdapter extends RecyclerView.Adapter<FetchedTaskAdapter.FetchedTaskViewHolder> {
    private Context context;
    private ArrayList<Tasks> taskList;
    private LatLng employerLatLng;
    private LatLng employeeLatLng;


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
        Tasks task = taskList.get(position);
        final String employerUid = task.getUid();
        String employeeUid = FirebaseAuth.getInstance().getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(employerUid).child("latLng").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                employerLatLng = dataSnapshot.getValue(LatLng.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("users").child(employeeUid).child("LatLng").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                employeeLatLng = dataSnapshot.getValue(LatLng.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Integer distance = calculateDistanceInKilometer(employeeLatLng.latitude,employerLatLng.longitude,employerLatLng.latitude,employerLatLng.longitude);

        holder.title.setText(task.getTitle());

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

}
