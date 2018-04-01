package ru.tinkoff.ru.seminar;

import java.util.List;

import io.reactivex.Single;
import ru.tinkoff.ru.seminar.model.Book;


// Api для книг http://fakerestapi.azurewebsites.net/swagger/ui/index#/
@SuppressWarnings("SameParameterValue")
public interface BookServer {

    Single<List<Book>> getAllBooks();

    Single<Book> getBookById(int id);
}
