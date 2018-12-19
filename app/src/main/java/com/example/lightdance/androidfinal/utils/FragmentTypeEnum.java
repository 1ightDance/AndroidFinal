package com.example.lightdance.androidfinal.utils;

/**
 * fragment名称注册
 */
public enum  FragmentTypeEnum {

    /**
     *
     */
    TypeListFragmentEnum(1, "TypeListFragment"),
    NoteListFragmentEnum(2, "NoteListFragment"),
    NoteFragmentEnum(3, "NoteFragment");

    private int value;
    private String name;

    FragmentTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

}