package com.afaq.huts.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afaq.huts.R;
import com.afaq.huts.adapters.ParentAdapter;
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


public class ActiveOrdersFragment extends Fragment {


    private RecyclerView recyclerView;


    private ArrayList<OrderData> activeOrdersList = new ArrayList<>();
    private ArrayList<OrderDetails> orderDetailsArrayList = new ArrayList<>();

    private DatabaseReference ordersRef;
    private TextView textView;

    private ParentAdapter parentAdapters;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_active_orders, container, false);


        recyclerView = rootView.findViewById(R.id.activeRecycler);
        textView = rootView.findViewById(R.id.textCounter12);

        ShowDialoge.showProgressDialog(getContext(), "Fetching your orders");


        String uid = FirebaseAuth.getInstance().getUid();
        ordersRef = FirebaseDatabase.getInstance().getReference("ActiveOrders").child(uid);


        activeOrdersList = new ArrayList<>();


        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ShowDialoge.dismissProgressDialog();

                activeOrdersList.clear();
                try {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        OrderData orderData = dataSnapshot.getValue(OrderData.class);
                        if (orderData != null && orderData.isActive()) {
                            activeOrdersList.add(orderData);

                        } else {
                            ShowDialoge.dismissProgressDialog();

//                            if (activeOrdersList.isEmpty()) {
//                                showNoActiveUsersDialog();
//                            }
                            Toast.makeText(getContext(), "No Active Orders", Toast.LENGTH_SHORT).show();
                        }
                    }


                    parentAdapters = new ParentAdapter(getContext(), activeOrdersList);

                    recyclerView.setAdapter(parentAdapters);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    parentAdapters.notifyDataSetChanged();

                    if (activeOrdersList.isEmpty()) {

                        textView.setVisibility(View.GONE);
                    } else {
                        textView.setVisibility(View.VISIBLE);
                    }
//
//                    if (activeOrdersList.isEmpty()) {
//                        showNoActiveUsersDialog();
//                    }
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


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (activeOrdersList.isEmpty()) {
//            showNoActiveUsersDialog();
//        }

    }

    private void showNoActiveUsersDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("No Active Users");
        builder.setMessage("There are currently no active users.");
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}