package com.jxr202.dreammaster;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jxr35 on 2018/2/27
 */

public class SearchAdapter extends BaseAdapter {

    private static final String TAG = "jxr202";
    private LayoutInflater mLayoutInflater;
    private List<Dream> mDreams = new ArrayList<>();

    public SearchAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDreams.size();
    }

    @Override
    public Object getItem(int position) {
        return mDreams.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.activity_search_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Dream dream = mDreams.get(position);
        holder.title.setText(dream.title);
        holder.desc.setText(dream.des);
        return convertView;
    }

    static class ViewHolder {

        @BindView(R.id.title) TextView title;

        @BindView(R.id.desc) TextView desc;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void setDreams(List<Dream> dreams) {
        this.mDreams = dreams;
    }

}
