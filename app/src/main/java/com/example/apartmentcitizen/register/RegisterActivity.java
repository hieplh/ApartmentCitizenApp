package com.example.apartmentcitizen.register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.example.apartmentcitizen.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class RegisterActivity extends AppCompatActivity {

    Button edtBirthDay;
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
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,listRelationship);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnRelationship.setAdapter(adapter);



        //set up edtBirthday
        edtBirthDay = findViewById(R.id.edit_register_birthday);
        edtBirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDateOfBirth();
            }
        });
    }

    public void chooseDateOfBirth(){
        final Calendar calendar = Calendar.getInstance();
        final int curDay = calendar.get(Calendar.DATE);
        final int curMonth = calendar.get(Calendar.MONTH);
        final int curYear = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edtBirthDay.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, curYear, curMonth, curDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public void clickToNextRegister(View view) {
        Intent intent = new Intent(this, RegisterImageActivity.class);
        startActivity(intent);
    }

    public enum Relationship{
        PARENT("Bố/Mẹ"),
        GRANDPA("Ông/Bà"),
        SIBLING("Anh/Chị/Em"),
        UNCLE("Cô/Chú"),
        CHILDREN("Con trai/Con gái"),
        GRANDCHILDREN("Cháu trai/cháu gái");

        private String description;

        Relationship(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

}
