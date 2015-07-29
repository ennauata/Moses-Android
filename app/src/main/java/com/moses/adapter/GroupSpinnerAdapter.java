package com.moses.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moses.model.Group;
import com.moses.moses.R;

import java.util.ArrayList;

public class GroupSpinnerAdapter extends BaseAdapter {

    private ArrayList<Group> listElements;

    public ArrayList<Group> getListElements() {
        return listElements;
    }

    public void setListElements(ArrayList<Group> listElements) {
        this.listElements = listElements;
    }

    private Context context;

    public GroupSpinnerAdapter(ArrayList<Group> resource, Context context) {
        super();
        this.listElements = resource;
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listElements.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout itemView;
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            itemView = (LinearLayout) mInflater.inflate(
                    R.layout.group_spinner_element, null);
        } else {
            itemView = (LinearLayout) convertView;
        }

        Group group = listElements.get(position);

        TextView tv = (TextView) itemView.findViewById(R.id.text);
        tv.setText(group.getName());

        return itemView;
    }
}