package com.example.lightdance.androidfinal.page;

import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {

    public boolean onKeyBackPressed() {
        return false;
    }

    /**
     * 执行真正的数据show操作，由于采用fragment，因此原有的fragment的生命周期内的createView进行数据show操作存在问题
     */
    abstract public void show();

}
