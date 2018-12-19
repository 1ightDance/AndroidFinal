package com.example.lightdance.androidfinal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lightdance.androidfinal.bean.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoteCurd {

    private SQLiteDatabase database;
    private SimpleDateFormat dateFormat;

    public NoteCurd(Context context) {
        database = new DataBase(context).getWritableDatabase();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }

    /**
     * 添加笔记信息
     *
     * @param note {@link Note}
     * @return 返回创建的该笔记的 {@link Long id}
     */
    public long createNote(Note note) {
        ContentValues values = new ContentValues();
        values.put(DataBase.NoteSchema.Field.CLASSIFY_ID, note.getClassifyId());
        values.put(DataBase.NoteSchema.Field.NOTE_TITLE, note.getTitle());
        values.put(DataBase.NoteSchema.Field.MODIFY_TIME, dateFormat.format(note.getModifyTime()));
        values.put(DataBase.NoteSchema.Field.LOCATION, note.getLocation());
        values.put(DataBase.NoteSchema.Field.CONTEXT, note.getContext());
        return database.insert(DataBase.NoteSchema.TABLE_NAME, null, values);
    }

    /**
     * 更新笔记信息
     *
     * @param note
     * @return
     */
    public boolean updateNote(Note note) {
        ContentValues values = new ContentValues();
        values.put(DataBase.NoteSchema.Field.ID, note.getId());
        values.put(DataBase.NoteSchema.Field.CLASSIFY_ID, note.getClassifyId());
        values.put(DataBase.NoteSchema.Field.NOTE_TITLE, note.getTitle());
        values.put(DataBase.NoteSchema.Field.MODIFY_TIME, dateFormat.format(note.getModifyTime()));
        values.put(DataBase.NoteSchema.Field.LOCATION, note.getLocation());
        values.put(DataBase.NoteSchema.Field.CONTEXT, note.getContext());
        return 0 != database.update(DataBase.NoteSchema.TABLE_NAME, values, "id=?", new String[]{String.valueOf(note.getId())});
    }

    public boolean deleteNode(int noteId) {
        return 0 != database
                .delete(DataBase.NoteSchema.TABLE_NAME,
                        "id=",
                        new String[]{String.valueOf(noteId)});
    }

    /**
     * @param classifyId
     * @return
     */
    public List<Note> findNoteByClassifyId(String classifyId) {
        List<Note> notes = new ArrayList<>();
        Cursor cursor = database
                .query(DataBase.NoteSchema.TABLE_NAME,
                        null,
                        "classifyId=?",
                        new String[]{classifyId},
                        null,
                        null,
                        null);
        NoteWrapper noteWrapper = new NoteWrapper(cursor);
        noteWrapper.moveToFirst();
        while (!noteWrapper.isAfterLast()) {
            notes.add(noteWrapper.getNote());
            noteWrapper.moveToNext();
        }
        return notes;
    }

    /**
     * 根据笔记修改时间查询
     * @param start
     * @param end
     * @return
     */
    public List<Note> findNoteByDate(Date start, Date end) {
        List<Note> notes = new ArrayList<>();
        Cursor cursor = database
                .query(DataBase.NoteSchema.TABLE_NAME,
                        null,
                        "datetime(" + DataBase.NoteSchema.Field.MODIFY_TIME + ")  between datetime(?) and datetime(?)",
                        new String[]{dateFormat.format(start), dateFormat.format(end)},
                        null,
                        null,
                        null);
        NoteWrapper noteWrapper = new NoteWrapper(cursor);
        noteWrapper.moveToFirst();
        while (!noteWrapper.isAfterLast()) {
            noteWrapper.moveToNext();
            notes.add(noteWrapper.getNote());
        }
        return notes;
    }

    /**
     * 根据笔记标题模糊查询
     * @param title
     * @return
     */
    public List<Note> findNoteByTitle(String title) {
        List<Note> notes = new ArrayList<>();
        Cursor cursor = database
                .query(DataBase.NoteSchema.TABLE_NAME,
                        null,
                        "title like ?",
                        new String[]{ "%" + title +"%"},
                        null,
                        null,
                        null);
        NoteWrapper noteWrapper = new NoteWrapper(cursor);
        noteWrapper.moveToFirst();
        while (!noteWrapper.isAfterLast()) {
            notes.add(noteWrapper.getNote());
            noteWrapper.moveToNext();
        }
        return notes;
    }

}
