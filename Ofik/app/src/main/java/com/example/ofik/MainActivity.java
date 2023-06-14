package com.example.ofik;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
    }
    protected void onStart() {
        super.onStart();
        // Переход в Состав блюд
        findViewById(R.id.main_btnToDishesComposition).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, dishesComposition.class));
            }
        });
        // Переход в Заметки
        findViewById(R.id.main_btnToNotes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Notes.class));
            }
        });
        // Переход в Личный кабинет
        findViewById(R.id.main_btnToProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, KabinetActivity.class));
            }
        });
        findViewById(R.id.main_textToSchedule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Schedule.class));
            }
        });
        findViewById(R.id.main_btnToSchedule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Schedule.class));
            }
        });

        // Переход в Расчёт зп
        findViewById(R.id.textViewToPayRoll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, activity_payroll.class));
            }
        });
        findViewById(R.id.main_btnToPayRoll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, activity_payroll.class));
            }
        });
    }
}