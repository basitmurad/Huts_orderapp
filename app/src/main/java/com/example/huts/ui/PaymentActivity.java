package com.example.huts.ui;

import static android.content.ContentValues.TAG;


import static com.example.huts.messeges.Values.TOPIC_ADMIN;
import static com.google.firebase.messaging.reporting.MessagingClientEvent.MessageType.TOPIC;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.huts.SessionManager;


import com.example.huts.databinding.ActivityPaymentBinding;

import com.example.huts.model.OrderData;
import com.example.huts.model.OrderDetails;
import com.example.huts.model.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


public class PaymentActivity extends AppCompatActivity {


    private ActivityPaymentBinding binding;
    private SessionManager sessionManager;
    private ProgressDialog progressDialog;

    private int total;
    private String fcmToken, userName;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
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
        total = (int) dbHelper.calculateTotalNewPrice();


        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.editextDelivery.getText().toString().isEmpty()) {
                    Toast.makeText(PaymentActivity.this, "Please enter you detail", Toast.LENGTH_SHORT).show();
                } else {

                    String address = binding.editextDelivery.getText().toString();
                    progressDialog.show();
                    Toast.makeText(PaymentActivity.this, "orders placed successfully", Toast.LENGTH_SHORT).show();
                    DbHelper dbHelper = new DbHelper(PaymentActivity.this);
                    ArrayList<OrderDetails> orderDetailsList = dbHelper.getAll();
                    total = (int) dbHelper.calculateTotalNewPrice();


                    // Send data to Firebase
                    DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("ActiveOrders");

                    OrderData orderData = new OrderData(hut, userId, pushID, orderId, address, total, orderDetailsList, true);
                    ordersRef.child(userId).child(pushID).setValue(orderData)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    progressDialog.dismiss();


                                    databaseReference.child(userId).setValue(orderData)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    dbHelper.deleteAllOrders();
                                                    getToken();
                                                    Toast.makeText(PaymentActivity.this, "Order Placed successfully", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(PaymentActivity.this, DashboardActivity.class));

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
                                    Toast.makeText(PaymentActivity.this, "Failed Try again.." +e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });


    }


    private void getToken() {

        DatabaseReference adminDetailRef = FirebaseDatabase.getInstance().getReference("AdminDetail");
        DatabaseReference userRef = adminDetailRef.child("fsWmnv6WAIc8PFvBRhpYBHfQRAA3"); // Replace with the actual userId

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    fcmToken = dataSnapshot.child("fcmToken").getValue(String.class);
                    sessionManager.setAdminFcmToken(fcmToken);
                    onSendNotification(fcmToken, sessionManager.getNaame(), "Place an order");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
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

                            Toast.makeText(PaymentActivity.this, "" + response.toString(), Toast.LENGTH_SHORT).show();
                            // showDriverDialog(order);
                            // saveNotifData(order, new NotifFirebase(order.getUserId(), name, send_you_and_interest, String.valueOf(System.currentTimeMillis()), String.valueOf(UUID.randomUUID().toString())));
                            Log.d("Notification", "sent notification");
                        }

                    }, error -> {
                //    showDriverDialog(order);
                //   saveNotifData(order, new NotifFirebase(order.getUserId(), name, send_you_and_interest, String.valueOf(System.currentTimeMillis()), String.valueOf(UUID.randomUUID().toString())));
                Log.d("Notification", "sent not notification");
                Toast.makeText(this, "" + error.networkResponse, Toast.LENGTH_SHORT).show();
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

    public static String generateRandomNumber(int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10); // Generate a random digit (0 to 9)
            stringBuilder.append(digit);
        }

        return stringBuilder.toString();
    }


}