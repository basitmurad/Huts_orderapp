package com.afaq.huts.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afaq.huts.R;
import com.afaq.huts.fragments.fragmentAdapter.DeliveredOrdersAdapter;

import com.afaq.huts.model.OrderData;
import com.afaq.huts.model.OrderDetails;
import com.afaq.huts.model.ShowDialoge;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeliverFragment extends Fragment {

    private RecyclerView recyclerView;


    private ArrayList<OrderData> activeOrdersList = new ArrayList<>();
    private ArrayList<OrderDetails> orderDetailsArrayList = new ArrayList<>();

    private DatabaseReference ordersRef;

    private DeliveredOrdersAdapter deliveredOrdersAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_deliver, container, false);


        recyclerView = view.findViewById(R.id.recyclerViewDeliver);

        ShowDialoge.showProgressDialog(getContext(), "Fetching your orders");


        String uid = FirebaseAuth.getInstance().getUid();
        ordersRef = FirebaseDatabase.getInstance().getReference("DeliverdOrders").child(uid);


        activeOrdersList = new ArrayList<>();
        orderDetailsArrayList = new ArrayList<>();
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (isAdded()) {
                    if (snapshot.exists()) {
                        activeOrdersList.clear();
                        orderDetailsArrayList.clear();
                        ShowDialoge.dismissProgressDialog();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            OrderData orderData = snapshot1.getValue(OrderData.class);
                            if (orderData != null) {
                                activeOrdersList.add(orderData);
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    OrderDetails orderDetails = dataSnapshot.getValue(OrderDetails.class);
                                    orderDetailsArrayList.add(orderDetails);
                                }
                            }
                        }
                        deliveredOrdersAdapter = new DeliveredOrdersAdapter(requireContext(), activeOrdersList);
                        recyclerView.setAdapter(deliveredOrdersAdapter);
                        deliveredOrdersAdapter.notifyDataSetChanged();
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    } else {
                        ShowDialoge.dismissProgressDialog();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (isAdded()) {
                    ShowDialoge.dismissProgressDialog();
                }
            }
        });
//        ordersRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                try {
//
//
//
//                    if (snapshot.exists()) {
//                        activeOrdersList.clear();
//                        orderDetailsArrayList.clear();
//                        ShowDialoge.dismissProgressDialog();
//
//
//                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                            OrderData orderData = snapshot1.getValue(OrderData.class);
//                            if (orderData != null ) {
//                                activeOrdersList.add(orderData);
//
//                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                                    OrderDetails orderDetails = dataSnapshot.getValue(OrderDetails.class);
//
//                                    orderDetailsArrayList.add(orderDetails);
//
//                                }
//
//
//                            }
//
//                        }
//
//
//                        deliveredOrdersAdapter = new DeliveredOrdersAdapter(requireContext(), activeOrdersList);
//                        recyclerView.setAdapter(deliveredOrdersAdapter);
//
//                        deliveredOrdersAdapter.notifyDataSetChanged();
//                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//                    } else {
//                        ShowDialoge.dismissProgressDialog();
//                        Log.d("Exception", "error");
//                    }
//                } catch (ArrayIndexOutOfBoundsException e) {
//                    Log.d("Exception", "error");
//                    // Toast.makeText(getActivity().getApplicationContext(), " "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                ShowDialoge.dismissProgressDialog();
//                Log.d("Exception", "error");
//            }
//        });


        return view;
    }
}