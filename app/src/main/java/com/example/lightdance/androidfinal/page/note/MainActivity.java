package com.example.lightdance.androidfinal.page.note;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.lightdance.androidfinal.R;
import com.example.lightdance.androidfinal.page.BaseFragment;
import com.example.lightdance.androidfinal.page.note.type.AddTypeDialog;
import com.example.lightdance.androidfinal.page.note.type.TypeListFragment;
import com.example.lightdance.androidfinal.utils.FragmentTypeEnum;
import com.idescout.sql.SqlScoutServer;

import java.util.List;

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

    Fragment typeListFragment;
    FragmentManager fm;

    SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SqlScoutServer.create(this, getPackageName());

        fm = getSupportFragmentManager();
        typeListFragment = fm.findFragmentById(R.id.container);
        if (typeListFragment == null) {
            typeListFragment = TypeListFragment.newInstance();
            NoteListFragment noteListFragment = NoteListFragment.newInstance();
            NoteFragment noteFragment = NoteFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.container, typeListFragment, TypeListFragmentEnum.getName())
                    .add(R.id.container, noteListFragment, NoteListFragmentEnum.getName())
                    .add(R.id.container, noteFragment, NoteFragmentEnum.getName())
                    .hide(noteListFragment)
                    .hide(noteFragment)
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
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        //通过MenuItem得到SearchView
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //开局就显示搜索框
        //mSearchView.setIconified(false);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //传递参数
                FragmentManager fm = getSupportFragmentManager();
                NoteListFragment fragment = (NoteListFragment) fm.findFragmentByTag(FragmentTypeEnum.NoteListFragmentEnum.getName());
                Bundle args = new Bundle();
                args.putString(NoteListFragment.SEARCH_ARG , s);
                //切换模式
                fragment.changeMode(NoteListFragment.CreateModeEnum.SEARCH_MODE , args);
                switchFragment(fragment , FragmentTypeEnum.NoteListFragmentEnum , FragmentTypeEnum.TypeListFragmentEnum);
                //提醒等待
                Snackbar.make(getCurrentFocus() , "正在搜索..." , Snackbar.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
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
        List<Fragment> fragments = fm.getFragments();
        for (Fragment fragment : fragments) {
            BaseFragment baseFragment = (BaseFragment) fragment;
            if (baseFragment.isAdded() && !baseFragment.isHidden()) {
                baseFragment.onKeyBackPressed();
            }
        }
    }

    /**
     *
     * @param fragment
     * @param fragmentTypeEnum
     * @param targetHide
     */
    public void switchFragment(BaseFragment fragment, FragmentTypeEnum fragmentTypeEnum, FragmentTypeEnum targetHide) {
        getSupportActionBar().hide();
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment hide = fm.findFragmentByTag(targetHide.getName());
        fragment.show();
        transaction.hide(hide);
        transaction.show(fragment);
        transaction.commit();
    }

    public Fragment getFragment(FragmentTypeEnum fragmentTypeEnum) {
        return fm.findFragmentByTag(fragmentTypeEnum.getName());
    }

}
