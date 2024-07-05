package com.project.ecommmerce_2.Database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.project.ecommmerce_2.Model.ProductModel;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ProductModel> producta);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProductModel producta);

    @Query("select * from barangs where nama like '%' || :keyword || '%' or id like '%' || :keyword || '%'")
    LiveData<List<ProductModel>> getProduct(String keyword);

    @Update
    void update(ProductModel productModel);


    @Delete
    void delete(ProductModel productModel);

    @Query("SELECT * FROM barangs where id =:id")
    ProductModel get(int id);

    @Query("delete from barangs")
    void deleteAll();
}