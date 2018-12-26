package com.example.lightdance.androidfinal.page.note;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.lightdance.androidfinal.R;
import com.example.lightdance.androidfinal.page.BaseFragment;
import com.example.lightdance.androidfinal.page.note.type.AddTypeDialog;
import com.example.lightdance.androidfinal.page.note.type.TypeListFragment;
import com.example.lightdance.androidfinal.utils.FragmentTypeEnum;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO 一次性创建所有Fragment，方便复用， 但全是bug
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
