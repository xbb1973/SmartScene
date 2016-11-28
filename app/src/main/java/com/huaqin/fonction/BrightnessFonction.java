package com.huaqin.fonction;

import android.content.Context;

import com.huaqin.provider.SmartScene;
import com.huaqin.smartscene.R;
/**
 * Created by HongYilin 16-11-21 下午4:51
 */
public class BrightnessFonction extends SceneFonction {

    public static int BrightnessLeve[] = new int[]{30, 140, 255};

    private eBrightnessLevel mBrightnessLevel = eBrightnessLevel.BRIGHTNESS_DARK;

    public BrightnessFonction(SmartScene smartScene) {
        super(eFonctionMode.BRIGHTNESS);
    }

    @Override
    public String getInfo(Context context) {
        switch (mBrightnessLevel) {
            case BRIGHTNESS_DARK:
                return context.getApplicationContext().getString(R.string.brightness_darker);
            case BRIGHTNESS_NORMAL:
                return context.getApplicationContext().getString(R.string.brightness_middle);
            case BRIGHTNESS_BRIGHT:
                return context.getApplicationContext().getString(R.string.brightness_brightest);
            default:
                return context.getApplicationContext().getString(R.string.brightness_darker);
        }
    }

    @Override
    public boolean getInfo() {
        return false;
    }

    public static enum eBrightnessLevel {
        /*30 140 255*/
        BRIGHTNESS_DARK,
        BRIGHTNESS_NORMAL,
        BRIGHTNESS_BRIGHT;

    }
}
