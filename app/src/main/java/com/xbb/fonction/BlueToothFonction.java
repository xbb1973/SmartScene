package com.xbb.fonction;

import android.content.Context;

import com.gustavofao.jsonapi.Annotations.Type;
import com.xbb.provider.SmartScene;

/**
 * Created by HongYilin 16-11-21 下午4:51
 */
@Type("bluetooth")
public class BlueToothFonction extends SceneFonction {

    public BlueToothFonction() {}
    public BlueToothFonction(SmartScene smartScene) {
        super(SceneFonction.BLUE_TOOTH);
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
