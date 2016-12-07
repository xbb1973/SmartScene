package com.xbb.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.xbb.provider.SmartScene;
import com.xbb.smartscene.R;
import com.xbb.triggler.SceneTrigger;
import com.xbb.util.LogUtils;

import java.util.List;

/**
 * Created by HongYilin 16-11-25 下午5:23
 */
public class FakeRadioPreferenceAdapter extends BaseAdapter {

    Context context;
    List<SmartScene> list;
    LayoutInflater layoutinflater;

    public FakeRadioPreferenceAdapter(Context context, List<SmartScene> list) {
        this.context = context;
        this.list = list;
        layoutinflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public SmartScene getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (getItem(i) != null) {
            viewHolder = new ViewHolder();
            view = layoutinflater.inflate(R.layout.radio_button_item, viewGroup, false);
            viewHolder.icon = (ImageView) view.findViewById(R.id.trigger_icon);
            viewHolder.textLayout = view.findViewById(R.id.text_layout);
            viewHolder.title = (TextView) view.findViewById(android.R.id.title);
            viewHolder.summary = (TextView) view.findViewById(android.R.id.summary);
            viewHolder.select = (RadioButton) view.findViewById(R.id.radio_selected);

            viewHolder.select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getItem(i).setEnabled(!getItem(i).isEnabled());
                    setOtherUnable(list, getItem(i));
                    notifyDataSetChanged();
                }
            });

            fillViewHolder(viewHolder, i);
            return view;
        }
        return null;
    }

    private void setOtherUnable(List<SmartScene> list, SmartScene smartScene) {
        for (SmartScene s : list) {
            if (!s.getLabel().equals(smartScene.getLabel())) {
                s.setEnabled(false);
            }
        }
    }

    private void fillViewHolder(ViewHolder viewHolder, int i) {
        //data
        switch (getItem(i).getSceneTrigger().mTrigglerMode) {
            case SceneTrigger.ALARM:
                viewHolder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_pre_4));
                viewHolder.title.setText(R.string.alarm_title);
                viewHolder.summary.setText(R.string.trigger_alarm_note);
                viewHolder.select.setChecked(getItem(i).isEnabled());
                break;
            case SceneTrigger.AP:
                viewHolder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_pre_3));
                viewHolder.title.setText(R.string.scene_list_head_ap);
                viewHolder.summary.setText(R.string.trigger_ap_note);
                viewHolder.select.setChecked(getItem(i).isEnabled());
                break;
            default:
                viewHolder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_pre_1));
                viewHolder.title.setText("--");
                viewHolder.summary.setText("--");
                viewHolder.select.setChecked(getItem(i).isEnabled());
        }
    }

    public int getMode() {
        for (SmartScene s : list) {
            if (s.isEnabled()) {
                return Integer.valueOf(s.getLabel());
            }
        }
        return 0;
    }


    public static class ViewHolder {
        //item
        public ImageView icon;
        public View textLayout;
        public TextView title;
        public TextView summary;
        public RadioButton select;

        public ViewHolder() {

        }
    }

}
