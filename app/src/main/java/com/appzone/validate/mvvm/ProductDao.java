package com.appzone.validate.mvvm;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.appzone.validate.models.ProductModel;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert
    void insert(ProductModel productModel);

    @Query("SELECT * FROM products ORDER BY created_at DESC")
    LiveData<List<ProductModel>> getAllHistory();

    @Query("DELETE from products")
    void DeleteAll();

    @Query("SELECT * FROM products WHERE qr_code_num = :serialNumber LIMIT 1")
    ProductModel getProductBySerialNumber(String serialNumber);

    @Update
    void UpdateProduct(ProductModel productModel);
}
