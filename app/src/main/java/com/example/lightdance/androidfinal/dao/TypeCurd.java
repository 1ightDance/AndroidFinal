package com.example.lightdance.androidfinal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lightdance.androidfinal.bean.Type;

import java.util.ArrayList;
import java.util.List;

public class TypeCurd {

    private SQLiteDatabase database;

    public TypeCurd(Context context) {
        database = new DataBase(context).getWritableDatabase();
    }

    /**
     * 创建一个新的笔记类别
     *
     * @param type
     * @return 返回创建的该类别的 {@link Long id}
     */
    public long createType(Type type) {
        ContentValues values = new ContentValues();
        values.put(DataBase.TypeSchema.Field.TYPE_NAME, type.getTypeName());
        return database.insert(DataBase.TypeSchema.TABLE_NAME, null, values);
    }

    /**
     * 更新笔记类别信息
     *
     * @param type
     * @return
     */
    public boolean updateType(Type type) {
        ContentValues values = new ContentValues();
        values.put(DataBase.TypeSchema.Field.ID, type.getId());
        values.put(DataBase.TypeSchema.Field.TYPE_NAME, type.getTypeName());
        return 0 != database.update(
                DataBase.TypeSchema.TABLE_NAME,
                values,
                "id=?",
                new String[]{String.valueOf(type.getId())});
    }

    /**
     * 删除某一个笔记类别
     *
     * @param typeId
     * @return
     */
    public boolean deleteType(int typeId) {
        return 0 != database.delete(DataBase.TypeSchema.TABLE_NAME, "id=?", new String[]{String.valueOf(typeId)});
    }

    /**
     * 查找全部的笔记类别
     *
     * @return
     */
    public List<Type> findAllType() {
        List<Type> classifies = new ArrayList<>();
        Cursor cursor = database.query(DataBase.TypeSchema.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        TypeWrapper typeWrapper = new TypeWrapper(cursor);
        typeWrapper.moveToFirst();
        while (!typeWrapper.isAfterLast()) {
            classifies.add(typeWrapper.getType());
            typeWrapper.moveToNext();
        }
        return classifies;
    }

}
