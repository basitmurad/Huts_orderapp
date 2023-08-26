package com.example.huts.fragments.fragmentAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.huts.R;
import com.example.huts.model.OrderData;
import com.example.huts.model.OrderDetails;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CancelAdapter extends RecyclerView.Adapter<CancelAdapter.Myholder> {
    private Context context;
    private ArrayList<OrderData> canceledOrdersList;
    private DatabaseReference refCancel;
    private String name = "";

    public CancelAdapter(Context context, ArrayList<OrderData> canceledOrdersList) {
        this.context = context;
        this.canceledOrdersList = canceledOrdersList;
        this.refCancel = FirebaseDatabase.getInstance().getReference("CancelOrders");
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cancel_layout, parent, false);
        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {
        OrderData orderData1 = canceledOrdersList.get(position);

        holder.t3.setText("Huts : " + orderData1.getHutName());
        holder.t2.setText("Total Price : " + String.valueOf(orderData1.getTotalPrice()));
//        Toast.makeText(context, "" + orderData1.isActive(), Toast.LENGTH_SHORT).show();

        for (OrderDetails orderDetails : orderData1.getOrderDetailsList()) {


            name += orderDetails.getName() + " , ";

        }
        holder.t1.setText(name);
        name = "";



    }

    @Override
    public int getItemCount() {
        return canceledOrdersList.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {
        TextView t1, t2, t3;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.cancelItemName);
            t2 = itemView.findViewById(R.id.cancelItemHutName);
            t3 = itemView.findViewById(R.id.cancelItemPrice);
        }
    }
}
