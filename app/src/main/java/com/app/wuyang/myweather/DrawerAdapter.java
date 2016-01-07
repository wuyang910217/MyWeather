package com.app.wuyang.myweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.wuyang.myweather.data.DrawerData;

import java.util.List;

/**
 * Created by wuyang on 16-1-1.
 */
public class DrawerAdapter extends ArrayAdapter<DrawerData> {
    private int resourceId;

    public DrawerAdapter(Context context,int textViewResourceId, List<DrawerData> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DrawerData mData = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new ViewHolder();
            viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.mImage);
            viewHolder.mTextView = (TextView) convertView.findViewById(R.id.mItem);
            convertView.setTag(viewHolder);
        } else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.mImageView.setImageResource(mData.getImageId());
        viewHolder.mTextView.setText(mData.getItemName());

        return convertView;
    }

    private class ViewHolder {
        private ImageView mImageView;
        private TextView mTextView;
    }


}
