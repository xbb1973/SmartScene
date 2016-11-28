package com.huaqin.provider;

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

import com.huaqin.fonction.SceneFonction;
import com.huaqin.triggler.SceneTriggler;

import java.util.Calendar;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by HongYilin 16-11-21 下午4:32
 */
public class SmartScene implements Parcelable, SmartSceneContract.SmartSceneColumns, Comparator<SmartScene> {
    /**
     * SmartScenes start with an invalid id when it hasn't been saved to the database.
     */
    public static final long INVALID_ID = -1;

    /**
     * The default sort order for this table
     */
    private static final String DEFAULT_SORT_ORDER =
            STARTHOUR + ", " +
                    STARTMINUTES + " ASC" + ", " +
                    _ID + " DESC";

    private static final String[] QUERY_COLUMNS = {
            _ID,
            LABEL,
            ICON,
            ENABLED,
            ACTIVE,
            TRIGGERMODE,
            FREQUENCY,
            STARTHOUR,
            STARTMINUTES,
            ENDHOUR,
            ENDMINUTES,
            FONCTIONLIST
    };

    /**
     * These save calls to cursor.getColumnIndexOrThrow()
     * THEY MUST BE KEPT IN SYNC WITH ABOVE QUERY COLUMNS
     */
    private static final int ID_INDEX = 0;
    private static final int LABEL_INDEX = 1;
    private static final int ICON_INDEX = 2;
    private static final int ENABLED_INDEX = 3;
    private static final int ACTIVE_INDEX = 4;
    private static final int TRIGGERMODE_INDEX = 5;
    private static final int FREQUENCY_INDEX = 6;
    private static final int STARTHOUR_INDEX = 7;
    private static final int STARTMINUTES_INDEX = 8;
    private static final int ENDHOUR_INDEX = 9;
    private static final int ENDMINUTES_INDEX = 10;
    private static final int FONCTIONLIST_INDEX = 11;

    private static final int COLUMN_COUNT = FONCTIONLIST_INDEX + 1;

    public static ContentValues createContentValues(SmartScene SmartScene) {
        ContentValues values = new ContentValues(COLUMN_COUNT);
        if (SmartScene.id != INVALID_ID) {
            values.put(SmartSceneContract.SmartSceneColumns._ID, SmartScene.id);
        }

        values.put(ENABLED, SmartScene.enabled ? 1 : 0);
        values.put(LABEL, SmartScene.label);

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

    /**
     * Get SmartScene cursor loader for all SmartScenes.
     *
     * @param context to query the database.
     * @return cursor loader with all the SmartScenes.
     */
    public static CursorLoader getSmartScenesCursorLoader(Context context) {
        return new CursorLoader(context, SmartSceneContract.SmartSceneColumns.CONTENT_URI,
                QUERY_COLUMNS, null, null, DEFAULT_SORT_ORDER);
    }

    /**
     * Get SmartScene by id.
     *
     * @param contentResolver to perform the query on.
     * @param SmartSceneId    for the desired SmartScene.
     * @return SmartScene if found, null otherwise
     */
    public static SmartScene getSmartScene(ContentResolver contentResolver, long SmartSceneId) {
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

    /**
     * Get all SmartScenes given conditions.
     *
     * @param contentResolver to perform the query on.
     * @param selection       A filter declaring which rows to return, formatted as an
     *                        SQL WHERE clause (excluding the WHERE itself). Passing null will
     *                        return all rows for the given URI.
     * @param selectionArgs   You may include ?s in selection, which will be
     *                        replaced by the values from selectionArgs, in the order that they
     *                        appear in the selection. The values will be bound as Strings.
     * @return list of SmartScenes matching where clause or empty list if none found.
     */
    public static List<SmartScene> getSmartScenes(ContentResolver contentResolver,
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
                    result.add(new SmartScene(cursor));
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }

        return result;
    }

    public static boolean isTomorrow(SmartScene SmartScene) {
        return false;
    }

    public static SmartScene addSmartScene(ContentResolver contentResolver, SmartScene SmartScene) {
        ContentValues values = createContentValues(SmartScene);
        Uri uri = contentResolver.insert(CONTENT_URI, values);
        SmartScene.id = getId(uri);
        return SmartScene;
    }

    public static boolean updateSmartScene(ContentResolver contentResolver, SmartScene SmartScene) {
        if (SmartScene.id == SmartScene.INVALID_ID) return false;
        if (SmartScene.getSmartScene(contentResolver, SmartScene.id) == null)
            return false;
        ContentValues values = createContentValues(SmartScene);
        long rowsUpdated = contentResolver.update(getUri(SmartScene.id), values, null, null);
        return rowsUpdated == 1;
    }

    public static boolean deleteSmartScene(ContentResolver contentResolver, long SmartSceneId) {
        if (SmartSceneId == INVALID_ID) return false;
        int deletedRows = contentResolver.delete(getUri(SmartSceneId), "", null);
        return deletedRows == 1;
    }

    public static final Parcelable.Creator<SmartScene> CREATOR = new Parcelable.Creator<SmartScene>() {
        public SmartScene createFromParcel(Parcel p) {
            return new SmartScene(p);
        }

        public SmartScene[] newArray(int size) {
            return new SmartScene[size];
        }
    };

    // Public fields
    // TODO: Refactor instance names

    private long id;
    private String label;
    private boolean enabled;
    private boolean active;
    private SceneTriggler sceneTriggler;
    private List<SceneFonction> sceneFonctionList;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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

    public SceneTriggler getSceneTriggler() {
        return sceneTriggler;
    }

    public void setSceneTriggler(SceneTriggler sceneTriggler) {
        this.sceneTriggler = sceneTriggler;
    }

    public List<SceneFonction> getSceneFonctionList() {
        return sceneFonctionList;
    }

    public void setSceneFonctionList(List<SceneFonction> sceneFonctionList) {
        this.sceneFonctionList = sceneFonctionList;
    }


    // Creates a default SmartScene at the current time.
    public SmartScene() {
        super();
    }

    public SmartScene(String label, boolean enabled) {
        this.label = label;
        this.active = this.enabled = enabled;
    }

    public SmartScene(String label, boolean enabled, SceneTriggler sceneTriggler) {
        this.label = label;
        this.active = this.enabled = enabled;
        this.sceneTriggler = sceneTriggler;
    }

    public SmartScene(String label, boolean enabled, SceneTriggler sceneTriggler, List<SceneFonction> sceneFonctionList) {
        this.label = label;
        this.active = this.enabled = enabled;
        this.sceneTriggler = sceneTriggler;
        this.sceneFonctionList = sceneFonctionList;
    }

    public SmartScene(Cursor c) {
    }

    SmartScene(Parcel p) {
        id = p.readLong();
    }

    public String getLabelOrDefault(Context context) {
        return null;
    }

    public void writeToParcel(Parcel p, int flags) {
        p.writeLong(id);
        p.writeInt(enabled ? 1 : 0);
    }

    public int describeContents() {
        return 0;
    }


    @Override
    public String toString() {
        return "SmartScene{" +
                ", id=" + id +
                ", enabled=" + enabled +
                '}';
    }

    @Override
    public int compare(SmartScene smartScene1, SmartScene smartScene12) {
        
        return 0;
    }
}
