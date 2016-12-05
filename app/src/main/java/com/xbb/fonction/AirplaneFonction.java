package com.xbb.fonction;

import android.content.Context;

import com.xbb.provider.SmartScene;

/**
 * Created by HongYilin 16-11-21 下午4:51
 */
public class AirplaneFonction extends SceneFonction {

    public AirplaneFonction(SmartScene smartScene) {
        super(eFonctionMode.AIRPLANE);
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
