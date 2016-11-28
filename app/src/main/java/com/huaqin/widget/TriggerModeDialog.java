package com.huaqin.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.huaqin.fonction.SceneFonction;
import com.huaqin.provider.SceneDatabaseHelper;
import com.huaqin.provider.SmartScene;
import com.huaqin.smartscene.R;
import com.huaqin.triggler.APTrigger;
import com.huaqin.triggler.AlarmTrigger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HongYilin 16-11-25 下午4:01
 */
public class TriggerModeDialog extends DialogFragment {

    Context context;
    List<SmartScene> list;
    FakeRadioPreferenceAdapter f;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        initData();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.trigger_select_title);
        f = new FakeRadioPreferenceAdapter(context, list);
        builder.setSingleChoiceItems(f, -1, null);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "cancel", Toast.LENGTH_LONG).show();

            }
        });
        builder.setPositiveButton(R.string.next, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SmartScene s = f.getEnableTrigger();
                //do sth
                Toast.makeText(context, "next", Toast.LENGTH_LONG).show();

            }
        });

        return builder.create();
    }

    private void initData() {

        SmartScene alarm = new SmartScene("defaultS1", false);
        alarm.setSceneTriggler(new AlarmTrigger(alarm));

        SmartScene ap = new SmartScene("defaultS3", false);
        ap.setSceneTriggler(new APTrigger(ap));

        list = new ArrayList<SmartScene>();
        list.add(alarm);
        list.add(ap);
    }

    private class MyDialog extends Dialog {

        public MyDialog(Context context) {
            super(context);
        }

        public MyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onCreatePanelMenu(int featureId, Menu menu) {
            return super.onCreatePanelMenu(featureId, menu);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            super.onCreateContextMenu(menu, v, menuInfo);
        }

        @Override
        public View onCreatePanelView(int featureId) {
            return super.onCreatePanelView(featureId);
        }
    }
}

