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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMessegeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}