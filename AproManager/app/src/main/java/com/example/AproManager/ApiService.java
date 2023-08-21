package com.example.AproManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    private static ApiService instance;
    private final MyApi myApi;

    private ApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://5696-103-134-127-176.ngrok-free.app/") // backend URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myApi = retrofit.create(MyApi.class);
    }

    public static synchronized ApiService getInstance() {
        if (instance == null) {
            instance = new ApiService();
        }
        return instance;
    }

    public void storeUserData(UserData userData, Callback<Void> callback) {
        Call<Void> call = myApi.storeUserData(userData);
        call.enqueue(callback);
    }

    public void checkUser(String email, Callback<Boolean> callback) {
        Call<Boolean> call = myApi.checkUser(email);
        call.enqueue(callback);
    }
}
