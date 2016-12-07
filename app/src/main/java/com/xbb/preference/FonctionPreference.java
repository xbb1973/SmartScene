package com.xbb.preference;

import android.content.Context;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import com.xbb.fonction.SceneFonction;

/**
 * Created by HongYilin 16-11-21 下午4:46
 */
public abstract class FonctionPreference {

    private SceneFonction mSceneFonction;

    private Preference mPreference;

    protected FonctionPreference(SceneFonction sceneFonction) {
        this.mSceneFonction = sceneFonction;
    }

    public static FonctionPreference create(SceneFonction sceneFonction) {
        Object o;
        switch (sceneFonction.mFonctionMode) {
            case SceneFonction.AUDIO_PROFILE:
                o = new AudioProfilePreference(sceneFonction);
                break;
            case SceneFonction.AIRPLANE:
                o = new AirplanePreference(sceneFonction);
                break;
            case SceneFonction.BLUE_TOOTH:
                o = new BlueToothPreference(sceneFonction);
                break;
            case SceneFonction.WLAN:
                o = new WlanPreference(sceneFonction);
                break;
            case SceneFonction.DATA_CONNECT:
                o = new DataConnectionPreference(sceneFonction);
                break;
            case SceneFonction.BRIGHTNESS:
                o = new BrightnessPreference(sceneFonction);
                break;
            case SceneFonction.GPS:
                o = new GpsPreference(sceneFonction);
                break;
            case SceneFonction.APP:
                o = new AppPreference(sceneFonction);
                break;
            default:
                o = null;
        }
        return (FonctionPreference) o;
    }
    /*
    protected Preference addIntoParent(PreferenceActivity preferenceActivity, int id, String key) {
        preferenceActivity.addPreferencesFromResource(id);
        this.mPreference = preferenceActivity.findPreference(key);
        return this.mPreference;
    }
    */
    public SceneFonction getSceneAction() {
        return this.mSceneFonction;
    }

    public void removeFromParent(PreferenceActivity preferenceActivity) {
        preferenceActivity.getPreferenceScreen().removePreference(this.mPreference);
    }

    public abstract Preference addIntoParent(PreferenceActivity preferenceActivity);
    public abstract void update(SceneFonction preferenceActivity);

    //public abstract void onPreferenceCreated();
    //public abstract void onPreferenceDestory();
    /*
    public FonctionPreference.onPreferenceAddFinishCallBack getPreferenceAddFinishListener() {
        return this.mAddFinishListener;
    }

    public FonctionPreference.onPrefereceLongClickListener getPreferenceLongClickListener() {
        return this.mListener;
    }
    public void setPreferenceAddFinishListener(FonctionPreference.onPreferenceAddFinishCallBack var1) {
        this.mAddFinishListener = var1;
    }

    public void setPreferenceLongClickListener(FonctionPreference.onPrefereceLongClickListener var1) {
        this.mListener = var1;
    }
    */
}
