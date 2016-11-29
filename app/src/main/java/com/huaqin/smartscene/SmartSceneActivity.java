package com.huaqin.smartscene;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

    protected ListView mListView;
    protected TextView mEmpty;
    protected Button mAdd;
    private Map<Integer, List<SmartScene>> mSmartSceneMap;
    protected SceneDatabaseHelper mSceneDatabaseHelper;
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    protected void initData() {
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

    protected void initView() {
        setContentView(R.layout.activity_smart_scene);
        mListView = (ListView) findViewById(R.id.scene_list);
        mListView.setAdapter(new FakeSwitchPreferenceAdapter(mContext, mSmartSceneMap));
        //mListView.addFooterView(mAddView);
        mEmpty = (TextView) findViewById(R.id.scene_empty);
        /*
        mAdd = (Button) findViewById(R.id.button_add);
        mAdd.setText(mContext.getString(R.string.new_scene));
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.showTimeEditDialog(getFragmentManager());
            }
        });
        */
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
