package com.example.apartmentcitizen.home.account.information;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.apartmentcitizen.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

public class InformationActivity extends AppCompatActivity {

    private final int DAY_OF_MONTH_INDEX = 0;
    private final int MONTH_INDEX = 1;
    private final int YEAR_INDEX = 2;
    TextView title, edtBirthDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        setUpView();
    }

    public void setUpView() {
        title = findViewById(R.id.label_title_standard);
        title.setText(R.string.information_activity_title);


        //set up edtBirthday
        edtBirthDay = findViewById(R.id.infor_edit_text_birth_date);
        edtBirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDateOfBirth(((TextView) v).getText().toString());
            }
        });
    }

    private int splitStringDate(String date, int index) {
        return Integer.parseInt(date.split("/")[index]);
    }

    private long getPastMilisecondsFromNow(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 31);
        cal.set(Calendar.MONTH, 11);
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - year);
        return cal.getTimeInMillis();
    }

    private void chooseDateOfBirth(String date) {
        final Calendar calendar = Calendar.getInstance();
        int curDay, curMonth, curYear;

        if (date.isEmpty()) {
            curDay = calendar.get(Calendar.DATE);
            curMonth = calendar.get(Calendar.MONTH);
            curYear = calendar.get(Calendar.YEAR);
        } else {
            curDay = splitStringDate(date, DAY_OF_MONTH_INDEX);
            curMonth = splitStringDate(date, MONTH_INDEX) - 1;
            curYear = splitStringDate(date, YEAR_INDEX);
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edtBirthDay.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, curYear, curMonth, curDay);

        datePickerDialog.getDatePicker().setMaxDate(getPastMilisecondsFromNow(16));
        datePickerDialog.getDatePicker().setMinDate(getPastMilisecondsFromNow(720));
        datePickerDialog.show();
    }

}
