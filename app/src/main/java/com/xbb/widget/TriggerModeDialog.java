package com.xbb.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.xbb.fonction.SceneFonction;
import com.xbb.provider.SmartScene;
import com.xbb.smartscene.R;
import com.xbb.smartscene.SceneSettingActivity;
import com.xbb.triggler.APTrigger;
import com.xbb.triggler.AlarmTrigger;
import com.xbb.triggler.SceneTrigger;
import com.xbb.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HongYilin 16-11-25 下午4:01
 */
public class TriggerModeDialog extends DialogFragment {

    Context context;
    List<SmartScene> smartSceneList;
    Callback callback;

    List<SceneFonction> sceneFonctionList;

    FonctionListAdapter fonctionListAdapter;
    FakeRadioPreferenceAdapter fakeRadioPreferenceAdapter;

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public List<SceneFonction> getSceneFonctionList() {
        return sceneFonctionList;
    }

    public void setSceneFonctionList(List<SceneFonction> sceneFonctionList) {
        this.sceneFonctionList = sceneFonctionList;
    }

    public List<SmartScene> getSmartSceneList() {
        return smartSceneList;
    }

    public void setSmartSceneList(List<SmartScene> smartSceneList) {
        this.smartSceneList = smartSceneList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);


        if (sceneFonctionList != null && sceneFonctionList.size() > 0) {
            builder.setTitle(R.string.scene_feature);
            fonctionListAdapter = new FonctionListAdapter(context, sceneFonctionList, 1);
            builder.setSingleChoiceItems(fonctionListAdapter, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    callback.onDialogItemClick(i);
                }
            });

            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //do nothing
                    Toast.makeText(context, "back", Toast.LENGTH_LONG).show();
                }
            });

        } else {
            builder.setTitle(R.string.trigger_select_title);
            fakeRadioPreferenceAdapter = new FakeRadioPreferenceAdapter(context, smartSceneList);
            builder.setSingleChoiceItems(fakeRadioPreferenceAdapter, -1, null);
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //do nothing
                }
            });
            builder.setPositiveButton(R.string.next, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    //do sth
                    Intent intent = new Intent(context, SceneSettingActivity.class);
                    intent.putExtra(SceneTrigger.TRIGGERMODE, fakeRadioPreferenceAdapter.getMode());
                    startActivityForResult(intent, 1);
                    Toast.makeText(context, "next", Toast.LENGTH_LONG).show();

                }
            });
        }
        return builder.create();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initData() {
        context = getActivity();
        if (sceneFonctionList == null)
            sceneFonctionList = new ArrayList<SceneFonction>();

        SmartScene alarm = new SmartScene(String.valueOf(SceneTrigger.ALARM), false);
        alarm.setSceneTrigger(SceneTrigger.create(alarm, SceneTrigger.ALARM));

        SmartScene ap = new SmartScene(String.valueOf(SceneTrigger.AP), false);
        ap.setSceneTrigger(SceneTrigger.create(alarm, SceneTrigger.AP));
        smartSceneList = new ArrayList<SmartScene>();
        smartSceneList.add(alarm);
        smartSceneList.add(ap);

    }

    interface Callback {
        void onDialogItemClick(int position);
    }
}

