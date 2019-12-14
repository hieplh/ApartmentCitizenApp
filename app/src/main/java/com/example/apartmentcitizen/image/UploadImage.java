package com.example.apartmentcitizen.image;

import android.content.Context;
import android.util.Log;

import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.network.RetrofitInstance;
import com.example.apartmentcitizen.network.UserService;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UploadImage implements Callback{

    public static final String PROFILE = "profile";
    public static final String CIF = "cif";

    final String TAG = "INFO";

    public  UploadImage() {
    }

    public void uploadImageToServer(String email, String filePath, String type) {
        Retrofit retrofit = RetrofitInstance.getRetrofitInstance();

        UserService upload = retrofit.create(UserService.class);

        File file = new File(filePath);

        RequestBody fileRequestBody = RequestBody.create(MediaType.parse("image/*"), file);

        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), fileRequestBody);

        RequestBody desc = RequestBody.create(MediaType.parse("text/plain"), "image-type");

        Call<ResponseBody> call = null;

        switch (type) {
            case PROFILE: call = upload.uploadImageProfile(email, part, desc); break;
            case CIF: call = upload.uploadImageCif(email, part, desc); break;
        }

        call.enqueue(this);
    }

    @Override
    public void onResponse(Call call, Response response) {
        if (response.isSuccessful()) {
            Log.d(TAG, "AVATAR: " + "Cập nhật avatar thành công");
            Log.d(TAG, "CIF: " + "Cập nhật cif thành công");
        } else {
            Log.d(TAG, "AVATAR: " + "cập nhật avatar thất bại");
            Log.d(TAG, "CIF: " + "cập nhật cif thất bại");
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        Log.d(TAG, "FAILURE: " + "cập nhật avatar thất bại");
        Log.d(TAG, "FAILURE: " + "cập nhật cif thất bại");
    }
}
