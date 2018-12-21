package com.example.lightdance.androidfinal.page.note.type;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lightdance.androidfinal.R;
import com.example.lightdance.androidfinal.bean.Type;
import com.example.lightdance.androidfinal.dao.TypeCurd;
import com.example.lightdance.androidfinal.page.note.MainActivity;
import com.example.lightdance.androidfinal.page.note.NoteListFragment;
import com.example.lightdance.androidfinal.utils.FragmentTypeEnum;

import java.util.List;

/**
 * @author LightDance
 * @update 2018/12/16 22:30
 */
public class TypeListFragment extends Fragment {

    private Adapter mAdapter;
    private RecyclerView mRecyclerView;

    public static TypeListFragment newInstance() {
        TypeListFragment fragment = new TypeListFragment();
        Bundle args = new Bundle();
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
        View v =inflater.inflate(R.layout.fragment_type_list, container, false);
        mRecyclerView = v.findViewById(R.id.recycler_view_type_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity() , 2));
        updateUI();
        return v;
    }

    private void updateUI(){
        TypeCurd typeCurd = new TypeCurd(getActivity());
        List<Type> list = typeCurd.findAllType();

        if (mAdapter == null){
            mAdapter = new Adapter(list);
            mRecyclerView.setAdapter(mAdapter);
        }else {
            mAdapter.setList(list);
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 用于item绑定布局文件
     */
    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTvClass;
        private Type mType;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_type, parent, false));
            mTvClass = itemView.findViewById(R.id.tv_item_type_name);
            itemView.setOnClickListener(this);
        }

        void bind(Type type) {
            mType = type;
            mTvClass.setText(mType.getTypeName());
        }

        @Override
        public void onClick(View v) {
            Fragment noteListFragment = ((MainActivity) getActivity()).getFragment(FragmentTypeEnum.NoteFragmentEnum);
            Bundle args = new Bundle();
            args.putSerializable(Type.TYPE, mAdapter.getClickItem(getAdapterPosition()));
            noteListFragment.setArguments(args);
            ((MainActivity) getActivity()).switchFragment(noteListFragment, FragmentTypeEnum.NoteListFragmentEnum, FragmentTypeEnum.TypeListFragmentEnum);
        }
    }

    /**
     * 适配器，Fragment实际上是通过适配器将数据转化成可见信息的
     */
    private class Adapter extends RecyclerView.Adapter<ViewHolder> {
        private List<Type> mList;

        public Adapter(List<Type> list) {
            mList = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new ViewHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Type type = mList.get(position);
            holder.bind(type);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        /**
         * 重设adapter的局部变量list,
         * 配合{@link Adapter#notifyDataSetChanged()}实时更新数据
         *
         * @param list 新的数据源
         */
        public void setList(List<Type> list) {
            mList = list;
        }

        public Type getClickItem(int position) {
            return mList.get(position);
        }
    }

    /**
     * 重写用来响应DatePicker传过来的数据
     *
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        数据
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == MainActivity.REQUEST_NEW_TYPE) {
                updateUI();
            }
        }
    }
}
