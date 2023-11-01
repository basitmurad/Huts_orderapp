package com.afaq.huts.ui;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.afaq.huts.databinding.ActivityPaymentBinding;
import com.afaq.utils.GetDateTime;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.afaq.huts.Admin;
import com.afaq.huts.SessionManager;


import com.afaq.huts.model.ActiveOrderUsers;
import com.afaq.huts.model.OrderData;
import com.afaq.huts.model.OrderDetails;
import com.afaq.utils.InternetChecker;
import com.afaq.utils.NetworkChanger;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


public class PaymentActivity extends AppCompatActivity {


    private ActivityPaymentBinding binding;
    private SessionManager sessionManager;
    private ProgressDialog progressDialog;
    private BroadcastReceiver broadcastReceiver;
    private int total;


    private DatabaseReference databaseReference;

    private String hutName  ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());


        hutName = getIntent().getStringExtra("hut");



        broadcastReceiver = new NetworkChanger();
        registerNetworkChangeReceiver();

        sessionManager = new SessionManager(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Placing Order...");
        progressDialog.setCancelable(false);



        databaseReference = FirebaseDatabase.getInstance().getReference("LatestOrders");


        String userId = FirebaseAuth.getInstance().getUid();
        String pushID = UUID.randomUUID().toString();
        String hut = sessionManager.getHutName();

        String orderId = generateRandomNumber(16);

        DbHelper dbHelper = new DbHelper(PaymentActivity.this);
//        total = (int) dbHelper.calculateTotalNewPrice();

        

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }


        });






        binding.btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
                String dateString = dateFormat. format(new Date()). toString();
//                Toast.makeText(PaymentActivity.this, ""+dateString, Toast.LENGTH_SHORT).show();


                if (binding.editextDelivery.getText().toString().isEmpty()) {
                    Toast.makeText(PaymentActivity.this, "Please enter you detail address", Toast.LENGTH_SHORT).show();
                } else {

                    String address = binding.editextDelivery.getText().toString();
                    progressDialog.show();
                    //   Toast.makeText(PaymentActivity.this, "orders placed successfully", Toast.LENGTH_SHORT).show();
                    DbHelper dbHelper = new DbHelper(PaymentActivity.this);
                    ArrayList<OrderDetails> orderDetailsList = dbHelper.getAll();
                    total = (int) dbHelper.calculateTotalNewPrice();

                    int total1 = total + 50;


                    // Send data to Firebase
                    DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("ActiveOrders");

                    OrderData orderData = new OrderData(hutName, userId, pushID, orderId, address, total1, orderDetailsList, true );
                    ordersRef.child(userId).child(pushID).setValue(orderData)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {


                                    databaseReference.child(userId).setValue(orderData)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    dbHelper.deleteAllOrders();
                                                    DatabaseReference activeOrder = FirebaseDatabase.getInstance().getReference("ActiveOrdersUser");


                                                    ActiveOrderUsers activeOrderUsers = new ActiveOrderUsers(sessionManager.getNaame(), sessionManager.getEmail(), sessionManager.getNumber(), userId, true ,dateString);


//                                                    Toast.makeText(PaymentActivity.this, ""+currentTimestamp, Toast.LENGTH_SHORT).show();

                                                    activeOrder.child(orderData.getUserId())
                                                            .setValue(activeOrderUsers).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {

                                                                    getToken();
//                                                                    Toast.makeText(PaymentActivity.this, ""+dateString, Toast.LENGTH_SHORT).show();
//                                                                    Toast.makeText(PaymentActivity.this, "Order Placed successfully", Toast.LENGTH_SHORT).show();
//                                                                    startActivity(new Intent(PaymentActivity.this, DashboardActivity.class));
                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {

                                                                    Toast.makeText(PaymentActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            });


                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(PaymentActivity.this, "Exception" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                                                }
                                            });


                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    // Handle failure
                                    Toast.makeText(PaymentActivity.this, "Failed Try again.." + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });


    }

    private void fetchAdminData() {

        DatabaseReference adminDetailRef;

        adminDetailRef = FirebaseDatabase.getInstance().getReference("AdminDetail");
        adminDetailRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Admin admin = dataSnapshot.getValue(Admin.class);
                        Toast.makeText(PaymentActivity.this, "" + admin.getFcmToken() + admin.getName(), Toast.LENGTH_SHORT).show();

                    }


                } else {

                    Toast.makeText(PaymentActivity.this, "No user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


                Toast.makeText(PaymentActivity.this, "database error" + error.getMessage(), Toast.LENGTH_SHORT).show();


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

//                        onSendNotification(admin.getFcmToken(), sessionManager.getNaame(), "Place an Order");

                        onSendNotification(sessionManager.getAdminFcmToken(), sessionManager.getNaame(), "Place an Order");
//                        Toast.makeText(PaymentActivity.this, ""+sessionManager.getAdminFcmToken(), Toast.LENGTH_SHORT).show();
                    }


                } else {

                    Toast.makeText(PaymentActivity.this, "No user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


                Toast.makeText(PaymentActivity.this, "database error" + error.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });


    }


    private void onSendNotification(String token, String name, String order) {

        try {

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            String url = "https://fcm.googleapis.com/fcm/send";
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("title", name);
            jsonObject.put("body", order);


            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("notification", jsonObject);
            jsonObject1.put("to", token);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, jsonObject1,
                    new com.android.volley.Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            //     Toast.makeText(PaymentActivity.this, "notifications send  " + response.toString(), Toast.LENGTH_SHORT).show();

                            progressDialog.dismiss();
                            finish();
                            Toast.makeText(PaymentActivity.this, "Order Placed successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(PaymentActivity.this, DashboardActivity.class));
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

    public static String generateRandomNumber(int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10); // Generate a random digit (0 to 9)
            stringBuilder.append(digit);
        }

        return stringBuilder.toString();
    }

    @Override
    protected void onResume() {
        super.onResume();

        InternetChecker internetChecker = new InternetChecker(PaymentActivity.this);
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