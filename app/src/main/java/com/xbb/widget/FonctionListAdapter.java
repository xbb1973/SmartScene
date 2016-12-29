package com.xbb.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.xbb.fonction.SceneFonction;
import com.xbb.smartscene.R;

import java.util.List;

/**
 * Created by HongYilin 16-11-25 下午5:23
 */
public class FonctionListAdapter extends BaseAdapter {

    Context context;
    List<SceneFonction> fonctionList;
    LayoutInflater layoutinflater;
    ViewHolder viewHolder;
    int flag;

    public FonctionListAdapter(Context context, List<SceneFonction> fonctionList) {
        this.context = context;
        this.fonctionList = fonctionList;
        layoutinflater = LayoutInflater.from(context);
        flag = 0;
    }
    public FonctionListAdapter(Context context, List<SceneFonction> fonctionList, int flag) {
        this.context = context;
        this.fonctionList = fonctionList;
        layoutinflater = LayoutInflater.from(context);
        this.flag = flag;
    }

    @Override
    public int getCount() {
        return fonctionList.size();
    }

    @Override
    public SceneFonction getItem(int i) {
        return fonctionList.get(i);
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
            viewHolder.textHead.setText(context.getString(R.string.scene_feature));
            viewHolder.icon = (ImageView) view.findViewById(R.id.scene_icon);
            viewHolder.textLayout = view.findViewById(R.id.text_layout);
            viewHolder.title = (TextView) view.findViewById(android.R.id.title);
            viewHolder.summary = (TextView) view.findViewById(android.R.id.summary);
            viewHolder.enable = (Switch) view.findViewById(R.id.scene_enabled);
            fillViewHolder(i);
//
//            viewHolder.textLayout.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View view) {
//
//                }
//            });

            return view;
        }
        return null;
    }

    private void fillViewHolder(int i) {
        //data
        switch (getItem(i).mFonctionMode) {
            case SceneFonction.AUDIO_PROFILE:
                viewHolder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.menu_sound));
                viewHolder.title.setText(context.getText(R.string.audio_profiles_title));
                viewHolder.summary.setText(context.getText(R.string.audio_profiles_title));
                viewHolder.enable.setVisibility(View.GONE);
                break;
            case SceneFonction.AIRPLANE:
                viewHolder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.menu_airplanemodel));
                viewHolder.title.setText(context.getText(R.string.airplane_title));
                viewHolder.summary.setText(context.getText(R.string.airplane_title));
                break;
            case SceneFonction.BLUE_TOOTH:
                viewHolder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.menu_bluetooth));
                viewHolder.title.setText(context.getText(R.string.bluetooth_title));
                viewHolder.summary.setText(context.getText(R.string.bluetooth_title));
                break;
            case SceneFonction.WLAN:
                viewHolder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.menu_wifi));
                viewHolder.title.setText(context.getText(R.string.WLAN));
                viewHolder.summary.setText(context.getText(R.string.WLAN));
                break;
            case SceneFonction.DATA_CONNECT:
                viewHolder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.menu_celluar));
                viewHolder.title.setText(context.getText(R.string.data_connection_title));
                viewHolder.summary.setText(context.getText(R.string.data_connection_title));
                break;
            case SceneFonction.BRIGHTNESS:
                viewHolder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.menu_light));
                viewHolder.title.setText(context.getText(R.string.brightness_title));
                viewHolder.summary.setText(getItem(i).getInfo(context));
                viewHolder.enable.setVisibility(View.GONE);
                break;
            case SceneFonction.GPS:
                viewHolder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.menu_gps));
                viewHolder.title.setText(context.getText(R.string.gps_title));
                viewHolder.summary.setText(context.getText(R.string.gps_title));
                break;
            case SceneFonction.APP:
                viewHolder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.menu_settings));
                viewHolder.title.setText(context.getText(R.string.open_app_title));
                viewHolder.summary.setText(context.getText(R.string.open_app_title));
                break;
            default:
                break;
        }

        if (i == 0) {
            viewHolder.textHead.setVisibility(View.VISIBLE);
        }
        if (flag > 0) {
            viewHolder.textHead.setVisibility(View.GONE);
            viewHolder.enable.setVisibility(View.GONE);
        }

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
