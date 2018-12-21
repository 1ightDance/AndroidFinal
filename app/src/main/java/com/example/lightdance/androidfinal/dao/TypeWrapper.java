package com.example.lightdance.androidfinal.dao;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.lightdance.androidfinal.bean.Type;

public class TypeWrapper extends CursorWrapper {
    public TypeWrapper(Cursor cursor) {
        super(cursor);
    }

    public Type getType() {
        String id = getString(getColumnIndex(DataBase.TypeSchema.Field.ID));
        String typeName = getString(getColumnIndex(DataBase.TypeSchema.Field.TYPE_NAME));
        return Type.builder().id(Integer.valueOf(id)).typeName(typeName).build();
    }

}
