package com.karta.api;

import android.content.Context;
import com.facebook.flipper.android.AndroidFlipperClient;
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor;
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private final Context context;

    public ApiClient(Context context) {
        this.context = context;
    }

    // Emulator -> 10.0.2.2:8080/
    // Device -> IP Laptop:8080/
    private static final String BASE_URL = "http://10.0.2.2:8080/";

    private static Retrofit retrofit;

    public static Retrofit getClient(Context context) {

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(createOkHttpClient(context))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;

    }

    public UserService getUserService(){
       return getClient(context).create(UserService.class);
    }

    public static OkHttpClient createOkHttpClient(Context context) {
        return new OkHttpClient.Builder()
                .addInterceptor(new FlipperOkhttpInterceptor(AndroidFlipperClient.getInstance(context).getPlugin(
                        NetworkFlipperPlugin.ID))
                )
                .build();
    }
}
