package com.xbb.triggler;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import com.gustavofao.jsonapi.Annotations.Excluded;
import com.gustavofao.jsonapi.Annotations.Type;
import com.xbb.provider.SmartScene;
import com.xbb.smartscene.R;

import java.util.Calendar;

/**
 * Created by HongYilin 16-11-22 下午10:11
 */
@Type("alarmtrigger")
public class AlarmTrigger extends SceneTrigger implements Parcelable {

    @Excluded
    final public static int ONCE = 0;
    @Excluded
    final public static int EVERYDAY = ONCE + 1;
    @Excluded
    final public static int WEEKDAY = EVERYDAY + 1;
    @Excluded
    final public static int WEEKEND = WEEKDAY + 1;

    private int frequency;
    private int startHour;
    private int startMinutes;
    private int endHour;
    private int endMinutes;

    public AlarmTrigger() {
    }

    public AlarmTrigger(SmartScene parent) {
        super(parent, SceneTrigger.ALARM);
        setOther(ONCE, 0, 0, 23, 59);
    }

    public int getFrequency() {
        return frequency;
    }

    public String getFrequency(Context context) {
        switch (frequency) {
            case ONCE:
                return context.getResources().getString(R.string.switch_once);
            case EVERYDAY:
                return context.getResources().getString(R.string.switch_every_day);
            case WEEKDAY:
                return context.getResources().getString(R.string.switch_week_day);
            case WEEKEND:
                return context.getResources().getString(R.string.switch_week_end);
            default:
                return context.getResources().getString(R.string.no_data);
        }
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinutes() {
        return startMinutes;
    }

    public void setStartMinutes(int startMinutes) {
        this.startMinutes = startMinutes;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMinutes() {
        return endMinutes;
    }

    public void setEndMinutes(int endMinutes) {
        this.endMinutes = endMinutes;
    }



    public void setOther(int frequency, int i, int i1, int i2, int i3) {
        setFrequency(frequency);
        setStartHour(i);
        setStartMinutes(i1);
        setEndHour(i2);
        setEndMinutes(i3);
    }

    @Override
    public String getTitle(Context context) {
        return context.getString(R.string.alarm_title);
    }

    @Override
    public Drawable getIcon(Context context) {
        return context.getResources().getDrawable(R.drawable.icon_pre_4);
    }

    @Override
    public String getInfo(Context context) {
        String s = getFrequency(context) + "  " + startHour + ":" + startMinutes + "---" + endHour + ":" + endMinutes;
        return s;
    }

    @Override
    public long getStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startHour * 3600 + startMinutes * 60);
        return calendar.getTimeInMillis();
    }

    @Override
    public long getEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(endHour * 3600 + endMinutes * 60);
        return calendar.getTimeInMillis();
    }

    // =============================================================================================
    // Parcelable Interface

    public AlarmTrigger(final Parcel in) {
        readFromParcel(in);
    }

    public final static Parcelable.Creator<AlarmTrigger> CREATOR = new Parcelable.Creator<AlarmTrigger>() {
        public AlarmTrigger createFromParcel(final Parcel in) {
            return new AlarmTrigger(in);
        }

        public AlarmTrigger[] newArray(final int size) {
            return new AlarmTrigger[size];
        }
    };

    private void readFromParcel(Parcel in) {
        //special
        frequency = in.readInt();
        startHour = in.readInt();
        startMinutes = in.readInt();
        endHour = in.readInt();
        endMinutes = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        //special
        parcel.writeInt(frequency);
        parcel.writeInt(startHour);
        parcel.writeInt(startMinutes);
        parcel.writeInt(endHour);
        parcel.writeInt(endMinutes);
    }
}
