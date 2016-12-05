package com.xbb.fonction;

import android.content.Context;

import com.gustavofao.jsonapi.Annotations.Type;
import com.gustavofao.jsonapi.JSONApiConverter;
import com.gustavofao.jsonapi.Models.Resource;
import com.xbb.provider.SmartScene;

/**
 * Created by HongYilin 16-11-21 下午4:51
 */
@Type("scenefonctions")
public abstract class SceneFonction extends Resource {

    public eFonctionMode mFonctionMode;

    protected SceneFonction() {}

    protected SceneFonction(eFonctionMode fonctionMode) {
        this.mFonctionMode = fonctionMode;
    }

    public abstract String getInfo(Context context);

    public abstract boolean getInfo();

    public static SceneFonction create(SmartScene smartScene, eFonctionMode fonctionMode) {
        Object o;
        switch (fonctionMode) {
            case AUDIO_PROFILE:
                o = new AudioProfileFonction(smartScene);
                break;
            case AIRPLANE:
                o = new AirplaneFonction(smartScene);
                break;
            case BLUE_TOOTH:
                o = new BlueToothFonction(smartScene);
                break;
            case WLAN:
                o = new WlanFonction(smartScene);
                break;
            case DATA_CONNECT:
                o = new DataConnectionFonction(smartScene);
                break;
            case BRIGHTNESS:
                o = new BrightnessFonction(smartScene);
                break;
            case APP:
                o = new AppFonction(smartScene);
                break;
            case GPS:
                o = new GpsFonction(smartScene);
                break;
            default:
                o = null;
        }
        return (SceneFonction)o;
    }

    public enum eFonctionMode {
        AUDIO_PROFILE,
        AIRPLANE,
        BLUE_TOOTH,
        WLAN,
        DATA_CONNECT,
        BRIGHTNESS,
        APP,
        GPS;
    }
}
