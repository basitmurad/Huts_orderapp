package com.afaq.huts.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.afaq.huts.DashboardClass;

import com.afaq.huts.R;
import com.afaq.huts.ui.BreakFastActivity;

import java.util.ArrayList;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.MyHolder> {

    private Context context;
    private ArrayList<DashboardClass> list;

    public DashboardAdapter(Context context, ArrayList<DashboardClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DashboardAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.dashboarditem_custum, parent , false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardAdapter.MyHolder holder, int position) {

        DashboardClass dashboardClass = list.get(position);

        holder.name.setText(dashboardClass.getDishName());
        holder.description.setText(dashboardClass.getDishDiscription());
//        holder.imageUri.setImageURI(dashboardClass.getImageUri());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context.getApplicationContext(), BreakFastActivity.class);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView name , description;
        ImageView imageUri;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.lunchName);
            description = itemView.findViewById(R.id.lunchDesciption);

            imageUri  = itemView.findViewById(R.id.lunchImage);

        }
    }
}
