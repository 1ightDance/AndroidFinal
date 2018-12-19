package com.example.lightdance.androidfinal.page.note;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lightdance.androidfinal.R;
import com.example.lightdance.androidfinal.bean.Note;
import com.example.lightdance.androidfinal.dao.NoteCurd;

import java.util.List;

/**
 * @author LightDance
 */
public class NoteListFragment extends Fragment {

    private Adapter mAdapter;
    private RecyclerView mRecyclerView;

    public static NoteListFragment newInstance(int classifyId) {
        NoteListFragment fragment = new NoteListFragment();
        Bundle args = new Bundle();
        args.putString("classifyId", String.valueOf(classifyId));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //if need args
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_note_list, container, false);
        mRecyclerView = v.findViewById(R.id.recycler_view_note_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI(getArguments().getString("classifyId"));
        return v;
    }

    private void updateUI(String classifyId) {

        NoteCurd noteCurd = new NoteCurd(getActivity());
        //TODO set Id
        List<Note> list = noteCurd.findNoteByClassifyId(classifyId);

        if (mAdapter == null) {
            mAdapter = new Adapter(list);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setNoteList(list);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private List<Note> mNoteList;

        public Adapter(List<Note> items) {
            mNoteList = items;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new ViewHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            Note note = mNoteList.get(position);
            holder.bind(note);
        }

        @Override
        public int getItemCount() {
            return mNoteList.size();
        }

        public void setNoteList(List<Note> noteList) {
            mNoteList = noteList;
        }
    }

    /**
     * 用于item绑定布局文件
     */
    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //TODO 通过class id 查找 class name ， 完善控件 ， 完善点击事件

        private TextView mTvClassId;
        private Note mNote;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_type, parent, false));
            mTvClassId = itemView.findViewById(R.id.tv_item_note_type);
            itemView.setOnClickListener(this);
        }

        void bind(Note note) {
            mNote = note;
            mTvClassId.setText(mNote.getClassifyId());
        }

        @Override
        public void onClick(View view) {
            //TODO add logic
            Toast.makeText(getContext(), "我被点了", Toast.LENGTH_SHORT).show();
        }
    }

}
