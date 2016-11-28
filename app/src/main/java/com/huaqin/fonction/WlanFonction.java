package com.huaqin.fonction;

import android.content.Context;

import com.huaqin.provider.SmartScene;

/**
 * Created by HongYilin 16-11-21 下午4:51
 */
public class WlanFonction extends SceneFonction {

    public WlanFonction(SmartScene smartScene) {
        super(eFonctionMode.WLAN);
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
