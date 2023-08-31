package com.example.huts.fragments;

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
import java.util.Locale;


public class ActiveOrdersFragment extends Fragment {



    private RecyclerView recyclerView;


    private ArrayList<OrderData> activeOrdersList = new ArrayList<>();
    private ArrayList<OrderDetails> orderDetailsArrayList = new ArrayList<>();

    private DatabaseReference ordersRef;
private ParentAdapter parentAdapter ;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_active_orders, container, false);


        recyclerView = rootView.findViewById(R.id.activeRecycler);

        ShowDialoge.showProgressDialog(getContext(),"Fetching your orders");


        String uid = FirebaseAuth.getInstance().getUid();
        ordersRef = FirebaseDatabase.getInstance().getReference("ActiveOrders").child(uid);


        activeOrdersList = new ArrayList<>();
        orderDetailsArrayList   =new ArrayList<>();

        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {


                    if (snapshot.exists()) {
                        activeOrdersList.clear();
                        orderDetailsArrayList.clear();
                        ShowDialoge.dismissProgressDialog();


                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            OrderData orderData = snapshot1.getValue(OrderData.class);
                            if (orderData != null && orderData.isActive()) {
                                activeOrdersList.add(orderData);

                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    OrderDetails orderDetails = dataSnapshot.getValue(OrderDetails.class);

                                    orderDetailsArrayList.add(orderDetails);

                                }


                            }

                        }


                        parentAdapter = new ParentAdapter(requireContext(), activeOrdersList);
                        recyclerView.setAdapter(parentAdapter);
                        parentAdapter.notifyDataSetChanged();
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                    } else {
                        ShowDialoge.dismissProgressDialog();
                        Log.d("Exception" , "error");
                    }
                }
                catch (ArrayIndexOutOfBoundsException e)
                {
                    Log.d("Exception" , "error");
                   // Toast.makeText(getActivity().getApplicationContext(), " "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                ShowDialoge.dismissProgressDialog();
                Log.d("Exception" , "error");
            }
        });

















        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (parentAdapter != null) {
            parentAdapter.notifyDataSetChanged();
        }


    }
}