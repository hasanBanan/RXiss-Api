package com.example.rxiss.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.rxiss.domains.Issue;
import com.example.rxiss.domains.Label;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface LabelDao {

    @Query("SELECT * FROM label")
    Flowable<List<Label>> getAll();

    @Query("SELECT * FROM label WHERE iss_id = :id")
    Flowable<List<Label>> getById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Label> labels);

    @Update
    void update(Label label);

    @Query("DELETE FROM label")
    void deleteAll();
}