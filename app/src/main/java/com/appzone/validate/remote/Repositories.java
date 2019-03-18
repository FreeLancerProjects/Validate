package com.appzone.validate.remote;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.appzone.validate.R;
import com.appzone.validate.models.ProductDataModel;
import com.appzone.validate.models.ProductModel;

import java.io.IOException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repositories {

    private static Repositories instance = null;

    private Repositories() {
    }

    public static synchronized Repositories getInstance()
    {
        if (instance ==null)
        {
            instance = new Repositories();
        }

        return instance;
    }


    public LiveData<ProductModel> getProductData(String serial_number, final Context context)
    {
        final MutableLiveData<ProductModel> data = new MutableLiveData<>();
        Api.getServices()
                .getProduct(serial_number)
                .enqueue(new Callback<ProductDataModel>() {
                    @Override
                    public void onResponse(Call<ProductDataModel> call, Response<ProductDataModel> response) {

                        if (response.isSuccessful()&&response.body()!=null&&response.body().getData()!=null)
                        {

                            ProductDataModel.Products products = response.body().getData();
                            ProductModel productModel = new ProductModel(products.getId(),products.getQr_code_num(),products.getAr_name(),products.getEn_name(),products.getImage(),products.getType(),products.getPrice(),Long.parseLong(products.getProduction_date())*1000, Calendar.getInstance().getTimeInMillis());
                            productModel.setStatus(200);
                            data.setValue(productModel);
                        }else
                        {
                            if (response.code()==404)
                            {
                                ProductModel productModel =new ProductModel();
                                productModel.setStatus(404);
                                data.setValue(productModel);
                            }else
                            {
                                ProductModel productModel =new ProductModel();
                                productModel.setStatus(0);
                                data.setValue(productModel);
                                Toast.makeText(context, R.string.failed, Toast.LENGTH_SHORT).show();
                            }

                            try {
                                Log.e("code_error",response.code()+" "+response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductDataModel> call, Throwable t) {
                        try {

                            ProductModel productModel =new ProductModel();
                            productModel.setStatus(-1);
                            data.setValue(productModel);
                            Toast.makeText(context, R.string.something, Toast.LENGTH_SHORT).show();
                        }catch (Exception e){}
                    }
                });

        return data;
    }
}
