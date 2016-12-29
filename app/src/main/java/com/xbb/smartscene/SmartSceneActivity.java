package com.xbb.smartscene;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gustavofao.jsonapi.JSONApiConverter;
import com.xbb.fonction.AirplaneFonction;
import com.xbb.fonction.AppFonction;
import com.xbb.fonction.AudioProfileFonction;
import com.xbb.fonction.BlueToothFonction;
import com.xbb.fonction.BrightnessFonction;
import com.xbb.fonction.DataConnectionFonction;
import com.xbb.fonction.GpsFonction;
import com.xbb.fonction.SceneFonction;
import com.xbb.fonction.WlanFonction;
import com.xbb.provider.SceneDatabaseHelper;
import com.xbb.provider.SmartScene;
import com.xbb.triggler.APTrigger;
import com.xbb.triggler.AlarmTrigger;
import com.xbb.triggler.SceneTrigger;
import com.xbb.util.LogUtils;
import com.xbb.util.Utils;
import com.xbb.widget.FakeSwitchPreferenceAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HongYiLin
 * 16-11-15 下午3:27
 */

public class SmartSceneActivity extends Activity implements FakeSwitchPreferenceAdapter.CallBack {

    public final static JSONApiConverter mApi =
            new JSONApiConverter(SmartScene.class,
                    SceneTrigger.class, AlarmTrigger.class, APTrigger.class,
                    SceneFonction.class, AudioProfileFonction.class, AirplaneFonction.class, BlueToothFonction.class, WlanFonction.class,
                    DataConnectionFonction.class, BrightnessFonction.class, GpsFonction.class, AppFonction.class);

    private ListView mListView;
    private TextView mEmpty;
    private Context mContext;
    private SparseArray<List<SmartScene>> mSmartSceneSparseArray;
    private SceneDatabaseHelper mSceneDatabaseHelper;
    private ContentResolver mContentResolver;
    private FakeSwitchPreferenceAdapter mListAdapter;

    public static int REQUEST_CODE_ = 0;
    public static int RESULT_CODE_ALARM = 1;
    public static int RESULT_CODE_AP = RESULT_CODE_ALARM + 1;

    List<SmartScene> smartSceneList;
    List<SmartScene> alarm;
    List<SmartScene> ap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
        initListener();
    }

    protected void initData() {

        mSmartSceneSparseArray = new SparseArray<List<SmartScene>>();
        mContentResolver = getContentResolver();
        mContext = getApplicationContext();


        //analysis database , translate data into list<smartsce>
        List<SmartScene> smartSceneList = SmartScene.getSmartScenes(mContentResolver, null);
        List<SmartScene> alarm = new ArrayList<SmartScene>();
        List<SmartScene> ap = new ArrayList<SmartScene>();

        for (int i = 0; i < smartSceneList.size(); i++) {
            SmartScene smartScene = smartSceneList.get(i);
            if (smartScene.getSceneTrigger().mTrigglerMode == (SceneTrigger.ALARM)) {
                alarm.add(smartSceneList.get(i));
            } else if (smartScene.getSceneTrigger().mTrigglerMode == (SceneTrigger.AP)) {
                ap.add(smartSceneList.get(i));
            }
        }

        if (alarm.size() > 0)
            mSmartSceneSparseArray.put(SceneTrigger.ALARM, alarm);
        if (ap.size() > 0)
            mSmartSceneSparseArray.put(SceneTrigger.AP, ap);
    }


    protected void initView() {
        setContentView(R.layout.activity_smart_scene);
        mListView = (ListView) findViewById(R.id.scene_list);
        mListAdapter = new FakeSwitchPreferenceAdapter(mContext, mSmartSceneSparseArray);
        mListAdapter.setCallBack(this);
        mListView.setAdapter(mListAdapter);
        mEmpty = (TextView) findViewById(R.id.scene_empty);
        if (mSmartSceneSparseArray.size() > 0) {
            mEmpty.setVisibility(View.GONE);
        }
    }

    protected void initListener() {
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_smart_scene, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.menu_new:
                Toast.makeText(mContext, "add_button", Toast.LENGTH_LONG).show();
                Utils.showTimeEditDialog(getFragmentManager());
                return true;
            case R.id.menu_help:
                Toast.makeText(mContext, "help ~~", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(int position) {
        LogUtils.e("callback111");
        Intent intent = new Intent(mContext, SceneSettingActivity.class);
        intent.putExtra(SmartScene.SMARTSCENE, mListAdapter.getItem(position));
        startActivityForResult(intent, REQUEST_CODE_);
    }

    @Override
    public void onSwitchClick(int postion) {
        LogUtils.e("callback222");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        LogUtils.e("here----------------------------" + requestCode + resultCode);
        if (resultCode == RESULT_OK || requestCode == RESULT_OK) {
            LogUtils.e("here0-----------------------");
            if (data.getIntExtra(SmartScene.TRIGGERMODE, -1) == SceneTrigger.ALARM) {
                mSmartSceneSparseArray.remove(SceneTrigger.ALARM);
                alarm.add(SmartScene.getSmartScene(getContentResolver(), data.getStringExtra(SmartScene.ID)));
                mSmartSceneSparseArray.put(SceneTrigger.ALARM, alarm);
                LogUtils.e("here1");
            } else if (data.getIntExtra(SmartScene.TRIGGERMODE, -1) == SceneTrigger.AP) {
                mSmartSceneSparseArray.remove(SceneTrigger.AP);
                ap.add(SmartScene.getSmartScene(getContentResolver(), data.getStringExtra(SmartScene.ID)));
                mSmartSceneSparseArray.put(SceneTrigger.AP, ap);
                LogUtils.e("here2");
            }

            mListAdapter = new FakeSwitchPreferenceAdapter(mContext, mSmartSceneSparseArray);
            mListView.setAdapter(mListAdapter);
        }
    }
}
