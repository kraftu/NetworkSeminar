package ru.tinkoff.ru.seminar;

import java.util.List;

import ru.tinkoff.ru.seminar.model.Book;


// Api для книг http://fakerestapi.azurewebsites.net/swagger/ui/index#/
@SuppressWarnings("SameParameterValue")
public interface BookServer {

    List<Book> getAllBooks() throws Exception;

    Book getBookById(int id) throws Exception;
}
