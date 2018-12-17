package com.example.rxiss.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.rxiss.data.local.dao.IssueDao;
import com.example.rxiss.data.local.dao.LabelDao;
import com.example.rxiss.data.local.dao.UserDao;
import com.example.rxiss.domains.Issue;
import com.example.rxiss.domains.Label;
import com.example.rxiss.domains.User;

@Database(entities = {Issue.class, Label.class, User.class}, version = 1)
abstract public class LocaleStorage extends RoomDatabase {
    public abstract IssueDao issueDao();
    public abstract LabelDao labelDao();
    public abstract UserDao userDao();
}
