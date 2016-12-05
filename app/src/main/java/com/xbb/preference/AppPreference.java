package com.xbb.preference;

import android.preference.Preference;
import android.preference.PreferenceActivity;

import com.xbb.fonction.SceneFonction;

/**
 * Created by HongYilin 16-11-21 下午4:49
 */
public class AppPreference extends FonctionPreference {

    public AppPreference(SceneFonction sceneFonction) {
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
