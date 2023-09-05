package com.example.huts.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.huts.R;
import com.example.huts.model.MessegeDetails;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MessAdapter extends RecyclerView.Adapter<MessAdapter.MyHolder> {
    private Context context;
    private ArrayList<MessegeDetails> messegeDetailsArrayList;

    public MessAdapter(Context context, ArrayList<MessegeDetails> messegeDetailsArrayList) {
        this.context = context;
        this.messegeDetailsArrayList = messegeDetailsArrayList;
    }

    public void clear() {
        messegeDetailsArrayList.clear();
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MessAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.receiver_layout, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessAdapter.MyHolder holder, int position) {
        MessegeDetails messegeDetails = messegeDetailsArrayList.get(position);

        holder.textView.setText(messegeDetails.getMessege());

        Date date = new Date(messegeDetails.getTimestamp());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
//        sdf.setTimeZone(TimeZone.getTimeZone("UTC")); // Assuming the timestamps are stored in UTC

        String formattedTime = sdf.format(date);

// If you want to display the time in the device's local time zone:
// sdf.setTimeZone(TimeZone.getDefault()); // Set to the device's local time zone

        holder.time.setText(formattedTime);


        if (messegeDetails.getSenderId().equals(FirebaseAuth.getInstance().getUid())) {
            holder.layout.setBackground(context.getResources().getDrawable(R.drawable.back_messege));
            holder.time.setGravity(Gravity.END);
            holder.textView.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            holder.layout.setBackground(context.getResources().getDrawable(R.drawable.back_messgerece));
            holder.time.setGravity(Gravity.START);
            holder.textView.setGravity(Gravity.END);
            holder.textView.setTextColor(context.getResources().getColor(R.color.black));
        }

    }

    @Override
    public int getItemCount() {
        return messegeDetailsArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView textView ,time;
        LinearLayout layout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.userSend);
            layout = itemView.findViewById(R.id.userLayout);
            time = itemView.findViewById(R.id.time);
        }
    }
}
