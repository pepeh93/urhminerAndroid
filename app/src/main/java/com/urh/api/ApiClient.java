package com.urh.api;

import android.content.Context;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiClient {
    public static String baseUrl = "https://urhminer.ml/api/";
    public static String imagesUrl = "https://urhminer.ml/storage/uploads/";

    private static ApiService mRestService = null;
    public static ApiService getClient(Context context) {
        if (mRestService == null) {
            final OkHttpClient client = new OkHttpClient
                    .Builder()
                    .build();
            final Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .client(client)
                    .build();
            mRestService = retrofit.create(ApiService.class);
        }
        return mRestService;
    }
}