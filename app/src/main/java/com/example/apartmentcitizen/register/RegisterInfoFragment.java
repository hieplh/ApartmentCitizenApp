package com.example.apartmentcitizen.register;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.apartmentcitizen.MainActivity;
import com.example.apartmentcitizen.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterInfoFragment extends Fragment implements BirthdateListener {

    private final int DAY_OF_MONTH_INDEX = 0;
    private final int MONTH_INDEX = 1;
    private final int YEAR_INDEX = 2;

    TextView birthdate;
    Spinner spnRelationship;
    List<String> listRelationship;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("FRAGMENT", "onCreateView: ");
        return inflater.inflate(R.layout.fragment_register_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("FRAGMENT", "onViewCreated: ");
        setUpView(view);
    }

    public void setUpView(View view) {
        //set up spinner relationship
        listRelationship = new ArrayList<>();
        for (RegisterInfoFragment.Relationship x : RegisterInfoFragment.Relationship.values()) {
            listRelationship.add(x.getDescription());
        }

        spnRelationship = view.findViewById(R.id.spinner_relationship);
        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, listRelationship);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnRelationship.setAdapter(adapter);

        //set up edtBirthday
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

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                birthdate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, curYear, curMonth, curDay);

        datePickerDialog.getDatePicker().setMaxDate(getPastMilisecondsFromNow(16));
        datePickerDialog.getDatePicker().setMinDate(getPastMilisecondsFromNow(720));
        datePickerDialog.show();
    }

    @Override
    public void initBirthdateDialog(View view) {
        chooseDateOfBirth(((EditText)view).getText().toString());
    }

    private enum Relationship {
        PARTNER("Vợ/Chồng"),
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
