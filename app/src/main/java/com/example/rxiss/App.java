package com.example.rxiss;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.rxiss.data.local.LocaleStorage;

public class App extends Application {

    public static App instance;

    private LocaleStorage database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, LocaleStorage.class, "database")
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public LocaleStorage getDatabase() {
        return database;
    }
}
