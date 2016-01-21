/**
 * In this class,it mainly provide an adapter with showing comment list from server
 * in a view list.
 */
package com.example.jxq48.restaurant_finder.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jxq48.restaurant_finder.R;
import com.example.jxq48.restaurant_finder.entities.Comment;

import java.util.ArrayList;

/**
 * Created by jxq48 on 7/17/15.
 */
public class CommentsViewAdapter extends BaseAdapter {
    private ArrayList<Comment> comms;
    private LayoutInflater inflater;

    public CommentsViewAdapter(Context context, ArrayList<Comment> comms) {
        this.comms = comms;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return comms.size();
    }

    @Override
    public Object getItem(int position) {
        return comms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     *This method is used to get comment content and set it in the TextView of the ListView
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.comment_item, null);
            viewHolder.text = (TextView) convertView.findViewById(R.id.CommentText);
            viewHolder.username = (TextView) convertView.findViewById(R.id.CommentName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.text.setText(comms.get(position).getText());
        viewHolder.username.setText(comms.get(position).getUsername());
        return convertView;
    }
    public static class ViewHolder {
        TextView text;
        TextView username;
    }
}
