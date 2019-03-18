package com.appzone.validate.services;

import com.appzone.validate.models.ProductDataModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Services {

    @FormUrlEncoded
    @POST("/Api/search")
    Call<ProductDataModel> getProduct(@Field("qr_code_num") String qr_code_num);
}
