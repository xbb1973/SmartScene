package com.xbb.fonction;

import android.content.Context;

import com.gustavofao.jsonapi.Annotations.Type;
import com.xbb.provider.SmartScene;

/**
 * Created by HongYilin 16-11-21 下午4:51
 */
@Type("app")
public class AppFonction extends SceneFonction {

    public AppFonction() {}
    public AppFonction(SmartScene smartScene) {
        super(SceneFonction.APP);
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
