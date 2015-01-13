package com.example.mobiledev;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterLinks extends BaseAdapter {
    List<LinkModel> linkList = new ArrayList<LinkModel>();
    Context context;

    /** constructors */
    public AdapterLinks() {
    }

    public AdapterLinks(Context context) {
        this.context = context;
    }

    public AdapterLinks(Context context, List<LinkModel> list) {
        this.linkList = list;
        this.context = context;
    }

    /** function to return number of elements in list */
    @Override
    public int getCount() {
        if (linkList != null)
            return linkList.size();
        else
            return 0;
    }

    /** get the element in a specific position */
    @Override
    public Object getItem(int position) {

        if (linkList != null)
            return linkList.get(position);
        else
            return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    /** set the list in the listview and notify the adapter on change */
    public void setItems(List<LinkModel> items) {
        linkList = items;
        notifyDataSetChanged();
    }

    /** creates the row layout for the links */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder viewHolder = new Holder();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_link, null);

            viewHolder.tvLinkName = (TextView) convertView
                    .findViewById(R.id.tvLinkName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Holder) convertView.getTag();
        }
        if (position < linkList.size()) {
            viewHolder.tvLinkName.setText(linkList.get(position).getName());
        }
        return convertView;
    }

    /** holder for the row layout elements */
    final class Holder {
        TextView tvLinkName;
    }
}