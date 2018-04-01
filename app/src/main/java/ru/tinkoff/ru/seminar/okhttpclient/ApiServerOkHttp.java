package ru.tinkoff.ru.seminar.okhttpclient;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import ru.tinkoff.ru.seminar.BookServer;
import ru.tinkoff.ru.seminar.model.Book;


//http://fakerestapi.azurewebsites.net/swagger/ui/index
@SuppressWarnings({"ConstantConditions", "unused"})
public class ApiServerOkHttp implements BookServer {
    private OkHttpClient client;
    private Gson gson;

    public ApiServerOkHttp() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        client = new OkHttpClient.Builder()
                .addNetworkInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        gson = new Gson();
    }

    //http://fakerestapi.azurewebsites.net/api/Books
    @Override
    public List<Book> getAllBooks() throws IOException {
        Request request = new Request.Builder()
                .url("http://fakerestapi.azurewebsites.net/api/Books")
                .get()
                .build();

        Response response = client.newCall(request).execute();

        checkSuccess(response);

        String dataFromServer = response.body().string();
        Type itemsListType = new TypeToken<List<Book>>() {}.getType();
        List<Book> listBook;
        try {
            listBook = gson.fromJson(dataFromServer, itemsListType);
        } catch (JsonSyntaxException jException) {
            throw new IOException("Not expected data format" + jException.getMessage());
        }

        return listBook;
    }
    // http://fakerestapi.azurewebsites.net/api/Books/{id}
    @Override
    public Book getBookById(int id) throws IOException {
        //String urlById = "http://fakerestapi.azurewebsites.net/api/Books/"+id;
        HttpUrl httpUrl = HttpUrl.parse("http://fakerestapi.azurewebsites.net/api/Books")
                .newBuilder()
                .addPathSegment(String.valueOf(id)).build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .get()
                .build();

        Response response = client.newCall(request).execute();

        checkSuccess(response);

        String dataFromServer = response.body().string();
        Type itemsListType = new TypeToken<Book>() {}.getType();
        Book book;
        try {
            book = gson.fromJson(dataFromServer, itemsListType);
        } catch (JsonSyntaxException jException) {
            throw new IOException("Not expected data format" + jException.getMessage());
        }
        return book;
    }
    private void checkSuccess(Response response) throws IOException {
        if (!response.isSuccessful()) {
            throw new IOException("Http status:" + response.message());
        }
    }
}
