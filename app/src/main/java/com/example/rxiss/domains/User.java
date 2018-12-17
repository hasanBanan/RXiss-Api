package com.example.rxiss.domains;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    public long key;

    public long id;

    public String login;

    public String avatar_url;

    public long iss_id;

    public User() {
    }

    public User(long key, long id, String login, String avatar_url) {
        this.key = key;
        this.id = id;
        this.login = login;
        this.avatar_url = avatar_url;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}
