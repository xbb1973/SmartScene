package com.xbb.preference;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.xbb.smartscene.R;

/**
 * Created by HongYilin 16-11-28 下午4:11
 */
public class EditPreference extends Preference {

    private EditText editText;

    public EditPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayoutResource(R.layout.edit_preference);
    }

    public EditPreference(Context context) {
        super(context);
        setLayoutResource(R.layout.edit_preference);
    }

    public EditPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutResource(R.layout.edit_preference);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        final EditText editText = (EditText) view.findViewById(R.id.edit_text);

    }
}
