package com.xbb.triggler;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import com.gustavofao.jsonapi.Annotations.Excluded;
import com.gustavofao.jsonapi.Annotations.Type;
import com.gustavofao.jsonapi.Models.Resource;
import com.xbb.provider.SmartScene;
import com.xbb.smartscene.R;

/**
 * Created by HongYilin 16-11-22 下午10:07
 */
@Type("scenetriggers")
public abstract class SceneTrigger extends Resource implements Parcelable {

    @Excluded
    final public static String SCENETRIGGER = "scene_trigger";
    @Excluded
    final public static String TRIGGERMODE = "trigger_mode";
    @Excluded
    final public static int OFF = 0;
    @Excluded
    final public static int ON = 1;
    @Excluded
    public SmartScene mParent;
    @Excluded
    final public static int ALARM = 0;
    @Excluded
    final public static int AP = ALARM + 1;

    public int mTrigglerMode;
    public int mSwitchTrigger;


    //abstract
    public abstract String getTitle(Context context);
    public abstract Drawable getIcon(Context context);
    public abstract String getInfo(Context context);

    public SceneTrigger() {}

    protected SceneTrigger(SmartScene smartScene, int mode) {
        this.mParent = smartScene;
        this.mTrigglerMode = mode;
        //setId(mParent.getId());
        mSwitchTrigger = OFF;
    }

    public int getSwitchTrigger() {
        return mSwitchTrigger;
    }

    public void setSwitchTrigger(int switchTrigger) {
        this.mSwitchTrigger = switchTrigger;
    }

    public void setSmartScene(SmartScene smartScene) {
        mParent = smartScene;
    }

    public static SceneTrigger create(SmartScene smartScene, int mode) {
        Object o;
        switch (mode) {
            case ALARM:
                o = new AlarmTrigger(smartScene);
                return (SceneTrigger) o;
            case AP:
                o = new APTrigger(smartScene);
                return (SceneTrigger) o;
            default:
                return null;
        }
    }

    // =============================================================================================
    // Parcelable Interface

    public SceneTrigger(final Parcel in) {
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        //common
        //setId(String.valueOf(in.readString()));
        mTrigglerMode = in.readInt();
        mSwitchTrigger = in.readInt();
        //special
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        //common
        //parcel.writeInt(Integer.valueOf(getId()));
        parcel.writeInt(mTrigglerMode);
        parcel.writeInt(mSwitchTrigger);
        //special
    }

}
