package com.xbb.smartscene;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.xbb.triggler.AlarmTrigger;
import com.xbb.triggler.SceneTrigger;

/**
 * Created by HongYilin 16-11-29 下午2:56
 */
public class AlarmSettingActivity extends Activity implements View.OnClickListener {

    Context mContext;
    AlarmTrigger mAlarmTrigger;

    View mStart, mEnd;
    TextView mStartTime, mEndTime;
    RadioButton mFrequencyOnceR, mFrequencyEveryDayR, mFrequencyweekDayR, mFrequencyWeekEndR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
        initListener();
    }

    private void initListener() {
        mStart.setOnClickListener(this);
        mEnd.setOnClickListener(this);

        mFrequencyOnceR.setOnClickListener(this);
        mFrequencyEveryDayR.setOnClickListener(this);
        mFrequencyweekDayR.setOnClickListener(this);
        mFrequencyWeekEndR.setOnClickListener(this);
    }

    private void initData() {
        mContext = getApplicationContext();
        mAlarmTrigger = getIntent().getParcelableExtra(SceneTrigger.SCENETRIGGER);
    }

    private void initView() {
        setContentView(R.layout.activity_setting_alarm);

        mStart = findViewById(R.id.start_layout);
        mEnd = findViewById(R.id.end_layout);

        mStartTime = (TextView) findViewById(R.id.start_time_summary);
        mEndTime = (TextView) findViewById(R.id.end_time_summary);

        mFrequencyOnceR = (RadioButton) findViewById(R.id.onceR);
        mFrequencyEveryDayR = (RadioButton) findViewById(R.id.everydayR);
        mFrequencyweekDayR = (RadioButton) findViewById(R.id.weekdayR);
        mFrequencyWeekEndR = (RadioButton) findViewById(R.id.weekendR);


        mStartTime.setText(mAlarmTrigger.getStartHour() + ":" + mAlarmTrigger.getStartMinutes());
        mEndTime.setText(mAlarmTrigger.getEndHour() + ":" + mAlarmTrigger.getEndMinutes());
        switch (mAlarmTrigger.getFrequency()) {
            case AlarmTrigger.ONCE:
                mFrequencyOnceR.setChecked(true);
                break;
            case AlarmTrigger.EVERYDAY:
                mFrequencyEveryDayR.setChecked(true);
                break;
            case AlarmTrigger.WEEKDAY:
                mFrequencyweekDayR.setChecked(true);
                break;
            case AlarmTrigger.WEEKEND:
                mFrequencyWeekEndR.setChecked(true);
                break;
        }

    }

    @Override
    public void onClick(View view) {
        setResult(1);
    }
}
