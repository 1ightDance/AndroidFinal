package com.example.lightdance.androidfinal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lightdance.androidfinal.bean.Classify;

import java.util.ArrayList;
import java.util.List;

public class ClassifyCurd {

    private SQLiteDatabase database;

    public ClassifyCurd(Context context) {
        database = new DataBase(context).getWritableDatabase();
    }

    /**
     * 创建一个新的笔记类别
     *
     * @param classify
     * @return 返回创建的该类别的 {@link Long id}
     */
    public long createClassify(Classify classify) {
        ContentValues values = new ContentValues();
        values.put(DataBase.ClassifySchema.Field.CLASSIFY_NAME, classify.getClassifyName());
        return database.insert(DataBase.ClassifySchema.TABLE_NAME, null, values);
    }

    /**
     * 更新笔记类别信息
     *
     * @param classify
     * @return
     */
    public boolean updateClassify(Classify classify) {
        ContentValues values = new ContentValues();
        values.put(DataBase.ClassifySchema.Field.ID, classify.getId());
        values.put(DataBase.ClassifySchema.Field.CLASSIFY_NAME, classify.getClassifyName());
        return 0 != database.update(
                DataBase.ClassifySchema.TABLE_NAME,
                values,
                "id=?",
                new String[]{String.valueOf(classify.getId())});
    }

    /**
     * 删除某一个笔记类别
     *
     * @param classifyId
     * @return
     */
    public boolean deleteClassify(int classifyId) {
        return 0 != database.delete(DataBase.ClassifySchema.TABLE_NAME, "id=?", new String[]{String.valueOf(classifyId)});
    }

    /**
     * 查找全部的笔记类别
     *
     * @return
     */
    public List<Classify> findAllClassify() {
        List<Classify> classifies = new ArrayList<>();
        Cursor cursor = database.query(DataBase.ClassifySchema.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        ClassifyWrapper classifyWrapper = new ClassifyWrapper(cursor);
        classifyWrapper.moveToFirst();
        while (!classifyWrapper.isAfterLast()) {
            classifies.add(classifyWrapper.getClassify());
            classifyWrapper.moveToNext();
        }
        return classifies;
    }

}
