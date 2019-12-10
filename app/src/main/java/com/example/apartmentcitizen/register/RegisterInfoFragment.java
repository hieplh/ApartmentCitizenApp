package com.example.apartmentcitizen.register;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
public class RegisterInfoFragment extends Fragment {

    private final int DAY_OF_MONTH_INDEX = 0;
    private final int MONTH_INDEX = 1;
    private final int YEAR_INDEX = 2;

    TextView birthdate;
    Spinner spnRelationship;
    List<String> listRelationship;
    InputMethodManager imm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        birthdate = view.findViewById(R.id.edit_register_birthday);

        handleRelationshipSpinner(view);

        handleActionNext(view);

        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDateOfBirth(birthdate.getText().toString());
            }
        });
    }

    private void handleRelationshipSpinner(View view) {
        //set up spinner relationship
        listRelationship = new ArrayList<>();
        for (RegisterInfoFragment.Relationship x : RegisterInfoFragment.Relationship.values()) {
            listRelationship.add(x.getDescription());
        }

        spnRelationship = view.findViewById(R.id.spinner_relationship);
        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, listRelationship);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnRelationship.setAdapter(adapter);
    }

    private void handleActionNext(View view) {
        final EditText firstNameEdit = view.findViewById(R.id.edit_register_first_name);
        firstNameEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    imm.hideSoftInputFromWindow(firstNameEdit.getWindowToken(), 0);
                    chooseDateOfBirth(birthdate.getText().toString());
                }
                return true;
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
