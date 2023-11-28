package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String WARNING = "Норма воды на сегодняшний день выполнена!";
    public int maxWater;

    private List<String> listHistory = new ArrayList<>();


    private ListView listView;
    private int progress = 0;
    Button buttonIncrement50, buttonIncrement100, buttonIncrement200, buttonIncrement250;
    ProgressBar progressBar;
    TextView textView;


    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Получение максимальной нормы если пользователь ее высчитывал, если нет по дефолту 2000
        maxWater = getIntent().getIntExtra("res", 2000);

        // Получение числа уже выпитой воды, если такая была (*число*/2000 мл)
        progress = getIntent().getIntExtra("progress", 0);

        // Получение истории выпитой воды, если такой не было(null) то цикл пропускается
        String[] masHistory = getIntent().getStringArrayExtra("masHistory");
        if (masHistory != null) {
            for (int i = 0; i < masHistory.length; i++) {
                listHistory.add(masHistory[i]);
            }
        }

        // Получение кнопок по id
        buttonIncrement50 = findViewById(R.id.button_incr50);
        buttonIncrement100 = findViewById(R.id.button_incr100);
        buttonIncrement200 = findViewById(R.id.button_incr200);
        buttonIncrement250 = findViewById(R.id.button_incr250);

        // Получение общего прогресса(круг)
        progressBar = findViewById(R.id.progress_bar);
        // обновление заполненности круга
        progressBar.setProgress(progress);

        // установка максимально числа выпитой воды в день
        progressBar.setMax(maxWater);

        // Получение истории выпитой воды из XML файла
        listView = findViewById(R.id.list_history);
        // Установка в ListView выпитой воды наш пользовательский List
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listHistory);
        listView.setAdapter(adapter);

        // Получение надписи в круге ( /мл воды)
        textView = findViewById(R.id.text_view_progress);
        // Обновление значения
        textView.setText("" + progress + "мл / " + maxWater + " мл");


        // В зависимости от значения добавляется вода в общий прогресс
        buttonIncrement50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (progress <= maxWater) {
                    progress += 50;
                    updateProgressBar(50);

                } else {
                    showInfo();
                }
            }
        });

        buttonIncrement100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (progress <= maxWater) {
                    progress += 100;
                    updateProgressBar(100);

                } else {
                    showInfo();
                }
            }
        });

        buttonIncrement200.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (progress <= maxWater) {
                    progress += 200;
                    updateProgressBar(200);

                } else {
                    showInfo();
                }
            }
        });

        buttonIncrement250.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (progress <= maxWater) {
                    progress += 250;
                    updateProgressBar(250);

                } else {
                    showInfo();
                }
            }
        });
    }


    // Переход с с главной страницы, на страницу расчета воды
    public void startNewActivity(View view) {

        Intent intent = new Intent(this, SecondActivity.class);
        // Данные которые не должны потеряться
        intent.putExtra("progress", progress);
        String[] masHistory = new String[listHistory.size()];
        // Меняем List на массив, т.к Intent не поддерживает работу с List
        for (int i = 0; i < masHistory.length; i++) {
            masHistory[i] = listHistory.get(i);
        }
        intent.putExtra("masHistory", masHistory);
        startActivity(intent);
    }


    // Предупреждение о выполненной норме воды
    public void showInfo() {
        Toast.makeText(this, WARNING, Toast.LENGTH_SHORT).show();
    }


    // обновление отображения на main странице на новые значения
    @SuppressLint("SetTextI18n")
    private void updateProgressBar(int count) {
        progressBar.setProgress(progress);
        textView.setText("" + progress + "мл / " + maxWater + " мл");
        // история выпитой воды
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime localDateTime = LocalDateTime.now();
            String line = "" + localDateTime.getDayOfMonth() + " " + localDateTime.getMonth() + " " +
                    localDateTime.getHour() + ":" + localDateTime.getMinute() + ":" + localDateTime.getSecond() + "                    " + count + " мл";
            listHistory.add(line);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listHistory);
            listView.setAdapter(adapter);
        }
    }


}