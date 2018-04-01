package ru.tinkoff.ru.seminar;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import ru.tinkoff.ru.seminar.retrofit.ApiPostmanEchoServer;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView;
    private ApiPostmanEchoServer apiPostmanEchoServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTextView = findViewById(R.id.resultContent);
        findViewById(R.id.performRequest).setOnClickListener(v -> performRequest());
        apiPostmanEchoServer = new ApiPostmanEchoServer();
    }

    private void printResult(@Nullable String resultString) {
        resultTextView.setText(resultString);
    }

    private void performRequest() {
        apiPostmanEchoServer.getApi().requestUrl("https://www.tinkoff.ru")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        value -> printResult(value.string()),
                        throwable ->
                                printResult(
                                        String.format("Throwable:%s", throwable.getMessage())
                                )
                );
    }
}

