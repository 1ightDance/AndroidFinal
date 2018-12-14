package com.example.lightdance.androidfinal.bean;

import java.util.Date;

public class Note {

    private int id;
    private int classifyId;
    private String title;
    private Date modifyTime;
    private String location;
    private String context;

    public Note() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(int classifyId) {
        this.classifyId = classifyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", classifyId=" + classifyId +
                ", title='" + title + '\'' +
                ", modifyTime=" + modifyTime +
                ", location='" + location + '\'' +
                ", context='" + context + '\'' +
                '}';
    }
}
