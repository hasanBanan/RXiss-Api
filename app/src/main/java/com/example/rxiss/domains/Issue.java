package com.example.rxiss.domains;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "issue")
public class Issue {
    @PrimaryKey
    public long id;

    public String title;

    public String body;

    public String state;

    public String created_at;

    @Ignore public User user;

    @Ignore public List<Label> labels;

    public Issue() {
    }

    public Issue(long id, String title, String body, String state, String date, User user, List<Label> labels) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.state = state;
        this.created_at = date;
        this.user = user;
        this.labels = labels;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getStatus() {
        return state;
    }

    public String getDate() {
        return created_at;
    }

    public User getUser() {
        return user;
    }

    public List<Label> getLables() {
        return labels;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setStatus(String state) {
        this.state = state;
    }

    public void setDate(String date) {
        this.created_at = date;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLables(List<Label> lables) {
        this.labels = lables;
    }
}
