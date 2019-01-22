package com.example.user.myapplication.cartoon;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CartoonDAO {
    @Insert
    void insert(Cartoon cartoon);

    @Query("Select * from cartoons Limit:size Offset:page_nr")
    List<Cartoon> getCartoons(int page_nr, int size);

    @Query("Select * from cartoons")
    List<Cartoon> getAllCartoons();

    @Query("Update cartoons set added=:added where cartoons.id=:id")
    void updateCartoon(Integer id, Boolean added);
}
