package com.appzone.validate.remote;

import com.appzone.validate.services.Services;
import com.appzone.validate.tags.Tags;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    private static Retrofit instance=null;

    private static synchronized Retrofit getRetrofit()
    {
        if (instance==null)
        {
            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Request  r  = request.newBuilder().addHeader("ACCEPT","application/json")
                            .build();
                    return chain.proceed(r);
                }
            };
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(1,TimeUnit.MINUTES)
                    .readTimeout(1,TimeUnit.MINUTES)
                    .writeTimeout(1,TimeUnit.MINUTES)
                    .retryOnConnectionFailure(true)
                    .addInterceptor(interceptor)
                    .build();
            Gson gson = new GsonBuilder().create();

            instance = new Retrofit.Builder()
                    .baseUrl(Tags.BaseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return instance;
    }


    public static Services getServices()
    {
        return getRetrofit().create(Services.class);
    }
}
