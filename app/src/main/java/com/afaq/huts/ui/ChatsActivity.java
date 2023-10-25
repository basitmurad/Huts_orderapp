package com.afaq.huts.ui;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.afaq.huts.databinding.ActivityChatsBinding;
import com.afaq.huts.model.Senders;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.afaq.huts.Admin;
import com.afaq.huts.SessionManager;

import com.afaq.huts.adapters.MessAdapter;
import com.afaq.huts.model.HideKeyBoard;
import com.afaq.huts.model.MessegeDetails;

import com.afaq.utils.InternetChecker;
import com.afaq.utils.NetworkChanger;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChatsActivity extends AppCompatActivity {

    private ActivityChatsBinding binding;
    private DatabaseReference databaseReference, senderRef, adminRef;
    String senderRoom, recieverRoom;

    private SessionManager sessionManager;
    private MessAdapter messAdapter;
    private BroadcastReceiver broadcastReceiver;
    String token, adminId, userId;
    String messege;
    private Date date;

    private ArrayList<MessegeDetails> messegeDetailsArrayList;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        messegeDetailsArrayList = new ArrayList<>();

//        setListner();
        //  messegeDetailsArrayList.clear();
        databaseReference = FirebaseDatabase.getInstance().getReference("chats");
        senderRef = FirebaseDatabase.getInstance().getReference("Sender");
        adminRef = FirebaseDatabase.getInstance().getReference("SenderAdmin");


        date = new Date();
        String id = getIntent().getStringExtra("id");


        broadcastReceiver = new NetworkChanger();
        registerNetworkChangeReceiver();
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }


                    token = task.getResult();
                });

        sessionManager = new SessionManager(this);
        firebaseDatabase = FirebaseDatabase.getInstance();


        adminId = String.valueOf(sessionManager.getAdminUserId());
        adminRef.child(adminId).child("read").setValue(false);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            userId = auth.getCurrentUser().getUid();
            // Rest of your code
        }


        senderRoom = userId + adminId;

        recieverRoom = adminId + userId;


        databaseReference.child(senderRoom).child("messages").orderByChild("timestamp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    messegeDetailsArrayList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        MessegeDetails messegeDetails = dataSnapshot.getValue(MessegeDetails.class);
                        messegeDetailsArrayList.add(messegeDetails); // Add messages at the beginning of the list
                    }

                    messAdapter = new MessAdapter(ChatsActivity.this, messegeDetailsArrayList);

                    binding.recyclerMess.setAdapter(messAdapter);


                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatsActivity.this);
                    linearLayoutManager.setReverseLayout(false);
                    binding.recyclerMess.setLayoutManager(linearLayoutManager);

                    messAdapter.notifyDataSetChanged();

                    // Scroll to the last item (latest message)
                    if (messegeDetailsArrayList.size() > 0) {
                        binding.recyclerMess.scrollToPosition(messegeDetailsArrayList.size() - 1);
                    }
                } else {
                    Toast.makeText(ChatsActivity.this, "No Messages", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChatsActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messege = binding.editText.getText().toString();
//                Date date = new Date();

                String randomKey = firebaseDatabase.getReference().push().getKey();

                HideKeyBoard.hideKeyboard(ChatsActivity.this);

                binding.editText.setText("");


                MessegeDetails messegeDetails1 = new MessegeDetails(messege, userId, randomKey, date.getTime());
                databaseReference.child(senderRoom).child("messages").child(randomKey).setValue(messegeDetails1)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                databaseReference.child(recieverRoom)
                                        .child("messages")
                                        .child(randomKey)
                                        .setValue(messegeDetails1)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                                getToken();


                                            }
                                        });

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ChatsActivity.this, "Error " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


                            }
                        });


            }
        });

        binding.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(ChatsActivity.this, DashboardActivity.class));
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        HideKeyBoard.hideKeyboard(ChatsActivity.this);


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
//                        Date date1 = new Date();
//
//                        long time = date1.getTime();


//                        Senders senders = new Senders(sessionManager.getNaame(), sessionManager.getEmail(), userId, token, sessionManager.getNumber(), date1.getTime(), true );

//                        Toast.makeText(ChatsActivity.this, ""+admin.getFcmToken(), Toast.LENGTH_SHORT).show();
                        Senders senders = new Senders(sessionManager.getNaame(), sessionManager.getEmail(), userId, token
                                , sessionManager.getNumber(), true, date.getTime());
                        senderRef.child(userId)
                                .setValue(senders).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        onSendNotification(admin.getFcmToken(), sessionManager.getNaame(), messege, "messege");

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ChatsActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                                    }
                                });


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

    private void onSendNotification(String token, String name, String body, String notificationType) {

        try {

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            String url = "https://fcm.googleapis.com/fcm/send";
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("title", name);
            jsonObject.put("body", body);
            jsonObject.put("data", new JSONObject().put("notification_type", notificationType));


            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("notification", jsonObject);
            jsonObject1.put("to", token);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, jsonObject1,
                    new com.android.volley.Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Toast.makeText(ChatsActivity.this, "Message send", Toast.LENGTH_SHORT).show();

                            Log.d("Notification", "sent notification");
                        }

                    }, error -> {

                Log.d("Notification", "sent not notification");
                Toast.makeText(this, "not send " + error.networkResponse, Toast.LENGTH_SHORT).show();
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<>();
                    String key = "key=AAAAZqPVNqc:APA91bEn6NINzxkjSXCo6nHQ60cJD0JY0EpqUBpxkkEUX2Fx7ZpO79NeoDI39kS3vWUevsND5l3JmW3d15X3uEJtK9T74pETeQJiLervbPDjjJfZ671Cd55kf023issCy07zrbez7X-t";
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

        if (messAdapter != null)
            messAdapter.notifyDataSetChanged();


        HideKeyBoard.hideKeyboard(ChatsActivity.this);
        adminRef.child(adminId).child("read").setValue(false);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        adminRef.child(adminId).child("read").setValue(false);

        startActivity(new Intent(ChatsActivity.this, DashboardActivity.class));

    }
}