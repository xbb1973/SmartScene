package com.huaqin.smartscene;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.huaqin.fonction.SceneFonction;
import com.huaqin.provider.SceneDatabaseHelper;
import com.huaqin.provider.SmartScene;
import com.huaqin.triggler.APTrigger;
import com.huaqin.triggler.AlarmTrigger;
import com.huaqin.util.Utils;
import com.huaqin.widget.FakeSwitchPreferenceAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HongYiLin
 * 16-11-15 下午3:27
 */

public class SmartSceneActivity extends Activity {

    ListView mListView;
    TextView mEmpty;
    View mAddView;
    Map<Integer, List<SmartScene>> mSmartSceneMap;
    SceneDatabaseHelper mSceneDatabaseHelper;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
        /*
        addPreferencesFromResource(R.xml.scene_settings);
        PreferenceScreen pre = getPreferenceScreen();
        PreferenceCategory a = (PreferenceCategory) findPreference("bind_ap_key");
        PreferenceCategory b = (PreferenceCategory) findPreference("scene_list_key");


        addPreferencesFromResource(R.xml.brightness_prefs);
        Preference preference = findPreference("brightness_prefs_key");
        //b.addPreference(preference);
        preference.setSummary("hhhhhh");

        WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
        mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;// 系统提示window
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;// 焦点
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;//窗口的宽和高
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //mParams.screenBrightness = ;
        mParams.x = getWindow().getAttributes().x / 2;//窗口位置的偏移量
        mParams.y = getWindow().getAttributes().y;
        mParams.alpha = 0.9f;//窗口的透明度
        View view = View.inflate(getApplicationContext(), R.layout.activity_smart_scene, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        addContentView(view, mParams);
        */
    }

    private void initData() {
        SmartScene defaltS1 = new SmartScene("defaultS1", true);
        AlarmTrigger sceneTrigglerAlarm = new AlarmTrigger(12, 0, 14, 0, AlarmTrigger.eFrequency.ONCE);
        defaltS1.setSceneTriggler(sceneTrigglerAlarm);
        List<SceneFonction> sceneFonctionList = new ArrayList<SceneFonction>();
        sceneFonctionList.add(SceneFonction.create(defaltS1, SceneFonction.eFonctionMode.BRIGHTNESS));
        defaltS1.setSceneFonctionList(sceneFonctionList);

        SmartScene defaltS2 = new SmartScene("defaultS2", false);
        defaltS2.setSceneTriggler(new AlarmTrigger(14, 0, 16, 0, AlarmTrigger.eFrequency.EVERYDAY));
        defaltS2.setSceneFonctionList(sceneFonctionList);

        SmartScene defaltS3 = new SmartScene("defaultS3", false);
        defaltS3.setSceneTriggler(new APTrigger(defaltS3));
        defaltS3.setSceneFonctionList(sceneFonctionList);


        mContext = getApplicationContext();
        mSceneDatabaseHelper = new SceneDatabaseHelper(mContext);
        mSmartSceneMap = new HashMap<Integer, List<SmartScene>>();

        List<SmartScene> alarmList = new ArrayList<SmartScene>();
        List<SmartScene> apList = new ArrayList<SmartScene>();
        //mSmartSceneList = new ArrayList<SmartScene>();

        alarmList.add(defaltS1);
        alarmList.add(defaltS2);
        apList.add(defaltS3);
        mSmartSceneMap.put(defaltS1.getSceneTriggler().mTrigglerMode.ordinal(), alarmList);
        mSmartSceneMap.put(defaltS3.getSceneTriggler().mTrigglerMode.ordinal(), apList);

    }

    private void initView() {
        setContentView(R.layout.activity_smart_scene);
        mListView = (ListView) findViewById(R.id.scene_list);
        mListView.setAdapter(new FakeSwitchPreferenceAdapter(mContext, mSmartSceneMap));
        //mListView.addFooterView(mAddView);
        mEmpty = (TextView) findViewById(R.id.scene_empty);
        if (mSmartSceneMap.size() > 0) {
            //mHeadView.setVisibility(View.VISIBLE);
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

}
