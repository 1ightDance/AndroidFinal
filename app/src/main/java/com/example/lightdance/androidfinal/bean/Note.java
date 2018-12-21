package com.example.lightdance.androidfinal.bean;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {

    private int id;
    private int typeId;
    private String typeName;
    private String title;
    private Date modifyTime;
    private String location;
    private String content;
    private boolean isNew;

    public Note() {}

    public Note(int id, int typeId) {
        this.id = id;
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
                ", typeId=" + typeId +
                ", title='" + title + '\'' +
                ", modifyTime=" + modifyTime +
                ", location='" + location + '\'' +
                ", content='" + content + '\'' +
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

        public Builder typeId(int typeId) {
            this.note.typeId = typeId;
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
            this.note.content = context;
            return this;
        }

        public Note builded() {
            return note;
        }
    }

}
