package com.huaqin.triggler;

import com.huaqin.provider.SmartScene;

/**
 * Created by HongYilin 16-11-22 下午10:11
 */
public class APTrigger extends SceneTriggler {

    public APTrigger(SmartScene smartScene) {
        super(eTriggleMode.AP);
    }

    @Override
    public String getInfo() {
        return eTriggleMode.AP.toString();
    }
}
