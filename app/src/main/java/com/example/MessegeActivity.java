package com.example;

import static com.example.huts.messeges.Values.TO;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.huts.R;
import com.example.huts.adapters.ChildAdapter;
import com.example.huts.adapters.ParentAdpter;
import com.example.huts.databinding.ActivityMainBinding;
import com.example.huts.databinding.ActivityMessegeBinding;
import com.example.huts.messeges.ApiUtilsClass;
import com.example.huts.messeges.NotificationsData;
import com.example.huts.messeges.PushNotifications;
import com.example.huts.model.ChildItem;
import com.example.huts.model.ParentItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessegeActivity extends AppCompatActivity {

    ActivityMessegeBinding binding;
    String title, messege;

     private  ParentAdpter parentAdpter ;
     private  ArrayList<ParentItem> parentItemArrayList;
     private ArrayList<ChildItem> childItemArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessegeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        String [] orderId = {"3242c234","234c23432","2c42c424"};
        String [] itemName = {"burger","singer","2c42c424","sandwich"};
        int [] imageIId = {R.drawable.cart,R.drawable.chaneychat, R.drawable.aluanda};

        parentItemArrayList = new ArrayList<>();
        childItemArrayList = new ArrayList<>();

//
//        for (int i = 0 ;i <orderId.length;i++)
//        {
//            ParentItem parentItem = new ParentItem(orderId[i],String.valueOf(i+14),String.valueOf(i*43) ,imageIId[i]);
//            parentItemArrayList.add(parentItem);
//            if (i<itemName.length)
//            {
//                ChildItem childItem = new ChildItem(itemName[i],String.valueOf(i+5),String.valueOf(i*15),imageIId[i]);
//                childItemArrayList.add(childItem);
//            }
////        }
//        parentAdpter = new ParentAdpter(this,parentItemArrayList,childItemArrayList);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        binding.recyerl.setLayoutManager(linearLayoutManager);
//        binding.recyerl.setAdapter(parentAdpter);

//
//        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(
//                        new OnCompleteListener<String>() {
//                            @Override
//                            public void onComplete(@NonNull Task<String> task) {
//                                if (task.isSuccessful()) {
//                                    String token = task.getResult();
//                                    binding.editextNametitler.setText(token);
//                                    Toast.makeText(MessegeActivity.this, "" + task, Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(MessegeActivity.this, "no tekn", Toast.LENGTH_SHORT).show();
//
//                                }
//                            }
//                        }
//
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                        Toast.makeText(MessegeActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//        binding.btnSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                messege = binding.editextName.getText().toString();
//                title = binding.editextNametitler.getText().toString();
//                sendNot();
//            }
//        });
//
//        msg();
    }

    private void sendNot() {
        ApiUtilsClass.getClient().sendNotifications(new PushNotifications(new NotificationsData(title, messege), TO))
                .enqueue(new Callback<PushNotifications>() {
                    @Override
                    public void onResponse(Call<PushNotifications> call, Response<PushNotifications> response) {

                        if (response.isSuccessful()) {
                            Toast.makeText(MessegeActivity.this, "notifications send    ", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(MessegeActivity.this, "notifications send  failed  ", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<PushNotifications> call, Throwable t) {

                        Toast.makeText(MessegeActivity.this, "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void msg() {


        FirebaseMessaging.getInstance()
                .subscribeToTopic("All");
    }
}