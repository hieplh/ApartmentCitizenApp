package com.example.apartmentcitizen.handle;

import java.util.ArrayList;
import java.util.List;

public class Digit {

    public static String handleDigit(String digit) {
        StringBuilder result = new StringBuilder();
        List<String> list = new ArrayList<>();
        int length = digit.length();

        if (length <= 3) {
            return digit;
        } else {
            int start = length;
            int end = start - 3 >= 0 ? start - 3 : 0;
            boolean flag = false;

            do {
                if (flag) {
                    start = end;
                    end = start - 3 > 0 ? start - 3 : 0;
                    list.add(",");
                } else {
                    flag = true;
                }
                list.add(digit.substring(end, start));
            } while (end > 0);
        }
        for (int i = list.size() - 1; i >= 0; i--) {
            result.append(list.get(i));
        }
        return result.toString();
    }
}
