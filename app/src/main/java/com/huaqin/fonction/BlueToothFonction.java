package com.huaqin.fonction;

import android.content.Context;

import com.huaqin.provider.SmartScene;

/**
 * Created by HongYilin 16-11-21 下午4:51
 */
public class BlueToothFonction extends SceneFonction {

    public BlueToothFonction(SmartScene smartScene) {
        super(eFonctionMode.BLUE_TOOTH);
    }

    @Override
    public String getInfo(Context context) {
        return null;
    }

    @Override
    public boolean getInfo() {
        return false;
    }
}
