package com.example.lightdance.androidfinal.page.note;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lightdance.androidfinal.R;
import com.example.lightdance.androidfinal.bean.Note;
import com.example.lightdance.androidfinal.bean.Type;
import com.example.lightdance.androidfinal.dao.NoteCurd;
import com.example.lightdance.androidfinal.dao.TypeCurd;
import com.example.lightdance.androidfinal.utils.FragmentTypeEnum;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

/**
 * @author LightDance
 */
public class NoteListFragment extends Fragment {

    //TODO 修改创建模式，兼容无type参数创建和有type参数创建两种方式

    public static final String SEARCH_ARG = "SEARCH_ARG";
    public static final String TYPE_ARG = "SEARCH_ARG";
    private static final String CURRENT_MODE = "CURRENT_MODE";

    public enum CreateModeEnum {
        /**
         * 从searchView进入List的模式
         */
        SEARCH_MODE(1, "SEARCH_MODE"),
        /**
         * 从类型选择中进入List的模式
         */
        TYPE_MODE(2, "TYPE_MODE");

        private int id;
        private String mode;

        private CreateModeEnum(int id, String mode) {
            this.id = id;
            this.mode = mode;
        }

        public int getId() {
            return id;
        }

        public String getModeName() {
            return mode;
        }
    }

    private Adapter mAdapter;
    private RecyclerView mRecyclerView;

    /**
     * 控制List数据的加载方式，
     * 在{@link #onCreate(Bundle)}和{@link #newInstance(Bundle, CreateModeEnum)}中完成初始化，
     * 通过{@link #changeMode(CreateModeEnum, Bundle)}可以切换
     */
    private CreateModeEnum currentMode;

    private FloatingActionButton mFloatBtnAddNote;

    /**
     * 无参创建NoteListFragment的方法，默认以Search模式创建，
     * search关键字为默认的"",被加入到bundle中
     *
     * @return fragment实例
     */
    public static NoteListFragment newInstance() {
        Bundle args = new Bundle();
        if (args.getString(SEARCH_ARG) == null) {
            args.putString(SEARCH_ARG, "");
        }
        return newInstance(args, CreateModeEnum.SEARCH_MODE);
    }

    /**
     * 带参创建方法，需要指定模式（按搜索关键字or按类型）
     *
     * @param args 包含搜索模式的关键字   key:{@link #SEARCH_ARG}
     *             或类型模式的类型      key:{@link #TYPE_ARG}
     * @param mode 模式
     * @return 新fragment
     */
    public static NoteListFragment newInstance(Bundle args, CreateModeEnum mode) {
        NoteListFragment fragment = new NoteListFragment();
        if (mode.getId() == CreateModeEnum.SEARCH_MODE.getId()) {
            //TODO 按照搜索模式进行处理
            args.putString(CURRENT_MODE, CreateModeEnum.SEARCH_MODE.getModeName());
        } else if (mode.getId() == CreateModeEnum.TYPE_MODE.getId()) {
            //TODO 按照类别模式进行处理
            args.putString(CURRENT_MODE, CreateModeEnum.TYPE_MODE.getModeName());
        }
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 复用fragment时切换模式，
     *
     * @param targetMode 打算切换到这个模式
     * @param args 该模式所需要的参数
     */
    public void changeMode(CreateModeEnum targetMode , Bundle args) {
        args.putString(CURRENT_MODE , targetMode.getModeName());
        setArguments(args);
        currentMode = targetMode;
        updateUI(targetMode);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String modeStr = getArguments().getString(CURRENT_MODE);
            if (modeStr.equals(CreateModeEnum.SEARCH_MODE.getModeName())){
                currentMode = CreateModeEnum.SEARCH_MODE;
            }else if(modeStr.equals(CreateModeEnum.TYPE_MODE.getModeName())){
                currentMode = CreateModeEnum.TYPE_MODE;
            }
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
                ((MainActivity) getActivity()).switchFragment(targetFragment, FragmentTypeEnum.NoteFragmentEnum, FragmentTypeEnum.NoteListFragmentEnum);
            }
        });

        updateUI(currentMode);

        return v;
    }


    /**
     * updateUI方法，根据模式调用对应同名方法
     * @param mode 模式{@link CreateModeEnum}
     */
    private void updateUI(CreateModeEnum mode) {
        switch (mode) {
            case TYPE_MODE:
                //TODO 存在bug，目前args里面存的是type的id而非type类
                TypeCurd typeCurd = new TypeCurd(getActivity());
                List <Type> list = typeCurd.findAllType();

                assert getArguments() != null;
                Type type = list.get(getArguments().getInt(TYPE_ARG)) ;

                updateUI(type);
                break;
            case SEARCH_MODE:
                String keyStr = getArguments().getString(SEARCH_ARG);
                updateUI(keyStr);
                break;
            default:
                break;
        }
    }

    /**
     * 搜索模式调用这个，根据自动从Bundle中获取到的关键字检索并更新list
     * @param keyStr 关键字
     */
    private void updateUI(String keyStr) {
        NoteCurd noteCurd = new NoteCurd(getActivity());
        List<Note> titleMatchList = noteCurd.findNoteByTitle(keyStr);
        //TODO 模糊搜索匹配标题&内容，甚至&时间，&地点
        //List<Note> contentMatchList = noteCurd.findNoteByContent(keyStr);
        //List<Note> list = titleMatchList.addAll(contentMatchList);

        if (mAdapter == null) {
            mAdapter = new Adapter(titleMatchList);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setNoteList(titleMatchList);
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 类型模式用这个，根据自动从Bundle中获取到的type更新List
     * @param type 类型
     */
    private void updateUI(Type type) {
        NoteCurd noteCurd = new NoteCurd(getActivity());
        List<Note> list = noteCurd.findNoteByTypeId(String.valueOf(type.getId()));

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
            FragmentManager fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
            Bundle args = new Bundle();
            args.putSerializable(ARG_NOTE, mNote);
            Fragment targetFragment = fm.findFragmentByTag(FragmentTypeEnum.NoteFragmentEnum.getName());

            assert targetFragment != null;
            targetFragment.setArguments(args);
            ((MainActivity) getActivity()).switchFragment(targetFragment, FragmentTypeEnum.NoteFragmentEnum, FragmentTypeEnum.NoteListFragmentEnum);
        }
    }

}
