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
import com.example.huts.adapters.ParentAdapter;
import com.example.huts.fragments.fragmentAdapter.CancelAdpter;
import com.example.huts.model.OrderData;
import com.example.huts.model.OrderDetails;
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




    private ArrayList<OrderData> activeOrdersList = new ArrayList<>();
    private ArrayList<OrderDetails> orderDetailsArrayList = new ArrayList<>();

    private DatabaseReference ordersRef;

    private CancelAdpter cancelAdpter;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cancel_orders, container, false);

        recyclerView = view.findViewById(R.id.cancelRecy);

        ShowDialoge.showProgressDialog(getContext(),"Fetching your orders");


        String uid = FirebaseAuth.getInstance().getUid();
        ordersRef = FirebaseDatabase.getInstance().getReference("CancelOrders").child(uid);


        activeOrdersList = new ArrayList<>();
        orderDetailsArrayList   =new ArrayList<>();


        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists())
                {
                    activeOrdersList.clear();
                    orderDetailsArrayList.clear();
                    ShowDialoge.dismissProgressDialog();
                    Toast.makeText(getActivity().getApplicationContext(), "data is exist", Toast.LENGTH_SHORT).show();

             for (DataSnapshot snapshot1 :snapshot.getChildren())
             {

                 OrderData orderData = snapshot1.getValue(OrderData.class);
                 activeOrdersList.add(orderData);
             }


             cancelAdpter = new CancelAdpter(getActivity().getApplicationContext(),activeOrdersList);
             recyclerView.setAdapter(cancelAdpter);
             recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
             cancelAdpter.notifyDataSetChanged();
                }
                else {
                    ShowDialoge.dismissProgressDialog();
                    Toast.makeText(getActivity().getApplicationContext(), " no data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                ShowDialoge.dismissProgressDialog();
                Log.d("Exception" , "error");
            }
        });


        return view;
    }




}