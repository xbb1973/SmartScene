package com.xbb.fonction;

import android.content.Context;

import com.gustavofao.jsonapi.Annotations.Type;
import com.xbb.provider.SmartScene;

/**
 * Created by HongYilin 16-11-21 下午4:51
 */
@Type("audioprofile")
public class AudioProfileFonction extends SceneFonction {

    public AudioProfileFonction() {}

    public AudioProfileFonction(SmartScene smartScene) {
        super(SceneFonction.AUDIO_PROFILE);
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
