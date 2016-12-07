package com.xbb.widget;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.xbb.provider.SmartScene;
import com.xbb.smartscene.R;
import com.xbb.triggler.SceneTrigger;
import com.xbb.util.LogUtils;

import java.util.List;

/**
 * Created by HongYilin 16-11-25 下午5:23
 */
public class FakeSwitchPreferenceAdapter extends BaseAdapter {
    Context context;
    SparseArray<List<SmartScene>> sparseArray;
    List<SmartScene> alarmList;
    List<SmartScene> apList;
    LayoutInflater layoutinflater;
    ViewHolder viewHolder;
    CallBack callBack;
    int i = 0 , j = 0;


    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public FakeSwitchPreferenceAdapter(Context context, SparseArray<List<SmartScene>> sparseArray) {
        this.context = context;
        this.sparseArray = sparseArray;

        alarmList = this.sparseArray.get(SceneTrigger.ALARM);
        apList = this.sparseArray.get(SceneTrigger.AP);
        layoutinflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (alarmList != null)
            i = alarmList.size();
        if (apList != null)
            j = apList.size();
        return i + j;
    }

    @Override
    public SmartScene getItem(int position) {
        if (position < alarmList.size()) {
            return alarmList.get(position);
        } else {
            return apList.get(position - alarmList.size());
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (getItem(i) != null) {
            viewHolder = new ViewHolder();
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

    private void fillViewHolder(final int i) {
        //data
        switch (getItem(i).getSceneTrigger().mTrigglerMode) {
            case SceneTrigger.ALARM:
                viewHolder.textHead.setText(context.getString(R.string.scene_list_head_alarm));
                break;
            case SceneTrigger.AP:
                viewHolder.textHead.setText(context.getString(R.string.scene_list_head_ap));
                break;
            default:
                viewHolder.textHead.setText(context.getString(R.string.empty));
        }

        if (i == 0 || i == alarmList.size()) {
            viewHolder.textHead.setVisibility(View.VISIBLE);
        }

        viewHolder.icon.setImageDrawable(context.getResources().getDrawable(getItem(i).getIcon()));
        viewHolder.title.setText(getItem(i).getLabel());
        viewHolder.summary.setText(getItem(i).getSceneTrigger().getInfo(context));
        viewHolder.enable.setChecked(getItem(i).isEnabled());

        //listener

        viewHolder.textLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onItemClick(i);
            }
        });
        viewHolder.enable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                callBack.onSwitchClick(i);
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

    public interface CallBack {
        void onItemClick(int position);
        void onSwitchClick(int postion);
    }

}
