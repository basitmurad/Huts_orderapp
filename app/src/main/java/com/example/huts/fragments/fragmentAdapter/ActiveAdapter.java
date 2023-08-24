package com.example.huts.fragments.fragmentAdapter;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.huts.R;
import com.example.huts.model.OrderData;
import com.example.huts.model.OrderDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActiveAdapter extends RecyclerView.Adapter<ActiveAdapter.Myholder> {
    private Context context;

    private ArrayList<OrderData> orderData;
    private DatabaseReference databaseReference;
    private DatabaseReference refCancel;
    private String name = "";

    public ActiveAdapter(Context context, ArrayList<OrderData> orderData) {
        this.context = context;
        this.orderData = orderData;
        this.databaseReference = FirebaseDatabase.getInstance().getReference("ActiveOrders");
        this.refCancel = FirebaseDatabase.getInstance().getReference("CancelOrders");


    }

    @NonNull
    @Override
    public ActiveAdapter.Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.active1_layout, parent, false);
        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveAdapter.Myholder holder, @SuppressLint("RecyclerView") int position) {

        OrderData orderData1 = orderData.get(position);
        String hutName = orderData1.getHutName();
        String totalPrice = String.valueOf(orderData1.getTotalPrice());


        holder.t3.setText("Huts : " + orderData1.getHutName());
        holder.t2.setText("Total Price : " + String.valueOf(orderData1.getTotalPrice()));
        Toast.makeText(context, "" + orderData1.isActive(), Toast.LENGTH_SHORT).show();

        for (OrderDetails orderDetails : orderData1.getOrderDetailsList()) {


            name += orderDetails.getName() + " , ";

        }
        holder.t1.setText(name);
        name = "";

        String uDi = FirebaseAuth.getInstance().getCurrentUser().getUid();

        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderData orderData2 = orderData.get(position);
                orderData2.setActive(false);

                databaseReference.child(orderData2.getUserId()).child(orderData2.getPushId())
                        .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                refCancel.child(orderData2.getUserId()).child(orderData2.getPushId()).setValue(orderData2)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                // Remove the item from the list and notify the adapter
                                             try {
                                                 if (position >= 0 && position < orderData.size()) {
                                                     orderData.remove(position);
                                                     notifyItemRemoved(position);
                                                 } else {
                                                     // Handle invalid position
                                                     Log.e("Adapter", "Invalid position for removal: " + position);
                                                 }
                                             }
                                             catch (Exception e)
                                             {
                                                 Toast.makeText(context, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                             }
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(context, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle failure
                            }
                        });
            }
        });


//        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                OrderData orderData2 = orderData.get(position);
//                orderData2.setActive(false);
//
//                databaseReference.child(orderData2.getUserId()).child(orderData2.getPushId())
//                        .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                refCancel.child(orderData2.getUserId()).setValue(orderData2)
//                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void unused) {
//                                                // Remove the item from the list and notify the adapter
//                                                orderData.remove(position);
//                                                if (!orderData.isEmpty()) {
//                                                    notifyDataSetChanged();
//                                                } else {
//                                                    // Handle the case of an empty list (e.g., display a message)
//                                                    // For example: displayNoOrdersMessage();
//                                                    Toast.makeText(context, "orders are empty", Toast.LENGTH_SHORT).show();
//                                                }
//                                            }
//                                        })
//                                        .addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                Toast.makeText(context, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                // Handle failure
//                            }
//                        });
//            }
//        });




    }

    @Override
    public int getItemCount() {
        return orderData.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {

        TextView t1, t2, t3;
        Button btnCancel;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.activeNam);
            t2 = itemView.findViewById(R.id.activePric);
            t3 = itemView.findViewById(R.id.activeQan);
            btnCancel = itemView.findViewById(R.id.btnCancel);
        }
    }
}
