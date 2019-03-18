package com.appzone.validate.mvvm;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.appzone.validate.models.ProductModel;

@Database(entities = {ProductModel.class},version = 1)
public abstract class ProductRoomDatabase extends RoomDatabase {

    public abstract ProductDao getDao();
    private static volatile ProductRoomDatabase instance = null;

    public static synchronized ProductRoomDatabase newInstance(Context context)
    {
        if (instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),ProductRoomDatabase.class,"thakk_database")
                    .build();
        }

        return instance;
    }
}
