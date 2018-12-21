package com.example.lightdance.androidfinal.bean;

import java.io.Serializable;

public class Type implements Serializable {

    public static final String TYPE = "TYPE";

    private int id;
    private String typeName;

    public Type() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Type mType;

        public Builder() {
            this.mType = new Type();
        }

        public Builder id(int id) {
            this.mType.id = id;
            return this;
        }

        public Builder typeName(String typeName) {
            this.mType.typeName = typeName;
            return this;
        }

        public Type build() {
            return mType;
        }
    }

}
