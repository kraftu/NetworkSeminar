package ru.tinkoff.ru.seminar.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;


public class JsonBookDeserializer implements JsonDeserializer<Book> {
    @Override
    public Book deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Book book = new Book();
        // JsonElement  -> Book java object
        book.id = json.getAsJsonObject().get("ID").getAsInt();
        //...
        return book;
    }
}
