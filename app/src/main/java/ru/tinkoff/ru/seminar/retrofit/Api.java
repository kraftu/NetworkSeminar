package ru.tinkoff.ru.seminar.retrofit;

import java.util.Map;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;


public interface Api {

    //Конфигурация динамического url
    @GET("time/{type}")
    Single<ResponseBody> requestPath(@Path("type") String path);
    /**
     * requestPath("now") -> GET https://postman-echo.com/time/now
     * requestPath("format") -> GET https://postman-echo.com/time/format
     * requestPath("test") -> GET https://postman-echo.com/time/test
     */

    //Запросы GET с передачей параметров
    @GET("get")
    Single<ResponseBody> requestGet1(@Query("ValueA") String valueA, @Query("ValueB") String valueB);
    /**
     * requestGet1(q,a); -> GET https://postman-echo.com/get?ValueA=q&ValueB=a
     */

    @GET("get")
    Single<ResponseBody> requestGet2(@QueryMap Map<String, String> options);

    //Запросы POST с содержимым FormUrlEncoded
    @FormUrlEncoded
    @POST("post")
    Single<ResponseBody> requestPost1(@Field("ValueA") String valueA, @Field("ValueB") String valueB);
    /**
     * requestPost1(q,a)
     * ->
     * POST https://postman-echo.com/post
     * Content-Type: application/x-www-form-urlencoded
     * ValueA=q&ValueB=a
     */
    //POST с содержимым JSON
    @POST("post")
    Single<ResponseBody> requestPost2(@Body TestObject testObject);
    /**
     * requestPost2(new TestObject(1,"testName",System.currentTimeMillis()))
     * ->
     * POST https://postman-echo.com/post
     * Content-Type: application/json; charset=UTF-8
     * {"id":1,"name":"testName","time":1522593510505}
     */

    //POST c Multipart, загружаем файлы на сервер
    @Multipart
    @POST("post")
    Single<ResponseBody> requestPost3(@Part MultipartBody.Part filePart);
    /**
     Как вызвать метод
     File file = ....;
     MultipartBody.Part fileMultipartBody = MultipartBody.Part.createFormData(
                                    "file",
                                    file.getName(),
                                    RequestBody.create(MediaType.parse("image/*"), file)
     );
     requestPost3(fileMultipartBody);
    */
    //Запросы с дополнительными заголовками
    @GET("headers")
    Single<ResponseBody> requestHeaders1(@Header("User-Agent") String headerUserAgent);
    /**
     * requestHeaders1("Android")
     * ->
     * GET https://postman-echo.com/headers
     * User-Agent: Android
     */

    @GET("headers")
    @Headers({"User-Agent: Your-App-Name"})
    Single<ResponseBody> requestHeaders2();

    //Использование динамических url
    @GET()
    Single<ResponseBody> requestUrl(@Url String url);
    /**
     * requestUrl("https://www.tinkoff.ru")
     * ->
     * GET https://www.tinkoff.ru/
     */
}
