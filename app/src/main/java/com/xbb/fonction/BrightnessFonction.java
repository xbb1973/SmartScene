package com.xbb.fonction;

import android.content.Context;
import android.provider.Settings;

import com.gustavofao.jsonapi.Annotations.Excluded;
import com.gustavofao.jsonapi.Annotations.Type;
import com.xbb.provider.SmartScene;
import com.xbb.smartscene.R;
/**
 * Created by HongYilin 16-11-21 下午4:51
 */
@Type("brightness")
public class BrightnessFonction extends SceneFonction {

    @Excluded
    final public static int BRIGHTNESS_DARK = 30;
    @Excluded
    final public static int BRIGHTNESS_NORMAL = 140;
    @Excluded
    final public static int BRIGHTNESS_BRIGHT = 255;
    @Excluded
    private int mOriginalBrightLevel;

    private int mBrightnessLevel;

    public BrightnessFonction() {}
    public BrightnessFonction(SmartScene smartScene) {
        super(SceneFonction.BRIGHTNESS);
        mBrightnessLevel = BRIGHTNESS_DARK;
    }

    public int getmBrightnessLevel() {
        return mBrightnessLevel;
    }

    public void setmBrightnessLevel(int mBrightnessLevel) {
        this.mBrightnessLevel = mBrightnessLevel;
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

    @Override
    public boolean active(Context context) {
        mOriginalBrightLevel = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, mBrightnessLevel);
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, mBrightnessLevel);
        return true;
    }

    @Override
    public boolean inactive(Context context) {
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, mOriginalBrightLevel);
        return true;
    }

}
