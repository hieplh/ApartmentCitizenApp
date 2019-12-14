package com.example.apartmentcitizen.register;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class ThreadRegister extends AsyncTask<Call<ResponseBody>, Void, String> {

    private String email;

    public ThreadRegister(String email) {
        this.email = email;
    }

    @Override
    protected String doInBackground(Call<ResponseBody>... calls) {
        Call<ResponseBody> call = calls[calls.length - 1];

        Response<ResponseBody> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.isSuccessful()) {
            Log.d("REGISTER", "RESPONSE: " + email + " has been existed");
            return response.message();
        } else {
            Log.d("REGISTER", "RESPONSE: " + email + " not available");
        }
        return null;
    }
}
