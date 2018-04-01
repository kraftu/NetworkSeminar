package ru.tinkoff.ru.seminar.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.tinkoff.ru.seminar.model.Book;


public interface Api {

    @GET("api/Books")
    Call<List<Book>> getAllBooks();

    @GET("api/Books/{id}")
    Call<Book> getBookById(@Path("id") int id);
}
