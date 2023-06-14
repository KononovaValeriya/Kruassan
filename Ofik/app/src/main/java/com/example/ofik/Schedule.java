package com.example.ofik;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import DataBase.User.Schedule.Manager;

public class Schedule extends AppCompatActivity {
    CalendarView objCalendar;
    ArrayAdapter<String> objSelectorAdapter;
    Spinner objTimeStartWorkSelector, objTimeEndWorkSelector;
    TextView objTimeStartWorkText, objTimeEndWorkText;

    AssocArray objData;
    Manager objScheduleManager;
    String strCurrentDate = "";
    Integer intStartTime  = 10;
    Integer intEndTime    = 14;

    ImageButton objAddWorkDayBtn;
    TextView objAddWorkDayText;

    private void showTimeWorkBlock() {
        objTimeStartWorkText.setVisibility(TextView.VISIBLE);
        objTimeEndWorkText.setVisibility(TextView.VISIBLE);
        objTimeStartWorkSelector.setVisibility(Spinner.VISIBLE);
        objTimeEndWorkSelector.setVisibility(Spinner.VISIBLE);
        objAddWorkDayText.setText("Удалить рабочий день");

        String strVal = objData.get(strCurrentDate);
        if (!strVal.equals("")) {
            String[] parts = strVal.split("-");
            intStartTime = Integer.valueOf(parts[0]);
            intEndTime = Integer.valueOf(parts[1]);
            objTimeStartWorkSelector.setSelection(intStartTime);
            objTimeEndWorkSelector.setSelection(intEndTime);
        }
        saveShedule();
    }
    private void hideTimeWorkBlock() {
        objTimeStartWorkText.setVisibility(TextView.INVISIBLE);
        objTimeEndWorkText.setVisibility(TextView.INVISIBLE);
        objTimeStartWorkSelector.setVisibility(Spinner.INVISIBLE);
        objTimeEndWorkSelector.setVisibility(Spinner.INVISIBLE);
        objAddWorkDayText.setText("Добавить рабочий день");
        cleanShedule();
    }

    private void saveShedule() {
        this.objData.set(this.strCurrentDate, String.valueOf(this.intStartTime)+"-"+String.valueOf(this.intEndTime));
        try {
            this.objScheduleManager.save(this.objData);
        } catch (Exception objEx) {
            Log.d("schedule-error", objEx.getMessage());
        }
    }

    private void cleanShedule() {
        this.objData.set(this.strCurrentDate, "");
        try {
            this.objScheduleManager.save(this.objData);
        } catch (Exception objEx) {
            Log.d("schedule-error", objEx.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_schedule);

        objTimeStartWorkText     = findViewById(R.id.timeStartWorkText);
        objTimeEndWorkText       = findViewById(R.id.timeEndWorkText);
        objTimeStartWorkSelector = findViewById(R.id.timeStartSelector);
        objTimeEndWorkSelector   = findViewById(R.id.timeEndSelector);
        objCalendar              = findViewById(R.id.schedule_calendarView);
        objAddWorkDayBtn         = findViewById(R.id.addWorkDayBtn);
        objAddWorkDayText        = findViewById(R.id.addWorkDayText);

        /** Получаем текущую дату **/
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        this.strCurrentDate = formatter.format(new Date(this.objCalendar.getDate()));

        Log.d("DEB", "Current date: "+strCurrentDate);

        /** Загружаем данные о графике **/
        this.objData = new AssocArray();
        this.objScheduleManager = new Manager(this);
        try {
            this.objData = this.objScheduleManager.get();
        } catch (Exception objEx) {

        }

        objData.debug();

        objCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String strYear = String.valueOf(year);
                String strMonth = String.valueOf(month + 1);
                if (month < 9) strMonth = "0"+strMonth;
                String strDay = String.valueOf(dayOfMonth);
                if (dayOfMonth < 10) strDay = "0"+strDay;

                strCurrentDate = strDay+"."+strMonth+"."+strYear;

                Log.d("DEB", "Selected date: "+strCurrentDate);

                if (objData.get(strCurrentDate).equals("")) {
                    hideTimeWorkBlock();
                } else {
                    showTimeWorkBlock();
                }
            }
        });

        /** Добавляем данные в селекторы **/
        ArrayList<String> objTimeStartWorkSelectorData = new ArrayList<>();
        ArrayList<String> objTimeEndWorkSelectorData   = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                objTimeStartWorkSelectorData.add("0"+String.valueOf(i)+":00");
                objTimeEndWorkSelectorData.add("0"+String.valueOf(i)+":00");
            } else {
                objTimeStartWorkSelectorData.add(String.valueOf(i)+":00");
                objTimeEndWorkSelectorData.add(String.valueOf(i)+":00");
            }
        }

        /** Если выбрали час старта **/
        objTimeStartWorkSelector.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                intStartTime = i;
                saveShedule();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        objTimeEndWorkSelector.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                intEndTime = i;
                saveShedule();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        /** Создаем адаптеры для селекторов **/
        ArrayAdapter<String> objTimeStartWorkSelectorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, objTimeStartWorkSelectorData) {
            @Override
            public boolean isEnabled(int position) {
                return position < intEndTime;
            }
        };
        ArrayAdapter<String> objTimeEndWorkSelectorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, objTimeEndWorkSelectorData) {
            @Override
            public boolean isEnabled(int position) {
                return position > intStartTime;
            }
        };

        /** Передаем данные в селекторы **/
        objTimeStartWorkSelector.setAdapter(objTimeStartWorkSelectorAdapter);
        objTimeEndWorkSelector.setAdapter(objTimeEndWorkSelectorAdapter);
        objTimeStartWorkSelector.setSelection(this.intStartTime);
        objTimeEndWorkSelector.setSelection(this.intEndTime);
        objTimeStartWorkSelectorAdapter.notifyDataSetChanged();
        objTimeEndWorkSelectorAdapter.notifyDataSetChanged();

        /** Оповещаем селекторы о том, что изменились данные **/
        objTimeStartWorkSelectorAdapter.notifyDataSetChanged();
        objTimeEndWorkSelectorAdapter.notifyDataSetChanged();

        /** Показываем блок с выбором времени **/
        ImageButton objAddWorkDayBtn = findViewById(R.id.addWorkDayBtn);
        TextView objAddWorkDayText   = findViewById(R.id.addWorkDayText);
        objAddWorkDayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (objData.get(strCurrentDate).equals("")) {
                    showTimeWorkBlock();
                } else {
                    hideTimeWorkBlock();
                }
            }
        });
        objAddWorkDayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (objData.get(strCurrentDate).equals("")) {
                    showTimeWorkBlock();
                } else {
                    hideTimeWorkBlock();
                }
            }
        });

        if (this.objData.get(this.strCurrentDate).equals("")) {
            hideTimeWorkBlock();
        } else {
            showTimeWorkBlock();
        }

        findViewById(R.id.actSchedule_btnToHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Schedule.this, MainActivity.class));
            }
        });
        findViewById(R.id.actSchedule_btnToPayroll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Schedule.this, activity_payroll.class));
            }
        });
    }
}