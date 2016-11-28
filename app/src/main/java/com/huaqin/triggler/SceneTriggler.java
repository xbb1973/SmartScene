package com.huaqin.triggler;

import com.huaqin.provider.SmartScene;

/**
 * Created by HongYilin 16-11-22 下午10:07
 */
public abstract class SceneTriggler {

    public eTriggleMode mTrigglerMode;

    protected SceneTriggler(eTriggleMode mode) {
        this.mTrigglerMode = mode;
    }

    public abstract String getInfo();

    public static SceneTriggler create(SmartScene smartScene, eTriggleMode mode) {

        Object o;

        switch (mode) {
            case ALARM:
                o = new AlarmTrigger(smartScene);
                return (SceneTriggler) o;
            case AP:
                o = new APTrigger(smartScene);
                return (SceneTriggler) o;
            default:
                return null;
        }
    }

    public static enum eTriggleMode {
        ALARM,
        AP;
    }
}
