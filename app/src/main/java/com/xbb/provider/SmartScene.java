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
import com.gustavofao.jsonapi.Models.JSONApiObject;
import com.gustavofao.jsonapi.Models.Resource;
import com.xbb.fonction.SceneFonction;
import com.xbb.smartscene.SmartSceneActivity;
import com.xbb.triggler.SceneTrigger;
import com.xbb.util.LogUtils;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by HongYilin 16-11-21 下午4:32
 */
@Type("smartscenes")
public class SmartScene extends Resource implements Parcelable, SmartSceneContract.SmartSceneColumns, Comparator<SmartScene> {

    private String label;
    private int icon;
    private boolean enabled;
    private boolean active;
    private SceneTrigger sceneTrigger;
    private List<SceneFonction> sceneFonctionList = new ArrayList<SceneFonction>();

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
    public SmartScene() {
    }

    public SmartScene(String label, boolean enabled) {
        setId(INVALID_ID);
        this.label = label;
        this.active = this.enabled = enabled;
    }

    public SmartScene(String label, int icon, boolean enabled) {
        setId(INVALID_ID);
        this.label = label;
        this.icon = icon;
        this.active = this.enabled = enabled;
    }

    public SmartScene(String label, boolean enabled, SceneTrigger sceneTrigger) {
        setId(INVALID_ID);
        this.label = label;
        this.active = this.enabled = enabled;
        this.sceneTrigger = sceneTrigger;
    }

    public SmartScene(String label, boolean enabled, SceneTrigger sceneTrigger, List<SceneFonction> sceneFonctionList) {
        setId(INVALID_ID);
        this.label = label;
        this.active = this.enabled = enabled;
        this.sceneTrigger = sceneTrigger;
        this.sceneFonctionList = sceneFonctionList;
    }

    public SmartScene(Cursor c) {
        setId(c.getString(ID_INDEX));
        label = c.getString(LABEL_INDEX);
        icon = c.getInt(ICON_INDEX);
        enabled = c.getInt(ENABLED_INDEX) == 1;
        active = c.getInt(ACTIVE_INDEX) == 1;
        JSONApiObject jsonObject = SmartSceneActivity.mApi.fromJson(c.getString(TRIGGERMODE_INDEX));
        if (jsonObject.getData().size() > 0) {
            sceneTrigger = (SceneTrigger) jsonObject.getData(0);
            sceneTrigger.setSmartScene(this);
        }
        JSONApiObject apiObject = SmartSceneActivity.mApi.fromJson(c.getString(FONCTIONLIST_INDEX));

        if (apiObject.getData().size() > 1) {
            for (Resource resource : apiObject.getData()) {
                sceneFonctionList.add((SceneFonction) resource);
            }
        } else if (apiObject.getData().size() == 1) {
            sceneFonctionList.add((SceneFonction) apiObject.getData(0));
        }

    }

    public static ContentValues createContentValues(SmartScene smartScene) {
        ContentValues values = new ContentValues(COLUMN_COUNT);

        if (!smartScene.getId().equals(INVALID_ID)) {
            values.put(ID, smartScene.getId());
        }
        values.put(ENABLED, smartScene.enabled ? 1 : 0);
        values.put(ICON, smartScene.icon);
        values.put(LABEL, smartScene.label);
        values.put(ACTIVE, smartScene.enabled ? 1 : 0);
        values.put(TRIGGERMODE, SmartSceneActivity.mApi.toJson(smartScene.sceneTrigger));
        values.put(FONCTIONLIST, SmartSceneActivity.mApi.toJson(smartScene.sceneFonctionList));
        return values;
    }

    public static Intent createIntent(String action, String SmartSceneId) {
        return new Intent(action).setData(getUri(SmartSceneId));
    }

    public static Intent createIntent(Context context, Class<?> cls, String SmartSceneId) {
        return new Intent(context, cls).setData(getUri(SmartSceneId));
    }

    public static Uri getUri(String smartSceneId) {
        Integer integer = Integer.valueOf(smartSceneId);
        return ContentUris.withAppendedId(CONTENT_URI, integer);
    }

    public static String getId(Uri contentUri) {
        return String.valueOf(ContentUris.parseId(contentUri));
    }

    public static CursorLoader getSmartScenesCursorLoader(Context context) {
        return new CursorLoader(context, SmartSceneContract.SmartSceneColumns.CONTENT_URI,
                QUERY_COLUMNS, null, null, DEFAULT_SORT_ORDER);
    }

    public static SmartScene getSmartScene(ContentResolver contentResolver, String SmartSceneId) {
        Cursor cursor = contentResolver.query(getUri(SmartSceneId), QUERY_COLUMNS, null, null, null);
        SmartScene result = null;
        if (cursor == null) {
            return result;
        }
        try {
            if (cursor.moveToFirst()) {
                result = new SmartScene(cursor);
            }
        } finally {
            cursor.close();
        }
        return result;
    }

    public static List<SmartScene> getSmartScenes(ContentResolver contentResolver, String selection, String... selectionArgs) {
        Cursor cursor = contentResolver.query(CONTENT_URI, QUERY_COLUMNS,
                selection, selectionArgs, null);
        List<SmartScene> result = new LinkedList<SmartScene>();

        if (cursor == null) {
            return result;
        }
        try {
            if (cursor.moveToFirst()) {
                do {
                    result.add(new SmartScene(cursor));
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
        smartScene.setId(getId(uri));
        //smartScene.getSceneTrigger().setId(getId(uri));
        return smartScene;
    }

    public static boolean updateSmartScene(ContentResolver contentResolver, SmartScene smartScene) {
        if (smartScene.getId().equals(INVALID_ID)) return false;
        if (SmartScene.getSmartScene(contentResolver, smartScene.getId()) == null)
            return false;
        ContentValues values = createContentValues(smartScene);
        int updateRows = contentResolver.update(getUri(smartScene.getId()), values, null, null);
        return updateRows == 1;
    }

    public static boolean deleteSmartScene(ContentResolver contentResolver, String smartSceneId) {
        if (smartSceneId.equals(INVALID_ID)) return false;
        int deleteRows = contentResolver.delete(getUri(smartSceneId), "", null);
        return deleteRows == 1;
    }


    public SmartScene(Parcel p) {
        setId(p.readString());
        label = p.readString();
        icon = p.readInt();
        enabled = p.readInt() == 1;
        active = p.readInt() == 1;
        sceneTrigger = (SceneTrigger) SmartSceneActivity.mApi.fromJson(p.readString()).getData(0);
        List<Resource> resourceList = SmartSceneActivity.mApi.fromJson(p.readString()).getData();
        if (resourceList.size() > 0) {
            for (Resource resource : resourceList) {
                sceneFonctionList.add((SceneFonction) resource);
            }
        }
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
        p.writeString(getId());
        p.writeString(label);
        p.writeInt(icon);
        p.writeInt(enabled ? 1 : 0);
        p.writeInt(active ? 1 : 0);
        p.writeString(SmartSceneActivity.mApi.toJson(sceneTrigger));
        p.writeString(SmartSceneActivity.mApi.toJson(sceneFonctionList));
    }

    public int describeContents() {
        return 0;
    }


    @Override
    public String toString() {
        return "SmartScene{" +
                ", _id=" + getId() +
                ", enabled=" + enabled +
                '}';
    }

    @Override
    public int compare(SmartScene smartScene1, SmartScene smartScene12) {

        return 0;
    }
}
