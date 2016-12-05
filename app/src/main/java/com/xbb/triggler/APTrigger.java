package com.xbb.triggler;

import com.gustavofao.jsonapi.Annotations.Type;
import com.xbb.provider.SmartScene;
import com.xbb.smartscene.R;

/**
 * Created by HongYilin 16-11-22 下午10:11
 */
@Type("aptrigger")
public class APTrigger extends SceneTrigger {

    public APTrigger() {}

    public APTrigger(SmartScene smartScene) {
        super(eTriggleMode.AP);
    }

    @Override
    public String getInfo() {
        return mContext.getResources().getString(R.string.no_bundle_ap);
    }

}
