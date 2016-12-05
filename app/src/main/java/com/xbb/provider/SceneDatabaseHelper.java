/*
* Copyright (C) 2014 MediaTek Inc.
* Modification based on code covered by the mentioned copyright
* and/or permission notice(s).
*/
/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xbb.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gustavofao.jsonapi.JSONApiConverter;
import com.xbb.fonction.SceneFonction;
import com.xbb.smartscene.R;
import com.xbb.smartscene.SmartSceneActivity;
import com.xbb.triggler.APTrigger;
import com.xbb.triggler.AlarmTrigger;
import com.xbb.triggler.SceneTrigger;
import com.xbb.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for opening the database from multiple providers.  Also provides
 * some common functionality.
 */
public class SceneDatabaseHelper extends SQLiteOpenHelper {

    private static final int VERSION_5 = 5;

    private static final int VERSION_6 = 6;

    private static final int VERSION_7 = 7;

    private static final int VERSION_8 = 8;


    // Database and table names

    // Database and table names
    static final String DATABASE_NAME = "scenes.db";
    static final String OLD_SCENES_TABLE_NAME = "scenes";
    static final String SCENES_TABLE_NAME = "scene_templates";


    private static void createScenesTable(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + SCENES_TABLE_NAME + " (" +
                /*main*/
                SmartSceneContract.SmartSceneColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SmartSceneContract.SmartSceneColumns.LABEL + " TEXT NOT NULL, " +
                SmartSceneContract.SmartSceneColumns.ICON + " TEXT NOT NULL, " +
                SmartSceneContract.SmartSceneColumns.ENABLED + " INTEGER NOT NULL, " +
                SmartSceneContract.SmartSceneColumns.ACTIVE + " INTEGER NOT NULL, " +
                /*trrigle*/
                SmartSceneContract.SmartSceneColumns.TRIGGERMODE + " TEXT NOT NULL, " +
//                SmartSceneContract.SmartSceneColumns.TRIGGERMODE + " INTEGER NOT NULL, " +
//                SmartSceneContract.SmartSceneColumns.FREQUENCY + " INTEGER, " +
//                SmartSceneContract.SmartSceneColumns.STARTHOUR + " INTEGER, " +
//                SmartSceneContract.SmartSceneColumns.STARTMINUTES + " INTEGER, " +
//                SmartSceneContract.SmartSceneColumns.ENDHOUR + " INTEGER, " +
//                SmartSceneContract.SmartSceneColumns.ENDMINUTES + " INTEGER, " +
                /*list*/
                SmartSceneContract.SmartSceneColumns.FONCTIONLIST + " TEXT);");

        LogUtils.i("Scenes Table created");
    }

    private Context mContext;

    public SceneDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_8);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createScenesTable(db);
        initDefaultData(db);
    }


    private void initDefaultData(SQLiteDatabase db) {
        SmartScene defaltS1 = new SmartScene("工作日", R.drawable.icon_pre_4, false);
        AlarmTrigger sceneTrigglerAlarm = new AlarmTrigger(12, 0, 14, 0, AlarmTrigger.eFrequency.ONCE);
        defaltS1.setSceneTrigger(sceneTrigglerAlarm);
        List<SceneFonction> sceneFonctionList = new ArrayList<SceneFonction>();
        sceneFonctionList.add(SceneFonction.create(defaltS1, SceneFonction.eFonctionMode.BRIGHTNESS));
        defaltS1.setSceneFonctionList(sceneFonctionList);

        SmartScene defaltS2 = new SmartScene("周末", R.drawable.icon_pre_4, false);
        AlarmTrigger sceneTrigglerAlarm2 = new AlarmTrigger(14, 0, 16, 0, AlarmTrigger.eFrequency.EVERYDAY);
        defaltS2.setSceneTrigger(sceneTrigglerAlarm2);
        defaltS2.setSceneFonctionList(sceneFonctionList);

        SmartScene office = new SmartScene("工作室", R.drawable.icon_pre_1, false);
        office.setSceneTrigger(SceneTrigger.create(office, SceneTrigger.eTriggleMode.AP, mContext));

        SmartScene office1 = new SmartScene("工作室1", R.drawable.icon_pre_3, false);
        office1.setSceneTrigger(SceneTrigger.create(office1, SceneTrigger.eTriggleMode.AP, mContext));

        SmartScene office2 = new SmartScene("工作室2", R.drawable.icon_pre_2, false);
        office2.setSceneTrigger(SceneTrigger.create(office2, SceneTrigger.eTriggleMode.AP, mContext));

        SmartScene office3 = new SmartScene("工作室3", R.drawable.icon_pre_1, false);
        office3.setSceneTrigger(SceneTrigger.create(office3, SceneTrigger.eTriggleMode.AP, mContext));

        insert(db, defaltS1);
        insert(db, defaltS2);
        insert(db, office);
        insert(db, office1);
        insert(db, office2);
        insert(db, office3);
    }



    public long insert(SQLiteDatabase db, SmartScene smartScene) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SmartSceneContract.SmartSceneColumns.LABEL, smartScene.getLabel());
        contentValues.put(SmartSceneContract.SmartSceneColumns.ICON, smartScene.getIcon());
        contentValues.put(SmartSceneContract.SmartSceneColumns.ENABLED, smartScene.isEnabled());
        contentValues.put(SmartSceneContract.SmartSceneColumns.ACTIVE, smartScene.isActive());
        contentValues.put(SmartSceneContract.SmartSceneColumns.TRIGGERMODE, SmartSceneActivity.mApi.toJson(smartScene.getSceneTrigger()));
//        contentValues.put(SmartSceneContract.SmartSceneColumns.TRIGGERMODE, smartScene.getSceneTrigger().mTrigglerMode.ordinal());
//        if (smartScene.getSceneTrigger().mTrigglerMode.ordinal() == 0) {
//            contentValues.put(SmartSceneContract.SmartSceneColumns.FREQUENCY, ((AlarmTrigger) smartScene.getSceneTrigger()).getFrequency().toString());
//            contentValues.put(SmartSceneContract.SmartSceneColumns.STARTHOUR, ((AlarmTrigger) smartScene.getSceneTrigger()).getStartHour());
//            contentValues.put(SmartSceneContract.SmartSceneColumns.STARTMINUTES, ((AlarmTrigger) smartScene.getSceneTrigger()).getStartMinutes());
//            contentValues.put(SmartSceneContract.SmartSceneColumns.ENDHOUR, ((AlarmTrigger) smartScene.getSceneTrigger()).getEndHour());
//            contentValues.put(SmartSceneContract.SmartSceneColumns.ENDMINUTES, ((AlarmTrigger) smartScene.getSceneTrigger()).getEndMinutes());
//        }
        return db.insert(SCENES_TABLE_NAME, SmartScene._ID, contentValues);
    }

    public long insert(SQLiteDatabase db, ContentValues contentValues) {
        return db.insert(SCENES_TABLE_NAME, SmartScene._ID, contentValues);
    }



    //check ALARM_COUNTS is Exist
    private boolean checkColumnExist1(SQLiteDatabase db, String tableName,
                                      String columnName) {
        boolean result = false;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + tableName + " LIMIT 0",
                    null);
            result = cursor != null && cursor.getColumnIndex(columnName) != -1;
        } catch (Exception e) {
            LogUtils.e("checkColumnExists1..." + e.getMessage());
        } finally {
            if (null != cursor && !cursor.isClosed()) {
                cursor.close();
            }
        }

        LogUtils.e("LYG", "checkColumnExists1..." + result);
        return result;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int currentVersion) {
        LogUtils.v("Upgrading alarms database from version "
                + oldVersion + " to " + currentVersion);
    }

}
