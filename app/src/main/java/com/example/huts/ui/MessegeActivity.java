package com.example.huts.ui;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.Notification;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.huts.SessionManager;
import com.example.huts.databinding.ActivityMessegeBinding;
import com.example.huts.messeges.ApiInterface;
import com.example.huts.messeges.ApiUtilsClass;
import com.example.huts.messeges.NotificationData;
import com.example.huts.messeges.PushNotification;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessegeActivity extends AppCompatActivity {

    ActivityMessegeBinding binding;
    NotificationManagerCompat notificationManagerCompat;
    Notification notification;
private   String fcmToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMessegeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseMessaging.getInstance().subscribeToTopic("admin");


//        binding.btnsend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    NotificationChannel notificationChannel = new NotificationChannel("hello", "hello", NotificationManager.IMPORTANCE_DEFAULT);
//                    NotificationManager notificationManager = getSystemService(NotificationManager.class);
//                    notificationManager.createNotificationChannel(notificationChannel);
//
//                }
//
//                Intent intent = new Intent(MessegeActivity.this, DashboardActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                PendingIntent pendingIntent = PendingIntent.getActivity(MessegeActivity.this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);  // Add FLAG_IMMUTABLE here
//
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(MessegeActivity.this, "hello")
//                        .setSmallIcon(R.drawable.huts)
//                        .setContentTitle("Title")
//                        .setContentText("First line of the notification body")
//                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Large block of text for the notification body"))  // Optionally, set a large text body
//                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                        .setContentIntent(pendingIntent);  // Set the pending intent
//
//                notification = builder.build();
//                notificationManagerCompat = NotificationManagerCompat.from(MessegeActivity.this);
//
//                push();
//            }
//        });
//        binding.btnsend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    NotificationChannel notificationChannel = new NotificationChannel("hello", "hello", NotificationManager.IMPORTANCE_DEFAULT);
//                    NotificationManager notificationManager = getSystemService(NotificationManager.class);
//                    notificationManager.createNotificationChannel(notificationChannel);
//
//                }
//
//                Intent intent = new Intent(MessegeActivity.this, DashboardActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//                PendingIntent pendingIntent = PendingIntent.getActivity(MessegeActivity.this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);  // Add FLAG_IMMUTABLE here
//
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(MessegeActivity.this, "hello")
//                        .setSmallIcon(R.drawable.huts)
//                        .setContentTitle("Title")
//                        .setContentText("First line of the notification body")
//                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Large block of text for the notification body"))  // Optionally, set a large text body
//                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                        .setContentIntent(pendingIntent);  // Set the pending intent
//
//                notification = builder.build();
//                notificationManagerCompat = NotificationManagerCompat.from(MessegeActivity.this);
//
//                push();            }
//        });


        binding.btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionManager sessionManager = new SessionManager(MessegeActivity.this);
                String token  = sessionManager.getAdminFcmToken();
         //       Toast.makeText(MessegeActivity.this, ""+sessionManager.getAdminFcmToken(), Toast.LENGTH_SHORT).show();

         getToken();

            }
        });


    }
    private void getToken( ) {

        DatabaseReference adminDetailRef = FirebaseDatabase.getInstance().getReference("AdminDetail");
        DatabaseReference userRef = adminDetailRef.child("fsWmnv6WAIc8PFvBRhpYBHfQRAA3"); // Replace with the actual userId

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    fcmToken = dataSnapshot.child("fcmToken").getValue(String.class);

                    String name = "Basit";
//                    sessionManager.setAdminFcmToken(fcmToken);
//                    Toast.makeText(MessegeActivity.this, ""+fcmToken, Toast.LENGTH_SHORT).show();
                    onSendNotification(fcmToken ,name);
                    //          Toast.makeText(DashboardActivity.this, ""+fcmToken, Toast.LENGTH_SHORT).show();
                    // Use the fcmToken as needed (e.g., sending notifications)
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });



    }



    private void onSendNotification(String token, String name) {

        try {

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            String url = "https://fcm.googleapis.com/fcm/send";
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("title", name);
//            jsonObject.put("body", send_you_and_interest);


            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("notification", jsonObject);
            jsonObject1.put("to", token);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, jsonObject1,
                    new com.android.volley.Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Toast.makeText(MessegeActivity.this, ""+response.toString(), Toast.LENGTH_SHORT).show();
                           // showDriverDialog(order);
                           // saveNotifData(order, new NotifFirebase(order.getUserId(), name, send_you_and_interest, String.valueOf(System.currentTimeMillis()), String.valueOf(UUID.randomUUID().toString())));
                            Log.d("Notification", "sent notification");
                        }

                    }, error -> {
            //    showDriverDialog(order);
             //   saveNotifData(order, new NotifFirebase(order.getUserId(), name, send_you_and_interest, String.valueOf(System.currentTimeMillis()), String.valueOf(UUID.randomUUID().toString())));
                Log.d("Notification", "sent not notification");
                Toast.makeText(this, ""+error.networkResponse, Toast.LENGTH_SHORT).show();
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



    private void sendNotificationToAdmin(String adminFCMToken) {
        // Create a NotificationData instance with your desired title and message
        NotificationData notificationData = new NotificationData("Admin Notification", "Hello Admin!");

        // Create a PushNotification instance with the notification data and the admin FCM token
        PushNotification pushNotification = new PushNotification(notificationData, adminFCMToken);

        // Get the API client
        ApiInterface apiInterface = ApiUtilsClass.getClient();

        // Call the sendNotification method to send the notification
        Call<PushNotification> call = apiInterface.sendNotification(pushNotification);
        call.enqueue(new Callback<PushNotification>() {
            @Override
            public void onResponse(Call<PushNotification> call, Response<PushNotification> response) {
                // Handle success
            }

            @Override
            public void onFailure(Call<PushNotification> call, Throwable t) {
                // Handle failure
            }
        });
    }


//    private void sendNotificationToAdmin() {
//        // Create a NotificationData instance with your desired title and message
//        NotificationData notificationData = new NotificationData("Admin Notification", "Hello Admin!");
//
//        // Create a PushNotification instance with the notification data and the admin topic
//        PushNotification pushNotification = new PushNotification(notificationData, TOPIC_ADMIN);
//
//        // Get the API client
//        ApiInterface apiInterface = ApiUtilsClass.getClient();
//
//        // Call the sendNotification method to send the notification
//        Call<PushNotification> call = apiInterface.sendNotification(pushNotification);
//        call.enqueue(new Callback<PushNotification>() {
//            @Override
//            public void onResponse(Call<PushNotification> call, Response<PushNotification> response) {
//                // Handle success
//            }
//
//            @Override
//            public void onFailure(Call<PushNotification> call, Throwable t) {
//                // Handle failure
//            }
//        });
//    }







//    private void onSendNotification(String name, String send_you_and_interest, String token, ActiveRides order) {
//        try {
//
//            RequestQueue requestQueue = Volley.newRequestQueue(this);
//
//            String url = "https://fcm.googleapis.com/fcm/send";
//            JSONObject jsonObject = new JSONObject();
//
//            jsonObject.put("title", name);
//            jsonObject.put("body", send_you_and_interest);
//
//
//            JSONObject jsonObject1 = new JSONObject();
//            jsonObject1.put("notification", jsonObject);
//            jsonObject1.put("to", token);
//
//
//            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, jsonObject1,
//                    new com.android.volley.Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//
//                            showDriverDialog(order);
//                            saveNotifData(order, new NotifFirebase(order.getUserId(), name, send_you_and_interest, String.valueOf(System.currentTimeMillis()), String.valueOf(UUID.randomUUID().toString())));
//                            Log.d("Notification", "sent notification");
//                        }
//
//                    }, error -> {
//                showDriverDialog(order);
//                saveNotifData(order, new NotifFirebase(order.getUserId(), name, send_you_and_interest, String.valueOf(System.currentTimeMillis()), String.valueOf(UUID.randomUUID().toString())));
//                Log.d("Notification", "sent not notification");
//            }) {
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String, String> map = new HashMap<>();
//                    String key = "key=AAAAGriD1uw:APA91bHV7PTVFTXFaCXBlgRrT8Lr8-G79rMZWb1aVDBCpphUykRKNNV73JH0nK8jEfsMqpzKRJ0rlxyS5-nAPkKHJKmoJ8wiMMElQRRM34TLJN4rv3WzmRvAtFk_J2aOsbP4f1_JEATu";
//                    map.put("Content-type", "application/json");
//                    map.put("Authorization", key);
//
//
//                    return map;
//                }
//            };
//            requestQueue.add(jsonObjectRequest);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//    }

    private void push() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManagerCompat.notify(1, notification);
    }
}