package ru.tinkoff.ru.seminar.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;


public class JsonBookSerializer implements JsonSerializer<Book> {
    @Override
    public JsonElement serialize(Book src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        //Book java object -> JsonElement
        result.addProperty("ID", src.id);
        //....
        return result;
    }
}
