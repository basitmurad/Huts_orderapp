package com.afaq.huts.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afaq.huts.R;
import com.afaq.huts.fragments.fragmentAdapter.CancelAdpter;


import com.afaq.huts.model.OrderData;
import com.afaq.huts.model.ShowDialoge;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CancelOrdersFragment extends Fragment {
    private RecyclerView recyclerView;

    private CancelAdpter cancelAdpter;
    private ArrayList<OrderData> canceledOrdersList = new ArrayList<>();
    private DatabaseReference canceledOrdersRef;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cancel_orders, container, false);

        ShowDialoge.showProgressDialog(getContext(), "Fetching your cancel orders");

        recyclerView = view.findViewById(R.id.cancelRecy);


        String uid = FirebaseAuth.getInstance().getUid();
        canceledOrdersRef = FirebaseDatabase.getInstance().getReference("CancelOrders").child(uid);


        canceledOrdersList = new ArrayList<>();


        canceledOrdersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                canceledOrdersList.clear();
                ShowDialoge.dismissProgressDialog();

                try {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        OrderData orderData = dataSnapshot.getValue(OrderData.class);
                        if (orderData != null && orderData.isActive()) {
                            canceledOrdersList.add(orderData);
                        } else {
                            ShowDialoge.dismissProgressDialog();

                            Toast.makeText(getContext(), "No Cancel Orders", Toast.LENGTH_SHORT).show();
                        }
                    }
                    Context context = getContext();
                    if (context != null) {
                        cancelAdpter = new CancelAdpter(requireContext(), canceledOrdersList);
                    }


//                    cancelAdpter = new CancelAdpter(requireContext(), canceledOrdersList);


                    recyclerView.setAdapter(cancelAdpter);
                    if (isAdded()) {
                        // The fragment is attached, so it's safe to access the context
                        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                    }
//                    recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                } catch (ArrayIndexOutOfBoundsException e) {
                    ShowDialoge.dismissProgressDialog();

                    Toast.makeText(getActivity(), " catch" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                ShowDialoge.dismissProgressDialog();


                Toast.makeText(getActivity(), "database " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        return view;
    }


}