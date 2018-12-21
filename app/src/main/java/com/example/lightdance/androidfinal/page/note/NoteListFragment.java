package com.example.lightdance.androidfinal.page.note;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lightdance.androidfinal.R;
import com.example.lightdance.androidfinal.bean.Note;
import com.example.lightdance.androidfinal.dao.TypeCurd;
import com.example.lightdance.androidfinal.dao.NoteCurd;
import com.example.lightdance.androidfinal.utils.FragmentTypeEnum;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

/**
 * @author LightDance
 */
public class NoteListFragment extends Fragment {

    private Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatBtnAddNote;

    public static NoteListFragment newInstance(int typeId) {
        NoteListFragment fragment = new NoteListFragment();
        Bundle args = new Bundle();
        args.putString("typeId", String.valueOf(typeId));
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

        mFloatBtnAddNote = v.findViewById(R.id.float_btn_add_new_note);
        mFloatBtnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Fragment targetFragment = fm.findFragmentByTag(FragmentTypeEnum.NoteFragmentEnum.getName());
                ((MainActivity)getActivity()).switchFragment(targetFragment , FragmentTypeEnum.NoteFragmentEnum , FragmentTypeEnum.NoteListFragmentEnum);
            }
        });

        updateUI(getArguments().getString("typeId"));

        return v;
    }

    private void updateUI(String typeId) {

        NoteCurd noteCurd = new NoteCurd(getActivity());
        List<Note> list = noteCurd.findNoteByTypeId(typeId);

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

        public static final String ARG_NOTE = "ARG_NOTE";
        //TODO 完善点击事件

        private TextView mTvClassName;
        private TextView mTvTitle;
        private TextView mTvTime;
        private TextView mTvContent;
        private TextView mTvId;
        private TextView mTvLocation;

        private Note mNote;
        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_type, parent, false));

            mTvId = itemView.findViewById(R.id.tv_item_note_id);
            mTvTitle = itemView.findViewById(R.id.tv_item_note_title);
            mTvClassName = itemView.findViewById(R.id.tv_item_note_type);
            mTvContent = itemView.findViewById(R.id.tv_item_note_content);
            mTvTime = itemView.findViewById(R.id.tv_item_note_time);
            mTvLocation = itemView.findViewById(R.id.tv_item_note_location);
        }

        void bind(Note note) {
            mNote = note;
            mTvId.setText(mNote.getId());
            mTvTitle.setText(mNote.getTitle());
            mTvClassName.setText(mNote.getTypeName());
            mTvContent.setText(mNote.getContent());
            mTvLocation.setText(mNote.getLocation());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            mTvTime.setText(dateFormat.format(mNote.getModifyTime()));
        }

        @Override
        public void onClick(View view) {
            //TODO 跳转并传递数据

            FragmentManager fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
            Bundle args = new Bundle();
            args.putSerializable(ARG_NOTE , mNote);
            Fragment targetFragment = fm.findFragmentByTag(FragmentTypeEnum.NoteFragmentEnum.getName());

            targetFragment.setArguments(args);
            ((MainActivity)getActivity()).switchFragment(targetFragment , FragmentTypeEnum.NoteFragmentEnum , FragmentTypeEnum.NoteListFragmentEnum);


            Toast.makeText(getContext(), "我被点了", Toast.LENGTH_SHORT).show();
        }
    }

}
