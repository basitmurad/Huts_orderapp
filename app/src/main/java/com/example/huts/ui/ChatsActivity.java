package com.example.huts.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.huts.Admin;
import com.example.huts.SessionManager;

import com.example.huts.adapters.MessAdapter;
import com.example.huts.adapters.MessegeAdapter;
import com.example.huts.databinding.ActivityChatsBinding;
import com.example.huts.model.MessegeDetails;
import com.example.utils.InternetChecker;
import com.example.utils.NetworkChanger;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChatsActivity extends AppCompatActivity {

    private ActivityChatsBinding binding;
    private DatabaseReference databaseReference;
    String senderRoom, recieverRoom;

    private SessionManager sessionManager;
    private MessAdapter messAdapter;
    private BroadcastReceiver broadcastReceiver;
    String token, adminId, userId;
    String messege;

    private ArrayList<MessegeDetails> messegeDetailsArrayList;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseReference = FirebaseDatabase.getInstance().getReference("chats");
        broadcastReceiver = new NetworkChanger();
        registerNetworkChangeReceiver();


        sessionManager = new SessionManager(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        messegeDetailsArrayList = new ArrayList<>();


        adminId = String.valueOf(sessionManager.getAdminUserId());
        userId = FirebaseAuth.getInstance().getUid();


        senderRoom = userId + adminId;

        recieverRoom = adminId + userId;


//        Toast.makeText(this, "admin" + sessionManager.getAdminFcmToken(), Toast.LENGTH_SHORT).show();


        databaseReference.child(senderRoom)
                .child("messeges").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists())
                        {
                            messegeDetailsArrayList.clear();

                            for (DataSnapshot dataSnapshot : snapshot.getChildren())
                            {
                                MessegeDetails messegeDetails = dataSnapshot.getValue(MessegeDetails.class);
                                messegeDetailsArrayList.add(messegeDetails);
//                                Toast.makeText(ChatsActivity.this, ""+messegeDetails.getMessege(), Toast.LENGTH_SHORT).show();
                            }

                            messAdapter =new MessAdapter(ChatsActivity.this,messegeDetailsArrayList);

                            binding.recyclerMess.setAdapter(messAdapter);
                            binding.recyclerMess.setLayoutManager(new LinearLayoutManager(ChatsActivity.this));
                            messAdapter.notifyDataSetChanged();

                        }
                        else {
                            Toast.makeText(ChatsActivity.this, "no data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(ChatsActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messege = binding.editText.getText().toString();
                Date date = new Date();
                String randomKey = firebaseDatabase.getReference().push().getKey();
                binding.editText.setText("");


                MessegeDetails messegeDetails1 = new MessegeDetails(messege, userId, randomKey);
                databaseReference.child(senderRoom).child("messeges").child(randomKey).setValue(messegeDetails1)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                databaseReference.child(recieverRoom)
                                        .child("messeges")
                                        .child(randomKey)
                                        .setValue(messegeDetails1)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {


                                                getToken();

                                                Toast.makeText(ChatsActivity.this, "data send successfully", Toast.LENGTH_SHORT).show();

                                            }
                                        });

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ChatsActivity.this, " no data send successfully", Toast.LENGTH_SHORT).show();


                            }
                        });


            }
        });

        binding.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getToken() {

        DatabaseReference adminDetailRef;

        adminDetailRef = FirebaseDatabase.getInstance().getReference("AdminDetail");
        adminDetailRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Admin admin = dataSnapshot.getValue(Admin.class);


                        onSendNotification(admin.getFcmToken(), sessionManager.getNaame(), messege,"messege");


//

                    }


                } else {

                    Toast.makeText(ChatsActivity.this, "No user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


                Toast.makeText(ChatsActivity.this, "database error" + error.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });


    }

    private void onSendNotification(String token, String name, String order , String notificationType) {

        try {

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            String url = "https://fcm.googleapis.com/fcm/send";
            JSONObject jsonObject = new JSONObject();

//            jsonObject.put("title", name);
//            jsonObject.put("body", order);
            jsonObject.put("title", name);
            jsonObject.put("body", order);
            jsonObject.put("data", new JSONObject().put("notification_type", notificationType));


            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("notification", jsonObject);
            jsonObject1.put("to", token);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, jsonObject1,
                    new com.android.volley.Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            //     Toast.makeText(PaymentActivity.this, "notifications send  " + response.toString(), Toast.LENGTH_SHORT).show();

                            Log.d("Notification", "sent notification");
                        }

                    }, error -> {

                Log.d("Notification", "sent not notification");
                Toast.makeText(this, "not send " + error.networkResponse, Toast.LENGTH_SHORT).show();
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<>();
                    String key = "key=AAAAbLbshB4:APA91bGnUYsH35Uw2Knc0bABVFfAPkQZGg5F2YyUbgFsh9xe6bQb5uSm3QI_nH0alHATCT8mRkPNkweJsk5BoaB344dz6sgiFjKtTPBPo6pSuhsbZ-CdpccR5SuXSBuc5yvmOcetmxY9";
                    map.put("Content-type", "application/json");
                    map.put("Authorization", key);


                    return map;
                }
            };
            requestQueue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    @Override
    protected void onResume() {
        super.onResume();

        InternetChecker internetChecker = new InternetChecker(ChatsActivity.this);
        if (!internetChecker.isConnected()) {

            internetChecker.showInternetDialog();
        }
    }

    private void registerNetworkChangeReceiver() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadcastReceiver, filter);
    }

    private void unregisterNetworkChangeReceiver() {
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNetworkChangeReceiver();
    }
}