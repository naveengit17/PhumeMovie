package com.example.phumemovie.data.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;
import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

public class NetworkInterceptor implements Interceptor {

    Context mContext;

    public NetworkInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if(!isNetworkAvailable()) {
            Toast.makeText(mContext, "Make sure you have active data connection", Toast.LENGTH_LONG).show();
        }
        return chain.proceed(chain.request());
    }

    private Boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                mContext.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
