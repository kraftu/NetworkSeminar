package ru.tinkoff.ru.seminar.retrofit;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.tinkoff.ru.seminar.BookServer;
import ru.tinkoff.ru.seminar.model.Book;


public class ApiServerRetrofit implements BookServer {
    private Api api;

    public ApiServerRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fakerestapi.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    @Override
    public List<Book> getAllBooks() throws Exception {
        Response<List<Book>> response = api.getAllBooks().execute();
        checkSuccess(response);
        return response.body();
    }

    @Override
    public Book getBookById(int id) throws Exception {
        Response<Book> response = api.getBookById(id).execute();
        checkSuccess(response);
        return response.body();
    }
    private void checkSuccess(Response response) throws IOException {
        if (!response.isSuccessful()) {
            throw new IOException("Http status:" + response.message());
        }
    }
}
