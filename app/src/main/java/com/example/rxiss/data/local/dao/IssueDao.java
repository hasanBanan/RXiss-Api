package com.example.rxiss.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.rxiss.domains.Issue;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

@Dao
public interface IssueDao {

    @Query("SELECT * FROM issue")
    Flowable<List<Issue>> getAll();

    @Query("SELECT * FROM issue WHERE id = :id")
    Flowable<Issue> getById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Issue> issues);

    @Update
    void update(Issue issue);

    @Query("DELETE FROM issue")
    void deleteAll();
}