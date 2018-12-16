package com.example.lightdance.androidfinal.page.note;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lightdance.androidfinal.R;
import com.example.lightdance.androidfinal.bean.Classify;
import com.example.lightdance.androidfinal.dao.ClassifyCurd;

import java.util.List;

import static com.example.lightdance.androidfinal.page.note.MainActivity.REQUEST_NEW_TYPE;

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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
    private void updateUI(){
        ClassifyCurd classifyCurd = new ClassifyCurd(getActivity());
        List<Classify> list = classifyCurd.findAllClassify();

        if (mAdapter == null){
            mAdapter = new Adapter(list);
            mRecyclerView.setAdapter(mAdapter);
        }else {
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 用于item绑定布局文件
     */
    private class TypeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTvClass;
        private Classify mClassify;

        TypeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_type, parent, false));
            mTvClass = itemView.findViewById(R.id.tv_item_type_name);
            itemView.setOnClickListener(this);
        }

        void bind(Classify classify) {
            mClassify = classify;
            mTvClass.setText(mClassify.getClassifyName());
        }

        @Override
        public void onClick(View v) {
            Snackbar.make(v , getAdapterPosition() + ", 我让人点了", Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * 适配器，Fragment实际上是通过适配器将数据转化成可见信息的
     */
    private class Adapter extends RecyclerView.Adapter<TypeHolder> {
        private List<Classify> mList;

        public Adapter(List<Classify> list) {
            mList = list;
        }

        @NonNull
        @Override
        public TypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new TypeHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull TypeHolder holder, int position) {
            Classify classify = mList.get(position);
            holder.bind(classify);
        }

        @Override
        public int getItemCount() {
            return mList.size();
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
                //TODO 显示不出来？
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
