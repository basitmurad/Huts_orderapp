package com.example.huts.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.huts.R;
import com.example.huts.fragments.fragmentAdapter.ActiveAdapter;
import com.example.huts.fragments.fragmentAdapter.CancelAdapter;
import com.example.huts.model.OrderData;
import com.example.huts.model.ShowDialoge;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CancelOrdersFragment extends Fragment {
    private RecyclerView recyclerView;
    private CancelAdapter cancelAdapter;
    private ArrayList<OrderData> canceledOrdersList = new ArrayList<>();
    private DatabaseReference canceledOrdersRef;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cancel_orders, container, false);

        ShowDialoge.showProgressDialog(getContext(),"Fetching your cancel orders");

        recyclerView = view.findViewById(R.id.cancelRecy);


        cancelAdapter = new CancelAdapter(getContext(), canceledOrdersList);
        recyclerView.setAdapter(cancelAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        retrieveDataFromFirebase();


        return view;
    }


    private void retrieveDataFromFirebase() {
        DatabaseReference refCancel = FirebaseDatabase.getInstance().getReference("CancelOrders");

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        refCancel.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ShowDialoge.dismissProgressDialog();
                }
            },700);
                canceledOrdersList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    OrderData orderData = dataSnapshot.getValue(OrderData.class);
                    if (orderData != null) {
                        canceledOrdersList.add(orderData);
                    }
                    else {
                        Toast.makeText(getActivity(), "No orders are cancel", Toast.LENGTH_SHORT).show();
                    }
                }

                cancelAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error if needed
            }
        });
    }

}