package com.example.lightdance.androidfinal.bean;

public class Classify {

    private int id;
    private String classifyName;

    public Classify() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    @Override
    public String toString() {
        return "Classify{" +
                "id=" + id +
                ", classifyName='" + classifyName + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Classify classify;

        public Builder() {
            this.classify = new Classify();
        }

        public Builder id(int id) {
            this.classify.id = id;
            return this;
        }

        public Builder classifyName(String classifyName) {
            this.classify.classifyName = classifyName;
            return this;
        }

        public Classify build() {
            return classify;
        }
    }

}
