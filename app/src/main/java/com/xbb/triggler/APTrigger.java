package com.xbb.triggler;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;

import com.gustavofao.jsonapi.Annotations.Type;
import com.xbb.provider.SmartScene;
import com.xbb.smartscene.R;

/**
 * Created by HongYilin 16-11-22 下午10:11
 */
@Type("aptrigger")
public class APTrigger extends SceneTrigger {

    public APTrigger() {}

    public APTrigger(SmartScene parent) {
        super(parent, SceneTrigger.AP);
    }

    @Override
    public String getTitle(Context context) {
        return context.getString(R.string.trigger_ap_note);
    }

    @Override
    public Drawable getIcon(Context context) {
        return context.getResources().getDrawable(R.drawable.icon_pre_3);
    }

    @Override
    public String getInfo(Context context) {
        return context.getResources().getString(R.string.no_bundle_ap);
    }

}
