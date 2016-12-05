package com.xbb.triggler;

import android.os.Parcel;
import android.os.Parcelable;

import com.gustavofao.jsonapi.Annotations.Type;
import com.xbb.provider.SmartScene;

/**
 * Created by HongYilin 16-11-22 下午10:11
 */
@Type("alarmtrigger")
public class AlarmTrigger extends SceneTrigger implements Parcelable {

    public eFrequency getFrequency() {
        return frequency;
    }

    public void setFrequency(eFrequency frequency) {
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

    private eFrequency frequency;
    private int startHour;
    private int startMinutes;
    private int endHour;
    private int endMinutes;

    public AlarmTrigger() {}

    public AlarmTrigger(SmartScene smartScene) {
        super(eTriggleMode.ALARM);
    }

    public AlarmTrigger(int startH, int startM, int endH, int endM, eFrequency frequency) {
        super(eTriggleMode.ALARM);
        this.startHour = startH;
        this.startMinutes = startM;
        this.endHour = endH;
        this.endMinutes = endM;
        this.frequency = frequency;
    }

    @Override
    public String getInfo() {
        String s = startHour + ":" + startMinutes + "---" + endHour + ":" + endMinutes;
        return s;
    }

    // =============================================================================================
    // Parcelable Interface

    public AlarmTrigger(final Parcel in){
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
        frequency = eFrequency.values()[in.readInt()];
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
        parcel.writeInt(frequency.ordinal());
        parcel.writeInt(startHour);
        parcel.writeInt(startMinutes);
        parcel.writeInt(endHour);
        parcel.writeInt(endMinutes);
    }

    public static enum eFrequency {
        ONCE,
        EVERYDAY,
        WEEKDAY,
        WEEKEND;
        static {
            AlarmTrigger.eFrequency[] frequency =
                    new AlarmTrigger.eFrequency[]{ONCE, EVERYDAY, WEEKDAY, WEEKEND};
        }
    }
}
