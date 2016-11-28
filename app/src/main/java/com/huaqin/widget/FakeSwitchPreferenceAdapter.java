package com.huaqin.widget;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.huaqin.provider.SmartScene;
import com.huaqin.smartscene.R;
import com.huaqin.triggler.SceneTriggler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by HongYilin 16-11-25 下午5:23
 */
public class FakeSwitchPreferenceAdapter extends BaseAdapter {
    Context context;
    Map<Integer, List<SmartScene>> map;
    List<SmartScene> alarmList;
    List<SmartScene> apList;
    LayoutInflater layoutinflater;
    ViewHolder viewHolder = new ViewHolder();

    public FakeSwitchPreferenceAdapter(Context context, Map<Integer, List<SmartScene>> map) {
        this.context = context;
        this.map = map;
        alarmList = new ArrayList<SmartScene>();
        apList = new ArrayList<SmartScene>();
        alarmList = map.get(SceneTriggler.eTriggleMode.ALARM.ordinal());
        apList = map.get(SceneTriggler.eTriggleMode.AP.ordinal());
        layoutinflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return alarmList.size() + apList.size();
    }

    @Override
    public SmartScene getItem(int i) {
        if (i < alarmList.size()) {
            return alarmList.get(i);
        } else {
            return apList.get(i - alarmList.size());
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (getItem(i) != null) {
            view = layoutinflater.inflate(R.layout.scene_list_item, viewGroup, false);
            viewHolder.textHead = (TextView) view.findViewById(R.id.text_head);
            viewHolder.icon = (ImageView) view.findViewById(R.id.scene_icon);
            viewHolder.textLayout = view.findViewById(R.id.text_layout);
            viewHolder.title = (TextView) view.findViewById(android.R.id.title);
            viewHolder.summary = (TextView) view.findViewById(android.R.id.summary);
            viewHolder.enable = (Switch) view.findViewById(R.id.scene_enabled);
            fillViewHolder(i);
            return view;
        }
        return null;
    }

    private void fillViewHolder(int i) {
        //data
        switch (getItem(i).getSceneTriggler().mTrigglerMode) {
            case ALARM:
                viewHolder.textHead.setText(context.getString(R.string.scene_list_head_alarm));
                viewHolder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_pre_4));
                break;
            case AP:
                viewHolder.textHead.setText(context.getString(R.string.scene_list_head_ap));
                viewHolder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_pre_3));
                break;
            default:
                viewHolder.textHead.setText(context.getString(R.string.empty));
                viewHolder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_pre_1));
        }

        if (i == 0 || i == alarmList.size()) {
            viewHolder.textHead.setVisibility(View.VISIBLE);
        }

        viewHolder.title.setText(getItem(i).getLabel());
        viewHolder.summary.setText(getItem(i).getSceneTriggler().getInfo());
        viewHolder.enable.setChecked(getItem(i).isEnabled());

        //listener

        viewHolder.textLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("hyl", "click");

            }
        });
        viewHolder.enable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.e("hyl", "check");
            }
        });
    }


    public static class ViewHolder {
        //head
        public TextView textHead;
        //item
        public ImageView icon;
        public View textLayout;
        public TextView title;
        public TextView summary;
        public Switch enable;

        public ViewHolder() {

        }
    }

}
