package com.xbb.triggler;

import android.content.Context;

import com.gustavofao.jsonapi.Annotations.Excluded;
import com.gustavofao.jsonapi.Annotations.Type;
import com.gustavofao.jsonapi.Models.Resource;
import com.xbb.provider.SmartScene;

/**
 * Created by HongYilin 16-11-22 下午10:07
 */
@Type("scenetriggers")
public abstract class SceneTrigger extends Resource {

    public static Context mContext;
    @Excluded
    private static SmartScene mLastParent;

    public eTriggleMode mTrigglerMode;

    public SceneTrigger() {}

    protected SceneTrigger(eTriggleMode mode) {
        this.mTrigglerMode = mode; setId(mLastParent.getId());
    }

    public abstract String getInfo();

    public static SceneTrigger create(SmartScene smartScene, eTriggleMode mode, Context context) {
        mLastParent = smartScene;
        mContext = context;

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

    public static enum eTriggleMode {
        ALARM,
        AP;
    }
}
