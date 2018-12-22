package com.example.lightdance.androidfinal.page;

import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {

    public boolean onKeyBackPressed() {
        return false;
    }

}
