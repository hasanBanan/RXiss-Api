package com.example.rxiss.domains;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "label")
public class Label {
    @PrimaryKey(autoGenerate = true)
    public long key;

    public long id;

    public String name;

    public String color;

    public long iss_id;

    public Label() {
    }

    public Label(long key, long id, String name, String color, long iss_id) {
        this.key = key;
        this.id = id;
        this.name = name;
        this.color = color;
        this.iss_id = iss_id;
    }

    public Label(long key, long id, String name, String color) {
        this.key = key;
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIss_id(long iss_id) {
        this.iss_id = iss_id;
    }

    public long getKey() {
        return key;
    }

    public long getIss_id() {
        return iss_id;
    }
}
