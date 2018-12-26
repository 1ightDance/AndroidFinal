package com.example.lightdance.androidfinal.dao;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.lightdance.androidfinal.bean.Note;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteWrapper extends CursorWrapper {

    private SimpleDateFormat dateFormat;

    public NoteWrapper(Cursor cursor) {
        super(cursor);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }

    public Note getNote() {
        Note note;
        String id = getString(getColumnIndex(DataBase.NoteSchema.Field.ID));
        String typeId = getString(getColumnIndex(DataBase.NoteSchema.Field.TYPE_ID));
        String title = getString(getColumnIndex(DataBase.NoteSchema.Field.NOTE_TITLE));
        String modifyTime = getString(getColumnIndex(DataBase.NoteSchema.Field.MODIFY_TIME));
        String location = getString(getColumnIndex(DataBase.NoteSchema.Field.LOCATION));
        String context = getString(getColumnIndex(DataBase.NoteSchema.Field.CONTEXT));
        try {
             note = Note.builder()
                     .id(Integer.valueOf(id))
                     .typeId(Integer.valueOf(typeId))
                     .title(title)
                     .location(location)
                     .modifyTime(dateFormat.parse(modifyTime))
                     .context(context)
                     .isNew(false)
                     .builded();
        } catch (ParseException e) {
            note = Note.builder()
                    .id(Integer.valueOf(id))
                    .typeId(Integer.valueOf(typeId))
                    .title(title)
                    .location(location)
                    .modifyTime(new Date())
                    .context(context)
                    .isNew(false)
                    .builded();
        }
        return note;
    }

}
