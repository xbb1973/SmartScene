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

package com.huaqin.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;

import com.huaqin.util.LogUtils;

import java.util.Calendar;

/**
 * Helper class for opening the database from multiple providers.  Also provides
 * some common functionality.
 */
public class SceneDatabaseHelper extends SQLiteOpenHelper {

    private static final int VERSION_5 = 5;

    private static final int VERSION_6 = 6;

    private static final int VERSION_7 = 7;

    private static final int VERSION_8 = 8;

    private static final String DEFAULT_SCENE_1 = "(8, 30, 31, 0, 0, '', NULL, 0);";
    private static final String DEFAULT_SCENE_2 = "(9, 00, 96, 0, 0, '', NULL, 0);";


    // Database and table names
    //static final String DATABASE_NAME = "alarms.db";
    static final String OLD_ALARMS_TABLE_NAME = "alarms";
    static final String ALARMS_TABLE_NAME = "alarm_templates";
    static final String INSTANCES_TABLE_NAME = "alarm_instances";
    static final String CITIES_TABLE_NAME = "selected_cities";

    // Database and table names
    static final String DATABASE_NAME = "scenes.db";
    static final String OLD_SCENES_TABLE_NAME = "scenes";
    static final String SCENES_TABLE_NAME = "scene_templates";


    private static void createScenesTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ALARMS_TABLE_NAME + " (" +
                /*main*/
                SmartSceneContract.SmartSceneColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SmartSceneContract.SmartSceneColumns.LABEL + " TEXT NOT NULL, " +
                SmartSceneContract.SmartSceneColumns.ICON + " TEXT NOT NULL, " +
                SmartSceneContract.SmartSceneColumns.ENABLED + " INTEGER NOT NULL, " +
                SmartSceneContract.SmartSceneColumns.ACTIVE + " INTEGER NOT NULL, " +
                /*trrigle*/
                SmartSceneContract.SmartSceneColumns.TRIGGERMODE + " INTEGER NOT NULL, " +
                SmartSceneContract.SmartSceneColumns.FREQUENCY + " INTEGER, " +
                SmartSceneContract.SmartSceneColumns.STARTHOUR + " INTEGER, " +
                SmartSceneContract.SmartSceneColumns.STARTMINUTES + " INTEGER, " +
                SmartSceneContract.SmartSceneColumns.ENDHOUR + " INTEGER, " +
                SmartSceneContract.SmartSceneColumns.ENDMINUTES + " INTEGER, " +
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

        // insert default alarms
        LogUtils.i("Inserting default alarms");
        String cs = ", "; //comma and space
        String insertMe = "INSERT INTO " + ALARMS_TABLE_NAME + " (" +
                SmartSceneContract.SmartSceneColumns.LABEL + cs +
                SmartSceneContract.SmartSceneColumns.ICON + cs +
                SmartSceneContract.SmartSceneColumns.ENABLED + cs +
                SmartSceneContract.SmartSceneColumns.ACTIVE + cs +
                SmartSceneContract.SmartSceneColumns.TRIGGERMODE + cs +
                SmartSceneContract.SmartSceneColumns.FREQUENCY + cs +
                SmartSceneContract.SmartSceneColumns.STARTHOUR + cs +
                SmartSceneContract.SmartSceneColumns.STARTMINUTES + cs +
                SmartSceneContract.SmartSceneColumns.ENDHOUR + cs +
                SmartSceneContract.SmartSceneColumns.ENDMINUTES + cs + ") VALUES ";
        db.execSQL(insertMe + DEFAULT_SCENE_1);
        db.execSQL(insertMe + DEFAULT_SCENE_2);
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
			android.util.Log.e("LYG", "checkColumnExists1..." + e.getMessage());
		} finally {
			if (null != cursor && !cursor.isClosed()) {
				cursor.close();
			}
		}
		
		android.util.Log.e("LYG", "checkColumnExists1..." + result);
		return result;
	}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int currentVersion) {
        LogUtils.v("Upgrading alarms database from version "
                + oldVersion + " to " + currentVersion);
        /*

        //add by hq lingyuguo start for ALARM_COUNTS
        if(oldVersion <= VERSION_7 && !checkColumnExist1(db,INSTANCES_TABLE_NAME,ClockContract.InstancesColumns.ALARM_COUNTS)){
            android.util.Log.e("DeskClock","Upgrading alarms database from version "
                + oldVersion + " to " + currentVersion);
            db.execSQL("ALTER TABLE " + INSTANCES_TABLE_NAME +
                    " ADD COLUMN " + ClockContract.InstancesColumns.ALARM_COUNTS +" INTEGER NOT NULL DEFAULT 0;");
        }
        //add by hq lingyuguo end

        if (oldVersion <= VERSION_6) {
            // These were not used in DB_VERSION_6, so we can just drop them.
            db.execSQL("DROP TABLE IF EXISTS " + INSTANCES_TABLE_NAME + ";");
            db.execSQL("DROP TABLE IF EXISTS " + CITIES_TABLE_NAME + ";");

            // Create new alarms table and copy over the data
            createAlarmsTable(db);
            createInstanceTable(db);
            createCitiesTable(db);

            LogUtils.i("Copying old alarms to new table");
            String[] OLD_TABLE_COLUMNS = {
                    "_id",
                    "hour",
                    "minutes",
                    "daysofweek",
                    "enabled",
                    "vibrate",
                    "message",
                    "alert",
            };
            Cursor cursor = db.query(OLD_ALARMS_TABLE_NAME, OLD_TABLE_COLUMNS,
                    null, null, null, null, null);
            Calendar currentTime = Calendar.getInstance();
            while (cursor.moveToNext()) {
                Alarm alarm = new Alarm();
                alarm.id = cursor.getLong(0);
                alarm.hour = cursor.getInt(1);
                alarm.minutes = cursor.getInt(2);
                alarm.daysOfWeek = new DaysOfWeek(cursor.getInt(3));
                alarm.enabled = cursor.getInt(4) == 1;
                alarm.vibrate = cursor.getInt(5) == 1;
                alarm.label = cursor.getString(6);

                String alertString = cursor.getString(7);
                if ("silent".equals(alertString)) {
                    alarm.alert = Alarm.NO_RINGTONE_URI;
                } else {
                    alarm.alert = TextUtils.isEmpty(alertString) ? null : Uri.parse(alertString);
                }

                // Save new version of alarm and create alarminstance for it
                db.insert(ALARMS_TABLE_NAME, null, Alarm.createContentValues(alarm));
                if (alarm.enabled) {
                    AlarmInstance newInstance = alarm.createInstanceAfter(currentTime);
                    db.insert(INSTANCES_TABLE_NAME, null,
                            AlarmInstance.createContentValues(newInstance));
                }
            }
            cursor.close();

            LogUtils.i("Dropping old alarm table");
            db.execSQL("DROP TABLE IF EXISTS " + OLD_ALARMS_TABLE_NAME + ";");
        }

        */
    }
    
}
