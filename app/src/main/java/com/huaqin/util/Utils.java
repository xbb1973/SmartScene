package com.huaqin.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;

import com.huaqin.fonction.SceneFonction;
import com.huaqin.widget.TriggerModeDialog;

import java.util.List;

/**
 * Created by HongYilin 16-11-25 下午4:47
 */
public class Utils {

    public static void showTimeEditDialog(FragmentManager manager) {

        TriggerModeDialog triggerModeDialog = new TriggerModeDialog();
        // Make sure the dialog isn't already added.
        manager.executePendingTransactions();
        final FragmentTransaction ft = manager.beginTransaction();
        final Fragment prev = manager.findFragmentByTag("TriggerModeDialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.commit();
        triggerModeDialog.show(manager, "TriggerModeDialog");
    }

    public static void showTimeEditDialog(FragmentManager manager, List<SceneFonction> sceneFonctionList) {

        TriggerModeDialog triggerModeDialog = new TriggerModeDialog();
        triggerModeDialog.setSceneFonctionList(sceneFonctionList);
        // Make sure the dialog isn't already added.
        manager.executePendingTransactions();
        final FragmentTransaction ft = manager.beginTransaction();
        final Fragment prev = manager.findFragmentByTag("TriggerModeDialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.commit();
        triggerModeDialog.show(manager, "TriggerModeDialog");
    }
}
