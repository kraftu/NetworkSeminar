package ru.tinkoff.ru.seminar;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.tinkoff.ru.seminar.model.Book;
import ru.tinkoff.ru.seminar.retrofit.ApiServerRetrofit;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView;
    private BookServer bookServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTextView = findViewById(R.id.resultContent);
        findViewById(R.id.performRequest).setOnClickListener(v -> performRequest());
        bookServer = new ApiServerRetrofit();
    }

    private void printResult(@Nullable String resultString) {
        resultTextView.setText(resultString);
    }

    private void performRequest() {
        Single.fromCallable(new BackgroundTask())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::printResult,
                        throwable -> printResult(
                                "Throwable:\n" + throwable.toString()
                        )
                );
    }

    class BackgroundTask implements Callable<String> {

        @SuppressLint("DefaultLocale")
        @SuppressWarnings("ConstantConditions")
        @Override
        public String call() throws Exception {
            List<Book> list = bookServer.getAllBooks();
            Book book = bookServer.getBookById(10);
            return String.format("Book count:%d \nGet book by id 10:\n%s", list.size(), book);
        }
    }
}

