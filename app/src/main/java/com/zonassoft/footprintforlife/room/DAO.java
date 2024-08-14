package com.zonassoft.footprintforlife.room;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.zonassoft.footprintforlife.room.table.IndicatorEntity;
import com.zonassoft.footprintforlife.room.table.ProfileEntity;


@Dao
public interface DAO {

    /* table indicator transaction ------------------------------------------------------------------ */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertIndicator(IndicatorEntity indicator);

    @Query("DELETE FROM IndicatorEntity WHERE code = :code and country=:country and year=:year")
    void deleteIndicator(String code, String country, int year);

    @Query("DELETE FROM IndicatorEntity")
    void deleteAllIndicators();

    @Query("SELECT * FROM IndicatorEntity WHERE code = :code and country=:country and year=:year LIMIT 1")
    IndicatorEntity getIndicator(String code, String country, int year);

    @Query("SELECT count(*) FROM IndicatorEntity ")
    int countIndicators();

    @Query("SELECT count(*) FROM IndicatorEntity where country=:country and year=2018")
    int countCountryIndicators(String country);

    @Query("SELECT id FROM IndicatorEntity where id>0 order by id desc LIMIT 1")
    int getLastIndicatorId();

    @Query("SELECT sum(value) FROM IndicatorEntity WHERE code = :code and year>:year and country=:country ")
    double cumulativeEmisions(int year, String code, String country);


    /* table profile transaction ------------------------------------------------------------------ */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProfile(ProfileEntity profile);

    @Query("UPDATE ProfileEntity SET id_country=:id_country WHERE id=:id")
    void updateProfile(int id_country,int id);

    @Query("DELETE FROM ProfileEntity")
    void deleteAllProfiles();

    @Query("SELECT * FROM ProfileEntity WHERE id = :id LIMIT 1")
    ProfileEntity getProfile(int id);

    @Query("SELECT count(*) FROM ProfileEntity ")
    int countProfiles();


}