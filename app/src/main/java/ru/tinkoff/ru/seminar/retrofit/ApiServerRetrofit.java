package ru.tinkoff.ru.seminar.retrofit;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.tinkoff.ru.seminar.BookServer;
import ru.tinkoff.ru.seminar.model.Book;
import ru.tinkoff.ru.seminar.model.JsonBookDeserializer;
import ru.tinkoff.ru.seminar.model.JsonBookSerializer;


public class ApiServerRetrofit implements BookServer {
    private Api api;

    public ApiServerRetrofit() {
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
        gsonBuilder.registerTypeAdapter(Book.class, new JsonBookSerializer());
        gsonBuilder.registerTypeAdapter(Book.class, new JsonBookDeserializer());

        Gson gson = gsonBuilder.create();

        //Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://fakerestapi.azurewebsites.net/")
                .build();

        //Api server
        api = retrofit.create(Api.class);
    }

    @Override
    public Single<List<Book>> getAllBooks() {
        return api.getAllBooks();
    }

    @Override
    public Single<Book> getBookById(int id) {
        return api.getBookById(id);
    }
}
