package com.huaqin.triggler;

import com.huaqin.provider.SmartScene;

/**
 * Created by HongYilin 16-11-22 下午10:11
 */
public class AlarmTrigger extends SceneTriggler {

    public eFrequency getmFrequency() {
        return mFrequency;
    }

    public void setmFrequency(eFrequency mFrequency) {
        this.mFrequency = mFrequency;
    }

    public int getmStartHour() {
        return mStartHour;
    }

    public void setmStartHour(int mStartHour) {
        this.mStartHour = mStartHour;
    }

    public int getmStartMinutes() {
        return mStartMinutes;
    }

    public void setmStartMinutes(int mStartMinutes) {
        this.mStartMinutes = mStartMinutes;
    }

    public int getmEndHour() {
        return mEndHour;
    }

    public void setmEndHour(int mEndHour) {
        this.mEndHour = mEndHour;
    }

    public int getmEndMinutes() {
        return mEndMinutes;
    }

    public void setmEndMinutes(int mEndMinutes) {
        this.mEndMinutes = mEndMinutes;
    }

    private eFrequency mFrequency;
    private int mStartHour;
    private int mStartMinutes;
    private int mEndHour;
    private int mEndMinutes;

    public AlarmTrigger(SmartScene smartScene) {
        super(eTriggleMode.ALARM);
    }

    public AlarmTrigger(int startH, int startM, int endH, int endM, eFrequency frequency) {
        super(eTriggleMode.ALARM);
        this.mStartHour = startH;
        this.mStartMinutes = startM;
        this.mEndHour = endH;
        this.mEndMinutes = endM;
        this.mFrequency = frequency;
    }

    @Override
    public String getInfo() {
        return mFrequency.toString();
    }

    public static enum eFrequency {
        ONCE,
        EVERYDAY,
        WORKDAY,
        WEEKEND;
    }
}
