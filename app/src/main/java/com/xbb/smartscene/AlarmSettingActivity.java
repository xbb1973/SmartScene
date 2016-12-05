package com.xbb.smartscene;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by HongYilin 16-11-29 下午2:56
 */
public class AlarmSettingActivity extends Activity {

    Context mContext;


    TextView mTimeHeadText;
    TextView mStartTime;
    TextView mEndTime;

    TextView mFrequencyHeadText;
    TextView mFrequencyOnceT;
    TextView mFrequencyEveryDayT;
    TextView mFrequencyweekDayT;
    TextView mFrequencyWeekEndT;

    RadioButton mFrequencyOnceR;
    RadioButton mFrequencyEveryDayR;
    RadioButton mFrequencyweekDayR;
    RadioButton mFrequencyWeekEndR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    private void initData() {
        mContext = getApplicationContext();
    }

    private void initView() {
        setContentView(R.layout.activity_setting_alarm);

        mTimeHeadText = (TextView) findViewById(R.id.time_head);
        mStartTime = (TextView) findViewById(R.id.start_time);
        mEndTime = (TextView) findViewById(R.id.end_time);

        mFrequencyHeadText = (TextView) findViewById(R.id.frequency_head);
        mFrequencyOnceT = (TextView) findViewById(R.id.onceT);
        mFrequencyEveryDayT = (TextView) findViewById(R.id.everydayT);
        mFrequencyweekDayT = (TextView) findViewById(R.id.weekdayT);
        mFrequencyWeekEndT = (TextView) findViewById(R.id.weekendT);

        mFrequencyOnceR = (RadioButton) findViewById(R.id.onceR);
        mFrequencyEveryDayR = (RadioButton) findViewById(R.id.everydayR);
        mFrequencyweekDayR = (RadioButton) findViewById(R.id.weekdayR);
        mFrequencyWeekEndR = (RadioButton) findViewById(R.id.weekendR);

    }
}
