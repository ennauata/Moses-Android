package com.moses.moses.Moses.Adapter;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.moses.moses.Moses.Bin.GroupBin;
import com.moses.moses.Moses.Custom.GroupListItem;
import com.moses.moses.R;

import java.util.ArrayList;

public class GroupListAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<GroupBin> groups;

	public GroupListAdapter(Context c) {
		mContext = c;
		//groups = GroupDAO.getGroups();
	}

	public int getCount() {
		return groups.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout itemView;
		LayoutInflater mInflater = (LayoutInflater) mContext
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			itemView = (LinearLayout) mInflater.inflate(
					R.layout.group_list_item, null);
		} else {
			itemView = (LinearLayout) convertView;
		}
		Log.i("TESTE", "EH bixao");
		Log.i("TESTE", String.valueOf(position));
		GroupListItem customView = (GroupListItem) itemView.getChildAt(0);
	
		GroupBin group = groups.get(position);
		customView.setTittle(group.getName());

		return itemView;
	}
}
