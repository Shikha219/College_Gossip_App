package com.example.shikha.collegegossip.Fragments;

import com.example.shikha.collegegossip.Notifications.MyResponse;
import com.example.shikha.collegegossip.Notifications.Sender;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers({
            "Content-type:application/json",
            "Authorization:key=AAAAgAJ68PY:APA91bFvfBdltW4aVoumXpEtZC9cr7mJMS0Y-609DdLrVC5mIzyEMhk8SkxTl8_CdW50a3D4ysvwKKCX-naywFy7vpIrXOrKuSuQn3O2mRydgNMHO52Hj5atNBfPhJ8agiq14079lrcH"
    })

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
