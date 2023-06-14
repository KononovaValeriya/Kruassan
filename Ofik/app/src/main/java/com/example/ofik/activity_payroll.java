package com.example.ofik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import DataBase.User.Manager;
import DataBase.User.User;

public class activity_payroll extends AppCompatActivity {
    Manager objUserManager;
    TextView objTextViewCountHoursInMonth, objTextViewSalary, objTextViewTotalAmount;

    User objUser;

    Spinner monthSelector;

    DataBase.User.Schedule.Manager objSheduleManager;
    AssocArray arShedules;

    Integer intSelectedMonthNumber;


    private void getCalculation() {
        DecimalFormat df = new DecimalFormat("###,###,###");
        objTextViewSalary.setText(df.format(objUser.getSalary()));

        objTextViewCountHoursInMonth.setText("0");
        objTextViewTotalAmount.setText("0");

        String[][] arList = this.arShedules.getSchema();

        Integer intNumberWorkHours = 0;
        LocalDate objDate = LocalDate.now();
        Integer intCurrentYearNumber = objDate.getYear();

        for (int i = 0; i < arList.length; i++) {
            if (arList[i][1].equals("")) continue;
            String[] arDate = arList[i][0].split("\\.");
            Log.d("DEB", arList[i][0]);
            if (arDate.length != 3) continue;

            int intMonth = Integer.parseInt(arDate[1]);
            int intYear =  Integer.parseInt(arDate[2]);
            if (intMonth != intSelectedMonthNumber) continue;
            if (intYear != intCurrentYearNumber) continue;

            String[] arSchedule = arList[i][1].split("-");
            if (arSchedule.length != 2) continue;

            int intTimeStart = Integer.parseInt(arSchedule[0]);
            int intTimeEnd = Integer.parseInt(arSchedule[1]);
            int intDiff = intTimeEnd - intTimeStart;
            if (intDiff < 1) continue;

            intNumberWorkHours += intDiff;
        }
        if (intNumberWorkHours > 0) {
            objTextViewCountHoursInMonth.setText(df.format(intNumberWorkHours));
            objTextViewTotalAmount.setText(df.format(intNumberWorkHours * objUser.getSalary()));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_payroll);

        this.arShedules = new AssocArray();
        try {
            this.objSheduleManager = new DataBase.User.Schedule.Manager(this);
            this.arShedules = this.objSheduleManager.get();
        } catch (Exception objEx) {

        }

        this.objUser = new User();
        try {
            this.objUserManager = new Manager(this);
            this.objUser = this.objUserManager.get();
        } catch (Exception objEx) {

        }

        objUserManager = new Manager(this);
        objTextViewCountHoursInMonth = findViewById(R.id.textViewCountHoursInMonth);
        objTextViewSalary = findViewById(R.id.textViewSalary);
        objTextViewTotalAmount = findViewById(R.id.textViewTotalAmount);

        monthSelector = findViewById(R.id.monthSelector);

        ArrayList<String> arMonths = new ArrayList<>();
        arMonths.add("Январь");
        arMonths.add("Февраль");
        arMonths.add("Март");
        arMonths.add("Апрель");
        arMonths.add("Май");
        arMonths.add("Июнь");
        arMonths.add("Июль");
        arMonths.add("Август");
        arMonths.add("Сентябрь");
        arMonths.add("Октябрь");
        arMonths.add("Ноябрь");
        arMonths.add("Декбрь");

        ArrayAdapter<String> objMonthsSelectorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arMonths);
        monthSelector.setAdapter(objMonthsSelectorAdapter);

        LocalDate objDate = LocalDate.now();
        intSelectedMonthNumber = objDate.getMonth().getValue() - 1;
        monthSelector.setSelection(intSelectedMonthNumber);

        monthSelector.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                intSelectedMonthNumber = i + 1;
                getCalculation();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        findViewById(R.id.actPayroll_btnToHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_payroll.this, MainActivity.class));
            }
        });
        findViewById(R.id.actPayroll_btnToSchedule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_payroll.this, Schedule.class));
            }
        });
    }
}