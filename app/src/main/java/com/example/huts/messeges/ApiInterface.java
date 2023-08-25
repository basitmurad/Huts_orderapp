package com.example.huts.messeges;

import static com.example.huts.messeges.Values.CONTENT_TYPES;
import static com.example.huts.messeges.Values.SERVER_KEY;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {


    @Headers({"Authorization: "+SERVER_KEY , "COntent-type:" + CONTENT_TYPES})
    @POST("fcm/send")
    Call<PushNotifications> sendNotifications(@Body PushNotifications pushNotifications);
}
