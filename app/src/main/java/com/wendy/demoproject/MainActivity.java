package com.wendy.demoproject;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.wendy.demoproject.customView.ui.CustomViewActivity;

import java.lang.reflect.Method;


/**
 * Created by Administrator on 2016/7/20 0020.
 */
public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] items = {"自定义View"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        this.setListAdapter(adapter);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(MainActivity.this, CustomViewActivity.class));
                        break;
                }
            }
        });

        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
    }


    public void onWindowFocusChanged(boolean hasFocus) {
        disableStatusBar();
        super.onWindowFocusChanged(hasFocus);
    }

    public void disableStatusBar() {
        try {
            Object service = getSystemService("statusbar");
            Class<?> claz = Class.forName("android.app.StatusBarManager");
            Method expand = claz.getMethod("collapse");
            expand.invoke(service);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
