package com.example.lightdance.androidfinal.bean;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {

    public static final String NOTE = "NOTE";

    private int id;
    private int classifyId;
    private String title;
    private Date modifyTime;
    private String location;
    private String context;
    private boolean isNew;

    public Note() {}

    public Note(int id, int classifyId) {
        this.id = id;
        this.classifyId = classifyId;
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

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Note note;

        public Builder() {
            this.note = new Note();
        }

        public Builder id(int id) {
            this.note.id = id;
            return this;
        }

        public Builder classifyId(int classifyId) {
            this.note.classifyId = classifyId;
            return this;
        }

        public Builder title(String title) {
            this.note.title = title;
            return this;
        }

        public Builder modifyTime(Date modifyTime) {
            this.note.modifyTime = modifyTime;
            return this;
        }

        public Builder location(String location) {
            this.note.location = location;
            return this;
        }

        public Builder context(String context) {
            this.note.context = context;
            return this;
        }

        public Note builded() {
            return note;
        }
    }

}
