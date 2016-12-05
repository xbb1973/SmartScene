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
import com.xbb.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HongYilin 16-11-25 下午4:01
 */
public class TriggerModeDialog extends DialogFragment {

    Context context;
    List<SmartScene> smartSceneList;

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

    List<SceneFonction> sceneFonctionList;

    FonctionListAdapter fonctionListAdapter;
    FakeRadioPreferenceAdapter fakeRadioPreferenceAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);


        if (sceneFonctionList.size() > 0) {
            LogUtils.e(">0");
            builder.setTitle(R.string.scene_feature);
            fonctionListAdapter = new FonctionListAdapter(context, sceneFonctionList, 1);
            builder.setSingleChoiceItems(fonctionListAdapter, -1, null);
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //do nothing
                    Toast.makeText(context, "back", Toast.LENGTH_LONG).show();
                }
            });

        } else {
            LogUtils.e("<0");
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
                    SmartScene s = fakeRadioPreferenceAdapter.getEnableTrigger();
                    //do sth
                    Intent intent = new Intent(context, SceneSettingActivity.class);
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

        SmartScene alarm = new SmartScene("defaultS1", false);
        alarm.setSceneTrigger(new AlarmTrigger(alarm));
        SmartScene ap = new SmartScene("defaultS3", false);
        ap.setSceneTrigger(new APTrigger(ap));
        smartSceneList = new ArrayList<SmartScene>();
        smartSceneList.add(alarm);
        smartSceneList.add(ap);

    }

}
