package com.example.lightdance.androidfinal.page.note;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.lightdance.androidfinal.R;
import com.example.lightdance.androidfinal.bean.Note;
import com.example.lightdance.androidfinal.dao.NoteCurd;
import com.example.lightdance.androidfinal.page.BaseFragment;
import com.example.lightdance.androidfinal.utils.FragmentTypeEnum;

import java.util.Date;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends BaseFragment {

    private EditText noteTitleEdit;
    private EditText noteContextEdit;
    private NoteCurd noteCurd;
    private Note note;

    public NoteFragment() {}

    public static NoteFragment newInstance() {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        noteCurd = new NoteCurd(getActivity());
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        noteTitleEdit = view.findViewById(R.id.et_new_note_title);
        noteContextEdit = view.findViewById(R.id.note_context);
        return view;
    }

    @Override
    public boolean onKeyBackPressed() {
        note.setModifyTime(new Date());
        note.setLocation("Null");
        if (note.isNew()) {
            Log.i("note——id ", String.valueOf(noteCurd.createNote(note)));
        } else {
            noteCurd.updateNote(note);
        }
        sendResult(Activity.RESULT_OK);
        FragmentManager fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        BaseFragment targetFragment = (BaseFragment) fm.findFragmentByTag(FragmentTypeEnum.NoteListFragmentEnum.getName());
        ((MainActivity) getActivity()).switchFragment(targetFragment, FragmentTypeEnum.NoteListFragmentEnum, FragmentTypeEnum.NoteFragmentEnum);
        return true;
    }

    @Override
    public void show() {
        noteTitleEdit.setText("");
        noteContextEdit.setText("");
        note = (Note) getArguments().get(Note.NOTE);
        if (note == null) {
            note = new Note();
            note.setNew(true);
        }
        noteTitleEdit.setText(note.getTitle());
        noteContextEdit.setText(note.getContent());
        titleModify();
        contextModify();
    }

    private void titleModify() {
        noteTitleEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                note.setTitle("无主题");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                note.setTitle("无主题");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                note.setTitle(editable.toString());
            }
        });
    }

    private void contextModify() {
        noteContextEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                note.setContent("");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                note.setContent(editable.toString());
            }
        });
    }

    @Deprecated
    private void sendResult(int resultCode) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

}
