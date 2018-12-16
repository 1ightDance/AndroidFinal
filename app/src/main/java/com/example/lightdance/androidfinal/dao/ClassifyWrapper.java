package com.example.lightdance.androidfinal.dao;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.lightdance.androidfinal.bean.Classify;

public class ClassifyWrapper extends CursorWrapper {
    public ClassifyWrapper(Cursor cursor) {
        super(cursor);
    }

    public Classify getClassify() {
        String id = getString(getColumnIndex(DataBase.ClassifySchema.Field.ID));
        String classifyName = getString(getColumnIndex(DataBase.ClassifySchema.Field.CLASSIFY_NAME));
        return Classify.builder().id(Integer.valueOf(id)).classifyName(classifyName).builded();
    }

}
