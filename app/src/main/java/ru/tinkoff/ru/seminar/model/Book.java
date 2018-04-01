package ru.tinkoff.ru.seminar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


//http://www.jsonschema2pojo.org/
@SuppressWarnings({"WeakerAccess", "unused"})
public class Book {
    @SerializedName("ID")
    @Expose
    public Integer id;
    @SerializedName("Title")
    @Expose
    public String title;
    @SerializedName("Description")
    @Expose
    public String description;
    @SerializedName("PageCount")
    @Expose
    public Integer pageCount;
    @SerializedName("Excerpt")
    @Expose
    public String excerpt;
    @SerializedName("PublishDate")
    @Expose
    public String publishDate;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", pageCount=" + pageCount +
                ", publishDate='" + publishDate + '\'' +
                '}';
    }
}
