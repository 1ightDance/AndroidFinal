package com.example.lightdance.androidfinal.page.note;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.lightdance.androidfinal.R;
import com.example.lightdance.androidfinal.bean.Note;
import com.example.lightdance.androidfinal.page.note.type.AddTypeDialog;
import com.example.lightdance.androidfinal.page.note.type.TypeListFragment;
import com.example.lightdance.androidfinal.utils.FragmentTypeEnum;

import static com.example.lightdance.androidfinal.utils.FragmentTypeEnum.NoteFragmentEnum;
import static com.example.lightdance.androidfinal.utils.FragmentTypeEnum.NoteListFragmentEnum;
import static com.example.lightdance.androidfinal.utils.FragmentTypeEnum.TypeListFragmentEnum;

/**
 * @author LightDance
 * @update 2018/12/16 22:21
 */
public class MainActivity extends AppCompatActivity {
    private static String DIALOG_NEW_TYPE = "DIALOG_NEW_TYPE";
    public static final int REQUEST_NEW_TYPE = 0;
    private static boolean isExit = false;

    Fragment typeListFragment;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO 暂时只有一个fragment
        fm = getSupportFragmentManager();
        typeListFragment = fm.findFragmentById(R.id.container);
        if (typeListFragment == null) {
            typeListFragment = TypeListFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.container, typeListFragment, TypeListFragmentEnum.getName())
                    .add(R.id.container, NoteListFragment.newInstance(), NoteListFragmentEnum.getName())
                    .add(R.id.container, NoteFragment.newInstance(), NoteFragmentEnum.getName())
                    .show(typeListFragment)
                    .commit();

        }
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    /**
     * 菜单相关，打算把添加类别放这里
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * 菜单相关，点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_add_new) {
            Toast.makeText(this, "新增类别", Toast.LENGTH_SHORT).show();
            FragmentManager fm = getSupportFragmentManager();
            AddTypeDialog dialog = AddTypeDialog.newInstance();
            dialog.setTargetFragment(typeListFragment, REQUEST_NEW_TYPE);
            assert fm != null;
            dialog.show(fm, DIALOG_NEW_TYPE);
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        String currentFragment = TypeListFragment.class.getSimpleName();
        for (Fragment fragment : fm.getFragments()) {
            if (!fragment.isHidden() && fragment.isAdded()) {
                currentFragment = fragment.getClass().getSimpleName();
            }
        }

        Log.i("currentFragment : {}", currentFragment);

        switch (currentFragment) {
            case "TypeListFragment":
                exit();
                break;
            case "NoteListFragment":
                switchFragment(fm.findFragmentByTag(TypeListFragmentEnum.getName()), TypeListFragmentEnum, NoteListFragmentEnum);
                break;
            case "NoteFragment":
                switchFragment(fm.findFragmentByTag(NoteListFragmentEnum.getName()), NoteListFragmentEnum, NoteFragmentEnum);
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(this, "再按一次后退键退出程序", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            this.finish();
        }
    }

    /**
     *
     * @param fragment
     * @param fragmentTypeEnum
     * @param targetHide
     */
    public void switchFragment(Fragment fragment, FragmentTypeEnum fragmentTypeEnum, FragmentTypeEnum targetHide) {
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment hide = fm.findFragmentByTag(targetHide.getName());
        transaction.hide(hide);
        transaction.show(fragment);
        transaction.commit();
    }

    public Fragment getFragment(FragmentTypeEnum fragmentTypeEnum) {
        return fm.findFragmentByTag(fragmentTypeEnum.getName());
    }
}
