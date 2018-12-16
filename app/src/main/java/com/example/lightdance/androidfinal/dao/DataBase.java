package com.example.lightdance.androidfinal.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "note.db";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuilder createNoteSql = new StringBuilder();
        createNoteSql.append("create table ")
                .append(NoteSchema.TABLE_NAME)
                .append("(")
                .append(NoteSchema.Field.ID)
                .append(" integer primary key autoincrement,")
                .append(NoteSchema.Field.CLASSIFY_ID)
                .append(",")
                .append(NoteSchema.Field.NOTE_TITLE)
                .append(",")
                .append(NoteSchema.Field.MODIFY_TIME)
                .append(",")
                .append(NoteSchema.Field.LOCATION)
                .append(",")
                .append(NoteSchema.Field.CONTEXT)
                .append(")");
        StringBuilder createClassifySql = new StringBuilder();
        createClassifySql.append("create table ")
                .append(ClassifySchema.TABLE_NAME)
                .append("(")
                .append(ClassifySchema.Field.ID)
                .append(" integer primary key autoincrement,")
                .append(ClassifySchema.Field.CLASSIFY_NAME)
                .append(")");
        sqLiteDatabase.execSQL(createNoteSql.toString());
        sqLiteDatabase.execSQL(createClassifySql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    protected static final class NoteSchema {
        public static final String TABLE_NAME = "Note";

        public static final class Field {
            public static final String ID = "id";
            public static final String CLASSIFY_ID = "classifyId";
            public static final String NOTE_TITLE = "title";
            public static final String MODIFY_TIME = "modifyTime";
            public static final String LOCATION = "location";
            public static final String CONTEXT = "context";
        }
    }

    protected static final class ClassifySchema {
        public static final String TABLE_NAME = "Classify";

        public static final class Field {
            public static final String ID = "id";
            public static final String CLASSIFY_NAME = "classifyName";
        }
    }


}
