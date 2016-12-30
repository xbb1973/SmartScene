package com.xbb.fonction;

import android.content.Context;

import com.gustavofao.jsonapi.Annotations.Type;
import com.xbb.provider.SmartScene;

/**
 * Created by HongYilin 16-11-21 下午4:51
 */
@Type("dataconnection")
public class DataConnectionFonction extends SceneFonction {

    public DataConnectionFonction() {}

    public DataConnectionFonction(SmartScene smartScene) {
        super(SceneFonction.DATA_CONNECT);
    }

    @Override
    public String getInfo(Context context) {
        return null;
    }

    @Override
    public boolean getInfo() {
        return false;
    }

    @Override
    public boolean active(Context context) {
        return false;
    }

    @Override
    public boolean inactive(Context context) {
        return false;
    }
}
