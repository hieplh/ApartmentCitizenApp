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

    Context context;

    public  UploadImage() {

    }

    public UploadImage(Context context) {
        this.context = context;
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
            Log.d(TAG, "AVATAR: " + context.getString(R.string.update_avatar_success));
            Log.d(TAG, "CIF: " + context.getString(R.string.update_cif_success));
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        Log.d(TAG, "AVATAR: " + context.getString(R.string.update_avatar_fail));
        Log.d(TAG, "CIF: " + context.getString(R.string.update_avatar_fail));
    }
}
