package com.example.apartmentcitizen.network;

import com.example.apartmentcitizen.home.account.familymember.MemberCardDTO;
import com.example.apartmentcitizen.home.account.information.Information;
import com.example.apartmentcitizen.home.account.wallet.WalletActivity;
import com.example.apartmentcitizen.image.LoadImage;
import com.example.apartmentcitizen.register.Register;
import com.example.apartmentcitizen.register.RegisterActivity;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserService {

    @Headers("Content-Type: application/json")
    @POST("users")
    Call<RegisterActivity.RegisterResponse> createUser(@Body Register register);

    @Headers("Content-Type: application/json")
    @PUT("users/{userId}")
    Call<Information> updateUser(@Path("userId") int id, @Body Information register);

    @Multipart
    @PUT("users/profileImage/{email}")
    Call<ResponseBody> uploadImageProfile(@Path("email") String email, @Part MultipartBody.Part file, @Part("file") RequestBody requestBody);

    @Multipart
    @PUT("users/idImage/{email}")
    Call<ResponseBody> uploadImageCif(@Path("email") String email, @Part MultipartBody.Part file, @Part("file") RequestBody requestBody);

    @GET("users/email/{email}")
    Call<ResponseBody> checkPresentEmail(@Path("email") String email);

    @GET("users/houses/{houseId}")
    Call<List<MemberCardDTO>> getUserByHouseId(@Path("houseId") int id);

    @Headers("Content-Type: application/json")
    @PUT("houses/{houseId}/currentMoney/{currentMoney}")
    Call<WalletActivity.WalletResponse> UpdateHouseWalletByHouseId(@Path("houseId") int houseId, @Path("currentMoney") int currentMoney);

}