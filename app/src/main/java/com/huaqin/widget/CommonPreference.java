package com.huaqin.widget;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by HongYilin 16-11-21 下午5:00
 */
public class CommonPreference extends Preference {

    public CommonPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CommonPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommonPreference(Context context) {
        super(context);
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        return super.onCreateView(parent);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
    }
}
