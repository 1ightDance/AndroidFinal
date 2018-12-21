package com.example.lightdance.androidfinal.page.note;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lightdance.androidfinal.R;
import com.example.lightdance.androidfinal.bean.Note;
import com.example.lightdance.androidfinal.dao.NoteCurd;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment {

    private EditText noteTitleEdit;
    private EditText noteContextEdit;
    private EditText noteLocation;
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
        noteTitleEdit = view.findViewById(R.id.note_title);
        noteContextEdit = view.findViewById(R.id.note_context);
        noteLocation = view.findViewById(R.id.note_location);
        note = (Note) getArguments().get("NOTE");
        noteTitleEdit.setText(note.getTitle());
        noteContextEdit.setText(note.getContext());
        titleModify();
        contextModify();
        locationModify();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    note.setModifyTime(new Date());
                    if (note.isNew()) {
                        Toast.makeText(getActivity(), "保存", Toast.LENGTH_LONG).show();
                        noteCurd.createNote(note);
                    } else {
                        noteCurd.updateNote(note);
                    }
                    return false;
                }
                return false;
            }
        });
    }

    private void titleModify() {
        noteTitleEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

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

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                note.setContext(editable.toString());
            }
        });
    }

    private void locationModify() {
        noteLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                note.setLocation(editable.toString());
            }
        });
    }

}
