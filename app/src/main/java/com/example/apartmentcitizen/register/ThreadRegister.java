package com.example.apartmentcitizen.register;

import android.util.Log;

import com.example.apartmentcitizen.image.UploadImage;
import com.example.apartmentcitizen.network.UserService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ThreadRegister implements Runnable{

    Retrofit retrofit;
    String[] pathImage;
    String email, type;
    boolean flag = true;

    UserService userService;
    Call<ResponseBody> call;

    public ThreadRegister(Retrofit retrofit, String[] pathImage, String email, String type) {
        this.retrofit = retrofit;
        this.pathImage = pathImage;
        this.email = email;
        this.type = type;
    }

    @Override
    public void run() {
        if (pathImage != null) {
            userService = retrofit.create(UserService.class);
            call = userService.checkPresentEmail(email);

            try {
                while (flag) {
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                uploadImage(email, pathImage[0], type);
                                flag = false;
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                }
            } catch (Exception e) {
                Log.d("THREAD", "run: " + e.getMessage());
            }
        }
    }

    private void uploadImage(String email, String path, String type) {
        UploadImage uploadImage = new UploadImage();
        uploadImage.uploadImageToServer(email, path, type);
    }
}
