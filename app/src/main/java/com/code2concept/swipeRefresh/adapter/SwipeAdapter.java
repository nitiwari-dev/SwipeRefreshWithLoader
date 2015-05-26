package com.code2concept.swipeRefresh.adapter;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.code2concept.swipeRefresh.R;
import com.code2concept.swipeRefresh.viewHolder.VideoViewHolder;

/**
 * Created by nitesh on 26/5/15.
 */
public class SwipeAdapter extends CursorAdapter {


    private VideoViewHolder videoViewHolder;

    public SwipeAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        videoViewHolder = new VideoViewHolder();
        View convertView = LayoutInflater.from(context).inflate(R.layout.row_swipe_refresh , parent , false);
        videoViewHolder.videoTitleTextView = (TextView) convertView.findViewById(R.id.titleTv);

        convertView.setTag(videoViewHolder);

        return convertView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        videoViewHolder = (VideoViewHolder) view.getTag();
        videoViewHolder.videoTitleTextView.setText(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE)));
    }
}
