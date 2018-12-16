package com.example.lightdance.androidfinal.page.note;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.lightdance.androidfinal.R;

/**
 * @author LightDance
 * @update 2018/12/16 22:21
 */
public class MainActivity extends AppCompatActivity {
    private static String DIALOG_NEW_TYPE = "DIALOG_NEW_TYPE";
    public static final int REQUEST_NEW_TYPE = 0;

    Fragment typeListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO 暂时只有一个fragment
        FragmentManager fm = getSupportFragmentManager();
        typeListFragment = fm.findFragmentById(R.id.container);
        if (typeListFragment == null) {
            typeListFragment = TypeListFragment.newInstance();
            fm.beginTransaction().add(R.id.container, typeListFragment).commit();
        }
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    /**菜单相关，打算把添加类别放这里*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    /**菜单相关，点击事件*/
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
            dialog.setTargetFragment(typeListFragment , REQUEST_NEW_TYPE);
            assert fm != null;
            dialog.show(fm, DIALOG_NEW_TYPE);
            return false;
        }

        return super.onOptionsItemSelected(item);
    }


}
