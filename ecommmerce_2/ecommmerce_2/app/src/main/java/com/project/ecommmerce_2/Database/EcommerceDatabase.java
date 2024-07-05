package com.project.ecommmerce_2.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.project.ecommmerce_2.Database.Dao.ProductDao;
import com.project.ecommmerce_2.Model.OrderDetailModel;
import com.project.ecommmerce_2.Model.OrderModel;
import com.project.ecommmerce_2.Model.PaymentModel;
import com.project.ecommmerce_2.Model.ProductModel;

@Database(entities = {ProductModel.class}, version = 3, exportSchema = false)
public abstract class EcommerceDatabase extends RoomDatabase {
    public static final String database_name = "ecommerce_db";

    public abstract ProductDao productDao();

    private static volatile EcommerceDatabase INSTANCE;

    public static EcommerceDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (EcommerceDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    EcommerceDatabase.class, database_name)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

