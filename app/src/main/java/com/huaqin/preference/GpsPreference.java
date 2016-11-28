package com.huaqin.preference;

import android.preference.Preference;
import android.preference.PreferenceActivity;

import com.huaqin.fonction.SceneFonction;

/**
 * Created by HongYilin 16-11-21 下午4:49
 */
public class GpsPreference extends FonctionPreference {

    public GpsPreference(SceneFonction sceneFonction) {
        super(sceneFonction);
    }

    @Override
    public Preference addIntoParent(PreferenceActivity preferenceActivity) {
        return null;
    }

    @Override
    public void update(SceneFonction preferenceActivity) {

    }
}
