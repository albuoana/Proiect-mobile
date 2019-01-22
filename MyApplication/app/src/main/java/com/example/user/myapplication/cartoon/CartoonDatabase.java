package com.example.user.myapplication.cartoon;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


@Database(entities = {Cartoon.class}, version = 1)
public abstract class CartoonDatabase extends RoomDatabase {
    public abstract CartoonDAO cartoonDAO();
    public static CartoonDatabase INSTANCE;

    public static CartoonDatabase getAppDatabase(Context context)
    {
        if(INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CartoonDatabase.class, "cartoon-database").allowMainThreadQueries().build();

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
