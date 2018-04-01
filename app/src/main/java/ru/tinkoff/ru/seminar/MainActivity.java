package ru.tinkoff.ru.seminar;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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
        bookServer.getBookById(10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        book -> printResult(book.toString()),
                        throwable ->
                                printResult(
                                        String.format("Throwable:%s", throwable.getMessage())
                                )
                );
    }
}

