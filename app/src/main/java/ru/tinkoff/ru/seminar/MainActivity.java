package ru.tinkoff.ru.seminar;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTextView = findViewById(R.id.resultContent);
        findViewById(R.id.performRequest).setOnClickListener(v -> performRequest());
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

    static class BackgroundTask implements Callable<String> {

        @Override
        public String call() throws Exception {
            //https://httpbin.org/html

            InetAddress address = InetAddress.getByName("httpbin.org");
            Socket socket = new Socket(address, 80);

            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String httpRequest = "GET /html HTTP/1.1\r\n"
                    + "Host: httpbin.org\r\n"
                    + "Connection: Close\r\n"
                    + "\r\n";

            out.print(httpRequest);
            out.flush();

            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line).append("\n");
            }

            socket.close();
            return sb.toString();
        }
    }
}

