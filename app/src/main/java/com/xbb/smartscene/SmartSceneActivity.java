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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gustavofao.jsonapi.JSONApiConverter;
import com.gustavofao.jsonapi.Models.JSONApiObject;
import com.xbb.fonction.SceneFonction;
import com.xbb.provider.Data;
import com.xbb.provider.SceneDatabaseHelper;
import com.xbb.provider.SmartScene;
import com.xbb.triggler.APTrigger;
import com.xbb.triggler.AlarmTrigger;
import com.xbb.triggler.SceneTrigger;
import com.xbb.util.LogUtils;
import com.xbb.util.Utils;
import com.xbb.widget.FakeSwitchPreferenceAdapter;

import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HongYiLin
 * 16-11-15 下午3:27
 */

public class SmartSceneActivity extends Activity implements FakeSwitchPreferenceAdapter.CallBack {

    public final static JSONApiConverter mApi = new JSONApiConverter(SmartScene.class, SceneTrigger.class, AlarmTrigger.class, APTrigger.class);;

    protected ListView mListView;
    protected TextView mEmpty;
    protected Button mAdd;
    protected Context mContext;
    private SparseArray<List<SmartScene>> mSmartSceneSparseArray;
    private SceneDatabaseHelper mSceneDatabaseHelper;
    public ContentResolver mContentResolver;
    public FakeSwitchPreferenceAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    protected void initData() {

        mSmartSceneSparseArray = new SparseArray<List<SmartScene>>();
        mContentResolver = getContentResolver();
        mContext = getApplicationContext();

        List<SmartScene> smartSceneList = SmartScene.getSmartScenes(mContentResolver, mContext, null, null);
        List<SmartScene> alarm = new ArrayList<SmartScene>();
        List<SmartScene> ap = new ArrayList<SmartScene>();

        for (int i = 0; i < smartSceneList.size(); i++) {
            String json = mApi.toJson(smartSceneList.get(i));
            LogUtils.e("" + json);

            JSONApiObject jsonApiObject = mApi.fromJson(json);
            SmartScene smartScene = new SmartScene();
            if (jsonApiObject.getData().size() > 0) {
                smartScene = (SmartScene) jsonApiObject.getData(0);
                LogUtils.e("" + mApi.toJson(smartScene));
            } else
                LogUtils.e("fail");

            JSONApiObject jsonApiObject1 = mApi.fromJson(mApi.toJson(smartScene));

            if (jsonApiObject1.getData().size() > 0) {
                smartScene = (SmartScene) jsonApiObject1.getData(0);
                LogUtils.e("" + mApi.toJson(smartScene));
            } else
                LogUtils.e("fail");

            if (smartSceneList.get(i).getSceneTrigger().mTrigglerMode == SceneTrigger.eTriggleMode.ALARM) {
                alarm.add(smartSceneList.get(i));
            } else if (smartSceneList.get(i).getSceneTrigger().mTrigglerMode == SceneTrigger.eTriggleMode.AP) {
                ap.add(smartSceneList.get(i));
            }
        }

        if (alarm.size() > 0)
            mSmartSceneSparseArray.put(SceneTrigger.eTriggleMode.ALARM.ordinal(), alarm);
        if (ap.size() > 0)
            mSmartSceneSparseArray.put(SceneTrigger.eTriggleMode.AP.ordinal(), ap);
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
        startActivityForResult(intent, 1);
    }

    @Override
    public void onSwitchClick(int postion) {
        LogUtils.e("callback222");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
