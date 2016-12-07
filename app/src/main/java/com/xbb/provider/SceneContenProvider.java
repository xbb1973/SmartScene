package com.xbb.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.xbb.util.LogUtils;

/**
 * Created by HongYilin 16-11-30 下午3:45
 */
public class SceneContenProvider extends ContentProvider {

    private SceneDatabaseHelper sceneDatabaseHelper;
    final public static int SCENES = 1;
    final public static int SCENES_ID = 2;

    private static final UriMatcher sURLMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURLMatcher.addURI(SmartSceneContract.AUTHORITY, "scenes", SCENES);
        sURLMatcher.addURI(SmartSceneContract.AUTHORITY, "scenes/#", SCENES_ID);
    }

    public SceneContenProvider() {}

    @Override
    public boolean onCreate() {
        sceneDatabaseHelper = new SceneDatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projectionIn, String selection, String[] selectionArgs,
                        String sort) {

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        // Generate the body of the query
        int match = sURLMatcher.match(uri);
        switch (match) {
            case SCENES:
                qb.setTables(SceneDatabaseHelper.SCENES_TABLE_NAME);
                break;
            case SCENES_ID:
                qb.setTables(SceneDatabaseHelper.SCENES_TABLE_NAME);
                qb.appendWhere(SmartSceneContract.SmartSceneColumns.ID + "=");
                qb.appendWhere(uri.getLastPathSegment());
                break;

            default:
                throw new IllegalArgumentException("Unknown URL " + uri);
        }

        SQLiteDatabase db = sceneDatabaseHelper.getReadableDatabase();
        Cursor ret = qb.query(db, projectionIn, selection, selectionArgs,
                null, null, sort);

        if (ret == null) {
            LogUtils.e("query: failed");
        } else {
            ret.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return ret;
    }

    @Override
    public String getType(Uri uri) {
        switch (sURLMatcher.match(uri)) {
            case SCENES:
                return "vnd.android.cursor.dir/scenes";
            case SCENES_ID:
                return "vnd.android.cursor.item/scenes";
            default:
                throw  new IllegalArgumentException("unknow uri");
        }
    }


    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        int count;
        String alarmId;
        SQLiteDatabase db = sceneDatabaseHelper.getWritableDatabase();
        switch (sURLMatcher.match(uri)) {
            case SCENES_ID:
                alarmId = uri.getLastPathSegment();
                count = db.update(SceneDatabaseHelper.SCENES_TABLE_NAME, values,
                        SmartSceneContract.SmartSceneColumns.ID + "=" + alarmId,
                        null);
                break;
            default: {
                throw new UnsupportedOperationException(
                        "Cannot update URL: " + uri);
            }
        }
        LogUtils.v("*** notifyChange() id: " + alarmId + " url " + uri);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        long rowId;
        SQLiteDatabase db = sceneDatabaseHelper.getWritableDatabase();
        switch (sURLMatcher.match(uri)) {
            case SCENES:
                rowId = sceneDatabaseHelper.insert(db, initialValues);
                break;
            default:
                throw new IllegalArgumentException("Cannot insert from URL: " + uri);
        }

        Uri uriResult = ContentUris.withAppendedId(SmartSceneContract.SmartSceneColumns.CONTENT_URI, rowId);
        getContext().getContentResolver().notifyChange(uriResult, null);
        return uriResult;
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        int count;
        String primaryKey;
        SQLiteDatabase db = sceneDatabaseHelper.getWritableDatabase();
        switch (sURLMatcher.match(uri)) {
            case SCENES:
                count = db.delete(SceneDatabaseHelper.SCENES_TABLE_NAME, where, whereArgs);
                break;
            case SCENES_ID:
                primaryKey = uri.getLastPathSegment();
                if (TextUtils.isEmpty(where)) {
                    where = SmartSceneContract.SmartSceneColumns.ID + "=" + primaryKey;
                } else {
                    where = SmartSceneContract.SmartSceneColumns.ID + "=" + primaryKey +
                            " AND (" + where + ")";
                }
                count = db.delete(SceneDatabaseHelper.SCENES_TABLE_NAME, where, whereArgs);
                break;
            default:
                throw new IllegalArgumentException("Cannot delete from URL: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
