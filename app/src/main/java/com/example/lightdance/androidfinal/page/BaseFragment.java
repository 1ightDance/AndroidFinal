package com.example.lightdance.androidfinal.page;

import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {

    /**
     * 为每个fragment创建不同的返回按钮点击事件
     * @return
     */
    protected abstract boolean onKeyBackPressed();

    /**
     * 执行真正的数据show操作，由于采用fragment，因此原有的fragment的生命周期内的createView进行数据show操作存在问题
     * 所以在此处新增一个show方法，实现真正的数据渲染操作
     */
    abstract public void show();

}
