package com.xbb.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.gustavofao.jsonapi.Annotations.Type;
import com.gustavofao.jsonapi.Annotations.Types;
import com.gustavofao.jsonapi.Models.Resource;
import com.xbb.fonction.SceneFonction;
import com.xbb.smartscene.SmartSceneActivity;
import com.xbb.triggler.AlarmTrigger;
import com.xbb.triggler.SceneTrigger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by HongYilin 16-11-21 下午4:32
 */
@Type("smartscenes")
public class SmartScene extends Resource implements Parcelable, SmartSceneContract.SmartSceneColumns, Comparator<SmartScene> {


    private long _id = INVALID_ID;
    private String label;
    private int icon;
    private boolean enabled;
    private boolean active;
    private SceneTrigger sceneTrigger;
    private List<SceneFonction> sceneFonctionList;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public SceneTrigger getSceneTrigger() {
        return sceneTrigger;
    }

    public void setSceneTrigger(SceneTrigger sceneTrigger) {
        this.sceneTrigger = sceneTrigger;
    }

    public List<SceneFonction> getSceneFonctionList() {
        return sceneFonctionList;
    }

    public void setSceneFonctionList(List<SceneFonction> sceneFonctionList) {
        this.sceneFonctionList = sceneFonctionList;
    }

    // Creates a default SmartScene at the current time.
    public SmartScene() {}

    public SmartScene(String label, boolean enabled) {
        setId(String.valueOf(_id));
        this.label = label;
        this.active = this.enabled = enabled;
    }

    public SmartScene(String label, int icon, boolean enabled) {
        setId(String.valueOf(_id));
        this.label = label;
        this.icon = icon;
        this.active = this.enabled = enabled;
    }

    public SmartScene(long _id, String label, int icon, boolean enabled, boolean active) {
        this._id = _id;
        setId(String.valueOf(_id));
        this.label = label;
        this.icon = icon;
        this.enabled = enabled;
        this.active = active;
    }

    public SmartScene(String label, boolean enabled, SceneTrigger sceneTrigger) {
        setId(String.valueOf(_id));
        this.label = label;
        this.active = this.enabled = enabled;
        this.sceneTrigger = sceneTrigger;
    }

    public SmartScene(String label, boolean enabled, SceneTrigger sceneTrigger, List<SceneFonction> sceneFonctionList) {
        setId(String.valueOf(_id));
        this.label = label;
        this.active = this.enabled = enabled;
        this.sceneTrigger = sceneTrigger;
        this.sceneFonctionList = sceneFonctionList;
    }

    public SmartScene(Cursor c, Context context) {
        _id = c.getLong(ID_INDEX);
        setId(String.valueOf(_id));
        label = c.getString(LABEL_INDEX);
        icon = c.getInt(ICON_INDEX);
        enabled = c.getInt(ENABLED_INDEX) == 1;
        active = c.getInt(ACTIVE_INDEX) == 1;
        sceneTrigger = (SceneTrigger) SmartSceneActivity.mApi.fromJson(c.getString(TRIGGERMODE_INDEX)).getData(0);
//        SmartScene temp = new SmartScene(_id, label, icon, enabled, active);
//        if (c.getInt(TRIGGERMODE_INDEX) == SceneTrigger.eTriggleMode.ALARM.ordinal()) {
//
//            sceneTrigger = SceneTrigger.create(temp, SceneTrigger.eTriggleMode.ALARM, context);
//
//            ((AlarmTrigger) sceneTrigger).setFrequency(AlarmTrigger.eFrequency.valueOf(c.getString(FREQUENCY_INDEX)));
//            ((AlarmTrigger) sceneTrigger).setStartHour(c.getInt(STARTHOUR_INDEX));
//            ((AlarmTrigger) sceneTrigger).setStartMinutes(c.getInt(STARTMINUTES_INDEX));
//            ((AlarmTrigger) sceneTrigger).setEndHour(c.getInt(ENDHOUR_INDEX));
//            ((AlarmTrigger) sceneTrigger).setEndMinutes(c.getInt(ENDMINUTES_INDEX));
//        } else if (c.getInt(TRIGGERMODE_INDEX) == SceneTrigger.eTriggleMode.AP.ordinal()) {
//            sceneTrigger = SceneTrigger.create(temp, SceneTrigger.eTriggleMode.AP, context);
//        }
        //sceneFonctionList = c.getString(FONCTIONLIST_INDEX);
        sceneFonctionList = new ArrayList<SceneFonction>();
    }

    public static ContentValues createContentValues(SmartScene SmartScene) {
        ContentValues values = new ContentValues(COLUMN_COUNT);

        if (SmartScene._id != INVALID_ID) {
            values.put(_ID, SmartScene._id);
        }
        values.put(ENABLED, SmartScene.enabled ? 1 : 0);
        values.put(ICON, SmartScene.icon);
        values.put(LABEL, SmartScene.label);
        values.put(ACTIVE, SmartScene.enabled ? 1 : 0);
        values.put(TRIGGERMODE, SmartScene.sceneTrigger.mTrigglerMode.ordinal());
        if (SmartScene.sceneTrigger.mTrigglerMode.ordinal() == 0) {
            values.put(FREQUENCY, ((AlarmTrigger) SmartScene.sceneTrigger).getFrequency().toString());
            values.put(STARTHOUR, ((AlarmTrigger) SmartScene.sceneTrigger).getStartHour());
            values.put(STARTMINUTES, ((AlarmTrigger) SmartScene.sceneTrigger).getStartMinutes());
            values.put(ENDHOUR, ((AlarmTrigger) SmartScene.sceneTrigger).getEndHour());
            values.put(ENDMINUTES, ((AlarmTrigger) SmartScene.sceneTrigger).getEndMinutes());
        }
        values.put(FONCTIONLIST, " ");
        return values;
    }

    public static Intent createIntent(String action, long SmartSceneId) {
        return new Intent(action).setData(getUri(SmartSceneId));
    }

    public static Intent createIntent(Context context, Class<?> cls, long SmartSceneId) {
        return new Intent(context, cls).setData(getUri(SmartSceneId));
    }

    public static Uri getUri(long SmartSceneId) {
        return ContentUris.withAppendedId(CONTENT_URI, SmartSceneId);
    }

    public static long getId(Uri contentUri) {
        return ContentUris.parseId(contentUri);
    }

    public static CursorLoader getSmartScenesCursorLoader(Context context) {
        return new CursorLoader(context, SmartSceneContract.SmartSceneColumns.CONTENT_URI,
                QUERY_COLUMNS, null, null, DEFAULT_SORT_ORDER);
    }

    public static SmartScene getSmartScene(ContentResolver contentResolver, long SmartSceneId, Context context) {
        Cursor cursor = contentResolver.query(getUri(SmartSceneId), QUERY_COLUMNS, null, null, null);
        SmartScene result = null;
        if (cursor == null) {
            return result;
        }
        try {
            if (cursor.moveToFirst()) {
                result = new SmartScene(cursor, context);
            }
        } finally {
            cursor.close();
        }
        return result;
    }

    public static List<SmartScene> getSmartScenes(ContentResolver contentResolver, Context context,
                                                  String selection, String... selectionArgs) {
        Cursor cursor = contentResolver.query(CONTENT_URI, QUERY_COLUMNS,
                selection, selectionArgs, null);
        List<SmartScene> result = new LinkedList<SmartScene>();

        if (cursor == null) {
            return result;
        }
        try {
            if (cursor.moveToFirst()) {
                do {
                    result.add(new SmartScene(cursor, context));
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return result;
    }

    public static SmartScene addSmartScene(ContentResolver contentResolver, SmartScene smartScene) {
        ContentValues values = createContentValues(smartScene);
        Uri uri = contentResolver.insert(CONTENT_URI, values);
        smartScene._id = getId(uri);
        return smartScene;
    }

    public static boolean updateSmartScene(ContentResolver contentResolver, SmartScene smartScene, Context context) {
        if (smartScene._id == INVALID_ID) return false;
        if (SmartScene.getSmartScene(contentResolver, smartScene._id, context) == null)
            return false;
        ContentValues values = createContentValues(smartScene);
        int updateRows = contentResolver.update(getUri(smartScene._id), values, null, null);
        return updateRows == 1;
    }

    public static boolean deleteSmartScene(ContentResolver contentResolver, long smartSceneId) {
        if (smartSceneId == INVALID_ID) return false;
        int deleteRows = contentResolver.delete(getUri(smartSceneId), "", null);
        return deleteRows == 1;
    }


    public SmartScene(Parcel p) {
        _id = p.readLong();
        setId(String.valueOf(_id));
        label = p.readString();
        icon = p.readInt();
        enabled = p.readInt() == 1;
        active = p.readInt() == 1;
        //sceneTrigger = p.readParcelable();

//        private int icon;
//        private boolean enabled;
//        private boolean active;
//        private SceneTrigger sceneTrigger;
//        private List<SceneFonction> sceneFonctionList;
    }

    public static final Parcelable.Creator<SmartScene> CREATOR = new Parcelable.Creator<SmartScene>() {

        @Override
        public SmartScene createFromParcel(Parcel parcel) {
            return new SmartScene(parcel);
        }

        @Override
        public SmartScene[] newArray(int size) {
            return new SmartScene[size];
        }


    };

    public String getLabelOrDefault(Context context) {
        return null;
    }

    public void writeToParcel(Parcel p, int flags) {
        p.writeLong(_id);
        p.writeString(label);
        p.writeInt(icon);
        p.writeInt(enabled ? 1 : 0);
        p.writeInt(active ? 1 : 0);
        //p.writeParcelable();
    }

    public int describeContents() {
        return 0;
    }


    @Override
    public String toString() {
        return "SmartScene{" +
                ", _id=" + _id +
                ", enabled=" + enabled +
                '}';
    }

    @Override
    public int compare(SmartScene smartScene1, SmartScene smartScene12) {

        return 0;
    }
}
