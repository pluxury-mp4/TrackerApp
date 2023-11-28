package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    public static final String WARNING = "Введите корректные данные!";
    private TextView result;
    private EditText field1;
    private EditText field2;
    private Button button;

    private int res;


    private int progress;

    private String[] masHistory;


    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        result = findViewById(R.id.result);
        field1 = findViewById(R.id.field1);
        field2 = findViewById(R.id.field2);
        button = findViewById(R.id.button3);

        // Получение данных от первой страницы
        progress = getIntent().getIntExtra("progress", 0);
        masHistory = getIntent().getStringArrayExtra("masHistory");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int field1Int = Integer.parseInt(field1.getText().toString());
                int field2Int = Integer.parseInt(field2.getText().toString());
                // валидация данных
                if (field2Int > 150 || field1Int > 300) {
                    showInfo();
                    return;
                }
                int temp = field1Int - field2Int;
                res = temp * 30;
                result.setText("Ваша ежедневная питевая норма: " + res + " мл");
            }
        });

    }

    public void homePage(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        /* проверка нужна чтобы если пользователь не рассчитывал значение воды,
        но при этом вернулся на главную вкладку, ничего не возвращалось(без этого 0 стоит)
         */
        if (res != 0) {
            intent.putExtra("res", res);
        }
        // Передача данных первой странице
        intent.putExtra("progress", progress);
        intent.putExtra("masHistory", masHistory);
        startActivity(intent);


    }

    // Если случилась ошибка валидации
    public void showInfo() {
        Toast.makeText(this, WARNING, Toast.LENGTH_SHORT).show();
    }


}