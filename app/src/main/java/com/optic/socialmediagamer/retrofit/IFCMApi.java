package com.optic.socialmediagamer.retrofit;

import com.optic.socialmediagamer.models.FCMBody;
import com.optic.socialmediagamer.models.FCMResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMApi {

    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAA5suB6X0:APA91bFI5rx5GoNPJSGPqH3Rbao597gOyk9UYuAt9sVqCBKuGU7JeqXyZYFUPdjf3AmlSPdBeH7l_nnf97Y3Of_vJ8CX7BlPBCbqRN-mj73uSUDDdVhTuO0iGmzn8Kj949CdBONxb5iK"
    })
    @POST("fcm/send")
    Call<FCMResponse> send(@Body FCMBody body);
}
