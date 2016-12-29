package com.xbb.smartscene;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import com.xbb.widget.TriggerModeDialog;

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
    private List<SceneFonction> mRestSceneFonctionList;
    private FonctionListAdapter mFonctionListAdapter;
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

    private TriggerModeDialog.Callback callback = new TriggerModeDialog.Callback() {
        @Override
        public void onDialogItemClick(int position) {

            //logic wrong think a way out
            mSceneFonctionList.add(mRestSceneFonctionList.get(position));
            mRestSceneFonctionList = getOhterFonctions(mSceneFonctionList);
            refreshListView();
        }
    };

    private void refreshListView() {

        if (mSceneFonctionList.size() > 0) {
            mFonctionListAdapter = new FonctionListAdapter(mContext, mSceneFonctionList);
            mListView.setAdapter(mFonctionListAdapter);
            mEmpty.setVisibility(View.GONE);
        } else {
            mEmpty.setVisibility(View.VISIBLE);
        }
    }

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
        mRestSceneFonctionList = getOhterFonctions(mSceneFonctionList);
        fillView();
    }

    private void fillView() {

        mTriggerCommonView.setVisibility(View.VISIBLE);
        mIcon.setImageDrawable(getResources().getDrawable(mSmartScene.getIcon()));
        mLabel.setText(mSmartScene.getLabel());
        mTriggerIcon.setImageDrawable(mSmartScene.getSceneTrigger().getIcon(mContext));
        mTriggerTitle.setText(mSmartScene.getSceneTrigger().getTitle(mContext));
        mTriggerSummary.setText(mSmartScene.getSceneTrigger().getInfo(mContext));
        refreshListView();
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
                Utils.showTimeEditDialog(getFragmentManager(), mRestSceneFonctionList, callback);
            }
        });
        mListView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(mContext, "longclick", Toast.LENGTH_LONG).show();
                return false;
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

        //may cost too much time, need upgrade
        for (SceneFonction sceneFonction : mSceneFonctionList) {
            for (int i = 0; i < DEFAULT_LIST.size(); i++)
                if (sceneFonction.mFonctionMode == DEFAULT_LIST.get(i).mFonctionMode){
                    DEFAULT_LIST.remove(i);
                }
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
                Toast.makeText(mContext, "confirm", Toast.LENGTH_LONG).show();

                mSmartScene.setLabel(mLabel.getText().toString());
                mSmartScene.setSceneFonctionList(mSceneFonctionList);
                //mSmartScene.setSceneTrigger();

                mSmartScene = SmartScene.addSmartScene(getContentResolver(), mSmartScene);
                SmartScene.updateSmartScene(getContentResolver(), mSmartScene);
                Intent intent = new Intent();
                intent.putExtra(SmartScene.TRIGGERMODE, mSmartScene.getSceneTrigger().mTrigglerMode);
                intent.putExtra(SmartScene.ID, mSmartScene.getId());
                setResult(RESULT_OK, intent);

                finish();
                return true;
            case R.id.menu_cancel:
                Toast.makeText(mContext, "cancel", Toast.LENGTH_LONG).show();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
