package com.appzone.validate.mvvm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.appzone.validate.models.ProductModel;
import com.appzone.validate.remote.Repositories;

import java.util.List;

public class DatabaseMvvm extends AndroidViewModel {
    private ProductRoomDatabase database;
    private ProductDao dao;
    private Repositories repositories;
    private MutableLiveData<ProductModel> data = new MutableLiveData<>();

    public DatabaseMvvm(@NonNull Application application) {
        super(application);
        database = ProductRoomDatabase.newInstance(application);
        dao = database.getDao();
        repositories = Repositories.getInstance();
    }

    public void Insert(ProductModel productModel) {
        new AsyncTaskInsert().execute(productModel);
    }

    public void DeleteAll()
    {
        new AsyncTaskDeleteAll().execute();
    }

    public LiveData<List<ProductModel>> getAllProducts()
    {
        return dao.getAllHistory();
    }

    public ProductModel getProductBySerialNumber(String SerialNumber)
    {

        return dao.getProductBySerialNumber(SerialNumber);
    }
    public void UpdateProduct(ProductModel productModel)
    {
        new AsyncTaskUpdate().execute(productModel);
    }

    public void getProductData(Context context, String serialNumber)
    {
        data = (MutableLiveData<ProductModel>) repositories.getProductData(serialNumber,context);

    }

    public LiveData<ProductModel> getProductModel()
    {
        return data;
    }




    private class AsyncTaskInsert extends AsyncTask<ProductModel, Void, Void> {
        @Override
        protected Void doInBackground(ProductModel... productModels) {
            ProductModel productModel = productModels[0];
            dao.insert(productModel);
            getAllProducts();
            return null;
        }
    }

    private class AsyncTaskDeleteAll extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            dao.DeleteAll();
            getAllProducts();
            return null;
        }
    }

    private class AsyncTaskUpdate extends AsyncTask<ProductModel, Void, Void> {
        @Override
        protected Void doInBackground(ProductModel... productModels) {
            dao.UpdateProduct(productModels[0]);
            return null;
        }
    }




}
