package ru.tinkoff.ru.seminar.retrofit;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.tinkoff.ru.seminar.model.Book;


public interface Api {

    @GET("api/Books")
    Single<List<Book>> getAllBooks();

    @GET("api/Books/{id}")
    Single<Book> getBookById(@Path("id") int id);
}
