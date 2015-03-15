package com.moses.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TypeViewAdapter extends BaseAdapter {
	private Context mContext;

	public TypeViewAdapter(Context c) {
		mContext = c;
	}

	public int getCount() {
		return mThumbIds.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView textView;
		if (convertView == null) {
			textView = new TextView(mContext);
		} else {
			textView = (TextView) convertView;
		}

		textView.setText(mThumbIds[position]);
		return textView;
	}
	
	 private String[] mThumbIds = {
	            "Class", "Trip", "Family", "Another"
	    };
}
