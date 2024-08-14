package com.zonassoft.footprintforlife.room;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.zonassoft.footprintforlife.room.table.IndicatorEntity;
import com.zonassoft.footprintforlife.room.table.ProfileEntity;

@Database(entities = {IndicatorEntity.class, ProfileEntity.class}, version = 5, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract DAO getDAO();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDb(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context, AppDatabase.class, "co2_database")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}