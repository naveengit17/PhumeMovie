package com.example.phumemovie.data.network;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    static RetrofitServer mRetrofitServer = null;

    public static RetrofitServer getService(NetworkInterceptor networkInterceptor) {
        if (mRetrofitServer == null) {

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                                        .addInterceptor(networkInterceptor).build();
            mRetrofitServer = new Retrofit.Builder()
                                .baseUrl(RetrofitServer.BASE_URL)
                                .client(okHttpClient)
                                .addConverterFactory(GsonConverterFactory.create())
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                .build()
                                .create(RetrofitServer.class);
        }
        return mRetrofitServer;
    }
}
