package com.example.find_my_age;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button bt_today,bt_birth,bt_calculate;
    TextView tv_result;
    DatePickerDialog.OnDateSetListener dateSetListener1,dateSetListener2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_today        = findViewById(R.id.bt_today);
        bt_birth        = findViewById(R.id.bt_birth);
        bt_calculate    = findViewById(R.id.bt_calculate);
        tv_result       = findViewById(R.id.tv_result);

        Calendar calendar = Calendar.getInstance();
        final int year          = calendar.get(Calendar.YEAR);
        final int month         = calendar.get(Calendar.MONTH);
        final int day           = calendar.get(Calendar.DAY_OF_MONTH);

        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = simpleDateFormat.format(Calendar.getInstance().getTime());
        bt_today.setText(date);

        bt_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateSetListener1,year,month,day
                );
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        dateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month +"/" + year;
                bt_birth.setText(date);

            }
        };
        bt_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateSetListener2,year,month,day
                );
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        dateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month +"/" + year;
                bt_today.setText(date);

            }
        };

        bt_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sDate = bt_birth.getText().toString();
                String eDate = bt_today.getText().toString();
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date date1 = simpleDateFormat1.parse(sDate);
                    Date date2 = simpleDateFormat1.parse(eDate);

                    long startdate = date1.getTime();
                    long enddate   = date2.getTime();

                    if (startdate <= enddate) {
                        Period period = new Period(startdate,enddate, PeriodType.yearMonthDay());
                        int year     = period.getYears();
                        int month    = period.getMonths();
                        int day      = period.getDays();

                        tv_result.setText(year + "Years | " + month + "Months | " + day + "Days");
                    }else {
                        Toast.makeText(getApplicationContext(),
                                "Birth date should not be larger than today's date."
                        , Toast.LENGTH_SHORT).show();
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
