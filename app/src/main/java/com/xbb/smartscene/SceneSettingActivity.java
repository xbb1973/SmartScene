package com.xbb.smartscene;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xbb.fonction.SceneFonction;
import com.xbb.provider.SmartScene;
import com.xbb.triggler.AlarmTrigger;
import com.xbb.util.Utils;
import com.xbb.widget.FonctionListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HongYilin 16-11-28 下午3:40
 */
public class SceneSettingActivity extends SmartSceneActivity {

    private SmartScene mSmartScene;
    private List<SceneFonction> mSceneFonction;

    private View mEditView;
    private View mTriggerHeadView;
    private TextView mHeadText;
    private View mTriggerCommonView;
    private ImageView mTriggerIcon;
    private TextView mTriggerTitle;
    private TextView mTriggerSummary;
    private ImageView mIcon;
    private EditText mLabel;

    @Override
    protected void initData() {
        mContext = getApplicationContext();
        mSmartScene = getIntent().getParcelableExtra(SmartScene.SMARTSCENE);

//        mSmartScene = new SmartScene("hh", false);
//        AlarmTrigger alarmTrigger = new AlarmTrigger(12, 0, 14, 0, AlarmTrigger.eFrequency.EVERYDAY);
//        mSmartScene.setSceneTrigger(alarmTrigger);
//        mSceneFonction = new ArrayList<SceneFonction>();
//        mSceneFonction.add(SceneFonction.create(mSmartScene, SceneFonction.eFonctionMode.BRIGHTNESS));
//        mSmartScene.setSceneFonctionList(mSceneFonction);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_setting_scene);
        if (true) {
            setTitle(mContext.getResources().getString(R.string.new_scene));
        } else {
            setTitle(mContext.getResources().getString(R.string.scene_edit));
        }

        //Edit
        mEditView = findViewById(R.id.edit_item);
        mIcon = (ImageView) mEditView.findViewById(R.id.edit_icon);
        mIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_pre_1));
        mLabel = (EditText) mEditView.findViewById(R.id.edittext_name);
        mLabel.setText(mSmartScene.getLabel());

        //Trigger
        mTriggerHeadView = findViewById(R.id.head_item);

        mHeadText = (TextView) findViewById(R.id.text_head);
        mHeadText.setText(getString(R.string.scene_bind));
        mHeadText.setVisibility(View.VISIBLE);
        //
        mTriggerCommonView = findViewById(R.id.common_item);
        mTriggerCommonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "onClick", Toast.LENGTH_LONG).show();
                startActivityForResult(new Intent(mContext, AlarmSettingActivity.class), 1);
            }
        });
        mTriggerIcon = (ImageView) findViewById(R.id.common_item_icon);
        mTriggerIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_pre_4));
        mTriggerTitle = (TextView) findViewById(android.R.id.title);
        mTriggerTitle.setText(getString(R.string.alarm_title));
        mTriggerSummary = (TextView) findViewById(android.R.id.summary);
        mTriggerSummary.setText(mSmartScene.getSceneTrigger().getInfo());
        mTriggerCommonView.setVisibility(View.VISIBLE);

        //FonctionList
        mListView = (ListView) findViewById(R.id.setting_list);
        //
        mAdd = (Button) findViewById(R.id.button_add);
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.showTimeEditDialog(getFragmentManager(), mSceneFonction);
            }
        });
        //
        mEmpty = (TextView) findViewById(R.id.setting_empty);
        if (mSceneFonction.size() > 0) {
            FonctionListAdapter fonctionListAdapter = new FonctionListAdapter(mContext, mSceneFonction);
            mListView.setAdapter(fonctionListAdapter);
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
                Toast.makeText(this.mContext, "add_button", Toast.LENGTH_LONG).show();
                Utils.showTimeEditDialog(getFragmentManager());
                return true;
            case R.id.menu_help:
                Toast.makeText(this.mContext, "help ~~", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
