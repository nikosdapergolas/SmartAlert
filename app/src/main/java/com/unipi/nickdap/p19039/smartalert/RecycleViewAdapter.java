package com.unipi.nickdap.p19039.smartalert;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder>
{
    List<Emergency> emergenciesList;
    Context context;

    public RecycleViewAdapter(List<Emergency> emergenciesList, Context context) {
        this.emergenciesList = emergenciesList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_emergency,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.emergencyType.setText(emergenciesList.get(position).getEmergency());
        holder.descryption.setText(emergenciesList.get(position).getDescription());
        holder.location.setText(emergenciesList.get(position).getLocation());
        holder.timestamp.setText(emergenciesList.get(position).getTimestamp());

        int index = position;
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,AfterClickOnListViewActivity.class);
                intent.putExtra("EmergencyType",emergenciesList.get(index).getEmergency());
                intent.putExtra("Descryption",emergenciesList.get(index).getDescription());
                intent.putExtra("Location",emergenciesList.get(index).getLocation());
                intent.putExtra("Longtitude",emergenciesList.get(index).getLongtitude());
                intent.putExtra("Latitude",emergenciesList.get(index).getLatitude());
                intent.putExtra("Timestamp",emergenciesList.get(index).getTimestamp());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return emergenciesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView emergencyType;
        TextView descryption;
        TextView location;
        TextView timestamp;

        ConstraintLayout parentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // Binding the objects to the items of the layout
            emergencyType = itemView.findViewById(R.id.tv_EmergencyTipe);
            descryption = itemView.findViewById(R.id.tv_Description);
            location = itemView.findViewById(R.id.tv_Location);
            timestamp = itemView.findViewById(R.id.tv_Timestamp);
            parentLayout = itemView.findViewById(R.id.one_line_emergency_layout);
        }
    }
}
