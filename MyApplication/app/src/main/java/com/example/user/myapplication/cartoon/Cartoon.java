package com.example.user.myapplication.cartoon;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static com.example.user.myapplication.cartoon.Cartoon.TABLE_NAME;

@Entity(tableName = TABLE_NAME)
public class Cartoon {
    public static final String TABLE_NAME = "cartoons";

    @PrimaryKey
    @NonNull
    Integer id;
    String title;
    String description;
    Integer score;
    Integer episodes;
    Boolean added;

    public Cartoon(@NonNull Integer id, String title, String description, Integer score, Integer episodes, Boolean added) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.score = score;
        this.episodes = episodes;
        this.added = added;
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Integer episodes) {
        this.episodes = episodes;
    }


    @Override
    public String toString(){
        return " "+this.title+" "+this.description+" "+this.episodes.toString()+" "+this.score.toString();
    }

    public Boolean getAdded() {
        return added;
    }

    public void setAdded(Boolean added) {
        this.added = added;
    }
}
