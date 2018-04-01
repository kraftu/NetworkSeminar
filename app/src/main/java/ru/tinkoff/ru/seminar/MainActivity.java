package ru.tinkoff.ru.seminar;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import ru.tinkoff.ru.seminar.model.Weather;

/**
 Реализовать приложение, показывающее текущую погоду в городе из предложенного списка.
 Часть 1. Подготавливаем окружение для взаимодействия с сервером.
 1) Сперва получаем ключ для разработчика
 (Достаточно зарегистрироваться на сайте, он бесплатный)
 инструкция: https://openweathermap.org/appid
 2) Выполнить запрос для получения погоды одного из следующих городов:
    Moscow,RU
    Sochi,RU
    Vladivostok,RU
    Chelyabinsk,RU
 API запроса By city name можно прочитать тут: https://openweathermap.org/current#name

 Шаблон запроса: api.openweathermap.org/data/2.5/weather?q={city name},{country code}
 Пример: http://api.openweathermap.org/data/2.5/weather?q=Moscow,ru&APPID=7910f4948b3dcb251ebc828f28d8b30b

 Данные с сервера должны приходить в json формате (прим.: значение температуры в градусах Цельсия).
 Также можно добавить локализацию языка: https://openweathermap.org/current#other

 Часть 2. Разработка мобильного приложения.
 UI менять не надо, используем уже реализованные методы MainActivity.
 Написать код выполнения запроса в методе performRequest(@NonNull String city)

 Реализовать следующий функционал:
 a) С помощью Retrofit, Gson и других удобных для вас инструментов,
 написать запрос для получения текущий погоды в конкретном городе, используя метод API By city name.
 б) Реализовать JsonDeserializer, который преобразует json структуру пришедшую с сервера в модель Weather.
 в) Во время загрузки данных показывать прогресс бар, в случае ошибки выводить соотвествующее сообщение.
 г) Если у пользователя нет доступа в интернет, кнопка выполнить запрос не активна.
 При его появлении/отсутствии необходимо менять состояние кнопки.
 */

@SuppressWarnings("unused")
public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private Button performBtn;
    private ProgressBar progressBar;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        spinner = findViewById(R.id.spinner);
        performBtn = findViewById(R.id.performBtn);
        progressBar = findViewById(R.id.progressBar);
        resultTextView = findViewById(R.id.resultTextView);
        performBtn.setOnClickListener(v -> performRequest(spinner.getSelectedItem().toString()));
    }

    private void setEnablePerformButton(boolean enable) {
        performBtn.setEnabled(enable);
    }

    @SuppressLint("DefaultLocale")
    private void printResult(@Nullable Weather weather) {
        String result = null;
        if (weather != null) {
            //String name, String description, String city, double temp, double speedWind
            result = String.format(
                    "City: %s\n%s\nDesc: %s\nTemp: %.1f\nSpeed wind: %.1f",
                    weather.city,
                    weather.name,
                    weather.description,
                    weather.temp,
                    weather.speedWind
            );
        }
        resultTextView.setText(result);
    }

    private void showProgress(boolean visible) {
        progressBar.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    private void showError(@NonNull String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    private void performRequest(@NonNull String city) {
        // Здесь необходимо написать свой код.
        // Вызываем нужные методы в нужном порядке.
    }
}

