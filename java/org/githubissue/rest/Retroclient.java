package org.githubissue.rest;

import org.githubissue.ext.SLog;
import org.githubissue.ext.Utils;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created on 9/21/2018.
 */
public class Retroclient {

    private Retrofit mRetrofit;

    public Retrofit getClient() throws WsException {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);
        try {
            if (mRetrofit == null) {
                mRetrofit = new Retrofit.Builder()
                        .baseUrl("https://api.github.com/")
                        .client(builder.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
        } catch (Exception e) {
            SLog.e(Utils.TAG, e.getMessage());
            throw new WsException("Base URL is not connecting", e);
        }

        return mRetrofit;
    }
}
