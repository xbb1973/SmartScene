package com.huaqin.provider;

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
    public static final String AUTHORITY = "com.huaqin.smartscene";

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
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/smartscene");
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
    }


}
