package com.example.lightdance.androidfinal.dao;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.lightdance.androidfinal.bean.Note;

public class NoteWrapper extends CursorWrapper {

    public NoteWrapper(Cursor cursor) {
        super(cursor);
    }

    public Note getNote() {
        String id = getString(getColumnIndex(DataBase.NoteSchema.Field.ID));
        String classifyId = getString(getColumnIndex(DataBase.NoteSchema.Field.CLASSIFY_ID));
        String title = getString(getColumnIndex(DataBase.NoteSchema.Field.NOTE_TITLE));
        String modifyTime = getString(getColumnIndex(DataBase.NoteSchema.Field.MODIFY_TIME));
        String location = getString(getColumnIndex(DataBase.NoteSchema.Field.LOCATION));
        String context = getString(getColumnIndex(DataBase.NoteSchema.Field.CONTEXT));
        return Note.builder()
                .id(Integer.valueOf(id))
                .classifyId(Integer.valueOf(classifyId))
                .title(title)
                .location(location)
                .context(context)
                .builded();
    }

}
