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
import com.example.huts.adapters.ParentAdpter;
import com.example.huts.fragments.fragmentAdapter.ActiveAdapter;
import com.example.huts.model.OrderData;
import com.example.huts.model.OrderDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ActiveOrdersFragment extends Fragment {

     private ActiveAdapter adapter;

     private  RecyclerView recyclerView;


    private  ArrayList<OrderData> activeOrdersList = new ArrayList<>();
    private  ArrayList<OrderDetails> orderDetailsArrayList = new ArrayList<>();
   private DatabaseReference ordersRef ;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_active_orders, container, false);


        recyclerView = rootView.findViewById(R.id.activeRecycler);

       String uid =  FirebaseAuth.getInstance().getUid();





        ordersRef = FirebaseDatabase.getInstance().getReference("ActiveOrders").child(uid);

        if (activeOrdersList.isEmpty())
        {
            Toast.makeText(getActivity(), "no active order ", Toast.LENGTH_SHORT).show();
        }

        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                activeOrdersList.clear();
                try {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        OrderData orderData = dataSnapshot.getValue(OrderData.class);
                        if (orderData != null && orderData.isActive()) {
                            activeOrdersList.add(orderData);
                        } else {
                            Toast.makeText(getContext(), "No Active Orders", Toast.LENGTH_SHORT).show();
                        }
                    }

                    adapter = new ActiveAdapter(getContext(), activeOrdersList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                    adapter.notifyDataSetChanged();
                }catch (ArrayIndexOutOfBoundsException e)
                {
                    Toast.makeText(getActivity(), " catch"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getActivity(), "database "+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });








//        ordersRef.addValueEventListener(new ValueEventListener() {
//
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                activeOrdersList.clear();
////                activeOrdersList.clear();
//                Log.d("Firebase", "Snapshot: " + snapshot.toString());
//
//           try {
//               for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                   OrderData orderData = dataSnapshot.getValue(OrderData.class);
//                   if (orderData != null && orderData.isActive()) {
//                       activeOrdersList.add(orderData);
//                   }
//                   else {
//                       Toast.makeText(getContext(), "No Active Orders", Toast.LENGTH_SHORT).show();
//                   }
//               }
//               adapter = new ActiveAdapter(getContext(),activeOrdersList);
//               recyclerView.setAdapter(adapter);
//               recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//               adapter.notifyDataSetChanged();
//           }
//           catch (ArrayIndexOutOfBoundsException e)
//           {
//               Toast.makeText(getContext(), "" +e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//           }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//                Toast.makeText(getActivity(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}