package com.example.demoapplication.base;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        //bind view here for all activities extending this Activity
        ButterKnife.bind(this);
    }

    /**
     * get layout to inflate
     */
    public abstract
    @LayoutRes
    int getLayout();

}