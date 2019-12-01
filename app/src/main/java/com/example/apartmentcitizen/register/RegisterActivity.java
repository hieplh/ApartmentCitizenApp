package com.example.apartmentcitizen.register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.apartmentcitizen.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class RegisterActivity extends AppCompatActivity {

    private final int DAY_OF_MONTH_INDEX = 0;
    private final int MONTH_INDEX = 1;
    private final int YEAR_INDEX = 2;

    TextView edtBirthDay;
    Spinner spnRelationship;
    List<String> listRelationship;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info);
        setUpView();
    }

    public void setUpView(){
        //set up spinner relationship
        listRelationship = new ArrayList<>();
        for (Relationship x: Relationship.values()) {
            listRelationship.add(x.getDescription());
        }

        spnRelationship = findViewById(R.id.spinner_relationship);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listRelationship);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnRelationship.setAdapter(adapter);

        //set up edtBirthday
        edtBirthDay = findViewById(R.id.edit_register_birthday);
        edtBirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDateOfBirth(((TextView)v).getText().toString());
            }
        });
    }

    private int splitStringDate(String date, int index) {
        return Integer.parseInt(date.split("/")[index]);
    }

    private long convertYearToMiliseconds(int year) {
        Date date = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime() - (1000 * 60 * 60 * 24 * 365 * year));
        return cal.getTimeInMillis();
    }

    private void chooseDateOfBirth(String date){
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

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
//        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - convertYearToMiliseconds(70));
        datePickerDialog.show();
    }

    public void clickToNextRegister(View view) {
        Intent intent = new Intent(this, RegisterImageActivity.class);
        startActivity(intent);
    }

    private enum Relationship{
        PARTNER("Chồng/Vợ"),
        PARENT("Bố/Mẹ"),
        GRANDPA("Ông/Bà"),
        SIBLING("Anh/Chị/Em"),
        UNCLE("Cô/Chú"),
        CHILDREN("Con trai/Con gái"),
        GRANDCHILDREN("Cháu trai/cháu gái"),
        FRIEND("Bạn Bè");

        private String description;

        Relationship(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

}
