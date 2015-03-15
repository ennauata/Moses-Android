package com.moses.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.moses.Moses.R;

public class NavBarAdapter extends BaseAdapter {
	private Context mContext;
	private int[] mThumbIds;

	public NavBarAdapter(Context c, int[] mThumbIds) {
		mContext = c;
		this.mThumbIds = mThumbIds;
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
		ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(mContext);
			imageView.setPadding(8, 20, 8, 20);
		} else {
			imageView = (ImageView) convertView;
		}

		imageView.setImageResource(mThumbIds[position]);
		return imageView;
	}
}
