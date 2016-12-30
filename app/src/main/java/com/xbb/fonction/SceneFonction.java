package com.xbb.fonction;

import android.content.Context;

import com.gustavofao.jsonapi.Annotations.Excluded;
import com.gustavofao.jsonapi.Annotations.Type;
import com.gustavofao.jsonapi.JSONApiConverter;
import com.gustavofao.jsonapi.Models.Resource;
import com.xbb.provider.SmartScene;

/**
 * Created by HongYilin 16-11-21 下午4:51
 */
@Type("scenefonctions")
public abstract class SceneFonction extends Resource {

    @Excluded
    final public static int AUDIO_PROFILE = 0;
    @Excluded
    final public static int AIRPLANE = AUDIO_PROFILE + 1;
    @Excluded
    final public static int BLUE_TOOTH = AIRPLANE + 1;
    @Excluded
    final public static int WLAN = BLUE_TOOTH + 1;
    @Excluded
    final public static int DATA_CONNECT = WLAN + 1;
    @Excluded
    final public static int BRIGHTNESS = DATA_CONNECT + 1;
    @Excluded
    final public static int GPS = BRIGHTNESS + 1;
    @Excluded
    final public static int APP = GPS + 1;


    public int mFonctionMode;

    protected SceneFonction() {
    }

    protected SceneFonction(int fonctionMode) {
        this.mFonctionMode = fonctionMode;
    }

    public abstract String getInfo(Context context);

    public abstract boolean getInfo();

    public abstract boolean active(Context context);

    public abstract boolean inactive(Context context);

    public static SceneFonction create(SmartScene smartScene, int fonctionMode) {
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
            case GPS:
                o = new GpsFonction(smartScene);
                break;
            case APP:
                o = new AppFonction(smartScene);
                break;
            default:
                o = null;
        }
        return (SceneFonction) o;
    }

}
