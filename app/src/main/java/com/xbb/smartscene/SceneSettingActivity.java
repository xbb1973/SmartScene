package com.xbb.smartscene;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gustavofao.jsonapi.Annotations.Excluded;
import com.xbb.fonction.SceneFonction;
import com.xbb.provider.SmartScene;
import com.xbb.triggler.AlarmTrigger;
import com.xbb.triggler.SceneTrigger;
import com.xbb.util.LogUtils;
import com.xbb.util.Utils;
import com.xbb.widget.FonctionListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HongYilin 16-11-28 下午3:40
 */
public class SceneSettingActivity extends Activity {

    private Context mContext;
    private int mMode;
    private SmartScene mSmartScene;
    private List<SceneFonction> mSceneFonctionList;

    private ListView mListView;
    private TextView mEmpty;
    private Button mAdd;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initListener();
    }

    private void initData() {
        mContext = getApplicationContext();
        mMode = getIntent().getIntExtra(SceneTrigger.TRIGGERMODE, -1);
        LogUtils.e("" + mMode);
        if (mMode == -1) {
            setTitle(getResources().getString(R.string.scene_edit));
            mSmartScene = getIntent().getParcelableExtra(SmartScene.SMARTSCENE);
        } else {
            setTitle(getResources().getString(R.string.new_scene));
            if (mMode == 0) {
                mSmartScene = new SmartScene("", R.drawable.icon_pre_4,false);
                mSmartScene.setSceneTrigger(SceneTrigger.create(mSmartScene, SceneTrigger.ALARM));
                ((AlarmTrigger) mSmartScene.getSceneTrigger()).setOther(AlarmTrigger.ONCE, 12, 0, 14, 0);
            } else if (mMode == 1) {
                mSmartScene = new SmartScene("", R.drawable.icon_pre_3,false);
                mSmartScene.setSceneTrigger(SceneTrigger.create(mSmartScene, SceneTrigger.AP));
            }
        }
        mSceneFonctionList = mSmartScene.getSceneFonctionList();
        fillView();
    }

    private void fillView() {

        mTriggerCommonView.setVisibility(View.VISIBLE);
        mIcon.setImageDrawable(getResources().getDrawable(mSmartScene.getIcon()));
        mLabel.setText(mSmartScene.getLabel());
        mTriggerIcon.setImageDrawable(mSmartScene.getSceneTrigger().getIcon(mContext));
        mTriggerTitle.setText(mSmartScene.getSceneTrigger().getTitle(mContext));
        mTriggerSummary.setText(mSmartScene.getSceneTrigger().getInfo(mContext));

        if (mSceneFonctionList.size() > 0) {
            FonctionListAdapter fonctionListAdapter = new FonctionListAdapter(mContext, mSceneFonctionList);
            mListView.setAdapter(fonctionListAdapter);
            mEmpty.setVisibility(View.GONE);
        } else {
            mEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {

        setContentView(R.layout.activity_setting_scene);

        //Edit
        mEditView = findViewById(R.id.edit_item);
        mIcon = (ImageView) mEditView.findViewById(R.id.edit_icon);
        mLabel = (EditText) mEditView.findViewById(R.id.edittext_name);
        //Trigger
        mTriggerHeadView = findViewById(R.id.head_item);
        mHeadText = (TextView) findViewById(R.id.text_head);
        mHeadText.setText(getString(R.string.scene_bind));
        mHeadText.setVisibility(View.VISIBLE);
        //
        mTriggerCommonView = findViewById(R.id.common_item);
        mTriggerIcon = (ImageView) findViewById(R.id.common_item_icon);
        mTriggerTitle = (TextView) findViewById(android.R.id.title);
        mTriggerSummary = (TextView) findViewById(android.R.id.summary);
        //FonctionList
        mListView = (ListView) findViewById(R.id.setting_list);
        mAdd = (Button) findViewById(R.id.button_add);
        mEmpty = (TextView) findViewById(R.id.setting_empty);
    }

    private void initListener() {
        if (mSmartScene.getSceneTrigger().mTrigglerMode != SceneTrigger.AP)
        mTriggerCommonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AlarmSettingActivity.class);
                intent.putExtra(SceneTrigger.SCENETRIGGER, mSmartScene.getSceneTrigger());
                startActivityForResult(intent, 1);
            }
        });
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.showTimeEditDialog(getFragmentManager(), getOhterFonctions(mSceneFonctionList));
            }
        });
    }

    private List<SceneFonction> getOhterFonctions(List<SceneFonction> mSceneFonctionList) {
        List<SceneFonction> DEFAULT_LIST = new ArrayList<SceneFonction>();
        DEFAULT_LIST.add(SceneFonction.create(new SmartScene(), SceneFonction.AUDIO_PROFILE));
        DEFAULT_LIST.add(SceneFonction.create(new SmartScene(), SceneFonction.AIRPLANE));
        DEFAULT_LIST.add(SceneFonction.create(new SmartScene(), SceneFonction.BLUE_TOOTH));
        DEFAULT_LIST.add(SceneFonction.create(new SmartScene(), SceneFonction.WLAN));
        DEFAULT_LIST.add(SceneFonction.create(new SmartScene(), SceneFonction.DATA_CONNECT));
        DEFAULT_LIST.add(SceneFonction.create(new SmartScene(), SceneFonction.BRIGHTNESS));
        DEFAULT_LIST.add(SceneFonction.create(new SmartScene(), SceneFonction.GPS));
        DEFAULT_LIST.add(SceneFonction.create(new SmartScene(), SceneFonction.APP));
        for (SceneFonction sceneFonction : mSceneFonctionList) {
            DEFAULT_LIST.remove(sceneFonction.mFonctionMode);
        }
        return DEFAULT_LIST;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_edit_scene, menu);
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
            case R.id.menu_confirm:
                Toast.makeText(this.mContext, "confirm", Toast.LENGTH_LONG).show();
                mSmartScene = SmartScene.addSmartScene(getContentResolver(), mSmartScene);
                SmartScene.updateSmartScene(getContentResolver(), mSmartScene);
                setResult(1);
                finish();
                return true;
            case R.id.menu_cancel:
                Toast.makeText(this.mContext, "cancel", Toast.LENGTH_LONG).show();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
