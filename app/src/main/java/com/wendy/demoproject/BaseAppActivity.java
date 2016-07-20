package com.wendy.demoproject;

import android.content.Context;
import android.os.Bundle;

import com.eagle.androidlib.baseUI.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/20 0020.
 */
public abstract class BaseAppActivity extends BaseActivity {

    private Context context;

    public Context getContext(){
        if (context == null){
            context = this;
        }
        return context;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

}
