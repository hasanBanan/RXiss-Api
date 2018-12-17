package com.example.rxiss.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.rxiss.domains.Issue;
import com.example.rxiss.domains.User;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    Flowable<List<User>> getAll();

    @Query("SELECT * FROM user WHERE iss_id = :id")
    Flowable<User> getById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<User> users);

    @Update
    void update(User user);

    @Query("DELETE FROM user")
    void deleteAll();
}