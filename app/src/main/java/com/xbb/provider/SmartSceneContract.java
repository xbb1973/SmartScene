package com.xbb.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by HongYiLin
 * 16-11-15 下午3:27
 */

public class SmartSceneContract {

    /**
     * This authority is used for writing to or querying from the clock
     * provider.
     */
    public static final String AUTHORITY = "com.xbb.smartscene";

    /**
     * This utility class cannot be instantiated
     */
    private SmartSceneContract() {
    }

    /**
     * Constants for tables with AlarmSettings.
     */
    private interface CommonColumns {
        String _ID = "_id";
        public static final String LABEL = "label";

    }

    /**
     * Constants for the Alarms table, which contains the user created alarms.
     */
    protected interface SmartSceneColumns extends CommonColumns {
        /**
         * The content:// style URL for this table.
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/scenes");
        public static final String ICON = "icon";
        public static final String ENABLED = "enabled";
        public static final String ACTIVE = "active";
        /*trigger*/
        public static final String TRIGGERMODE = "triggermode";
        public static final String FREQUENCY = "frequency";
        public static final String STARTHOUR = "starthour";
        public static final String STARTMINUTES = "startminutes";
        public static final String ENDHOUR = "endhour";
        public static final String ENDMINUTES = "endminutes";
        /*scene fonction list*/
        public static final String FONCTIONLIST = "fonctionlist";


        public static final String SMARTSCENE = "smart_scene";
        public static final long INVALID_ID = -1;

        /**
         * The default sort order for this table
         */
        public static final String DEFAULT_SORT_ORDER =
                STARTHOUR + ", " +
                        STARTMINUTES + " ASC" + ", " +
                        _ID + " DESC";

        public static final String[] QUERY_COLUMNS = {
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
        public static final int ID_INDEX = 0;
        public static final int LABEL_INDEX = 1;
        public static final int ICON_INDEX = 2;
        public static final int ENABLED_INDEX = 3;
        public static final int ACTIVE_INDEX = 4;
        public static final int TRIGGERMODE_INDEX = 5;
        public static final int FREQUENCY_INDEX = 6;
        public static final int STARTHOUR_INDEX = 7;
        public static final int STARTMINUTES_INDEX = 8;
        public static final int ENDHOUR_INDEX = 9;
        public static final int ENDMINUTES_INDEX = 10;
        public static final int FONCTIONLIST_INDEX = 11;

        public static final int COLUMN_COUNT = FONCTIONLIST_INDEX + 1;
    }


}
