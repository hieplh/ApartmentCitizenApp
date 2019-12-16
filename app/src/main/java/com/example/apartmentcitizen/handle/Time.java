package com.example.apartmentcitizen.handle;

import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Time {

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
    static SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    static Date d;
    static String[] arrTime1, arrTime2;

    public static void setTimeForTransaction(String pattern, TextView day, TextView month, TextView time){
        try{
            d = sdf.parse(pattern);
            String formattedTime = output.format(d);
            arrTime1 = formattedTime.split("-");
            month.setText("Th " + arrTime1[1]);
            arrTime2 = arrTime1[2].split(" ");
            day.setText(arrTime2[0]);
            time.setText("Lúc " + arrTime2[1]);
        }catch (ParseException e){
            e.printStackTrace();
        }
    }

    public static void setTimeForReceipt(String pattern, TextView time){
        try{
            d = sdf.parse(pattern);
            String formattedTime = output.format(d);
            arrTime1 = formattedTime.split("-");
            arrTime2 = arrTime1[2].split(" ");
            time.setText("Ngày " + arrTime2[0] + " tháng " + arrTime1[1] + " năm " + arrTime1[0]);
        }catch (ParseException e){
            e.printStackTrace();
        }
    }

}
