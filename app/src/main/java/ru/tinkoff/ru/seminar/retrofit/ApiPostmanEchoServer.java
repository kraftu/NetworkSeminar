package ru.tinkoff.ru.seminar.retrofit;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


//https://docs.postman-echo.com/
public class ApiPostmanEchoServer {
    private Api api;

    public ApiPostmanEchoServer() {
        //Логирование
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //OkHttpClient
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        //Gson парсер
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        //Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://postman-echo.com/")
                .build();

        //Api server
        api = retrofit.create(Api.class);
    }

    public Api getApi() {
        return api;
    }
}
