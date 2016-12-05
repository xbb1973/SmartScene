package com.xbb.preference;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xbb.smartscene.R;

/**
 * Created by HongYilin 16-11-21 下午5:00
 */
public class CommonPreference extends Preference {

    public CommonPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setLayoutResource(R.layout.common_preference);
    }

    public CommonPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        setLayoutResource(R.layout.common_preference);
    }

    public CommonPreference(Context context) {
        super(context);

        setLayoutResource(R.layout.common_preference);
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
