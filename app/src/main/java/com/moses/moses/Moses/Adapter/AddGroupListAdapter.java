package com.moses.moses.Moses.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.facebook.login.widget.ProfilePictureView;
import com.moses.moses.Moses.Utils.BaseListElement;
import com.moses.moses.R;

import java.util.List;

public class AddGroupListAdapter extends ArrayAdapter<BaseListElement> {
	private List<BaseListElement> listElements;
	private Context context;
	private ProfilePictureView profilePictureView;

	public AddGroupListAdapter(Context context, int resourceId,
			List<BaseListElement> listElements) {
		super(context, resourceId, listElements);
		this.context = context;
		this.listElements = listElements;
		// Set up as an observer for list item changes to
		// refresh the view.
		for (int i = 0; i < listElements.size(); i++) {
			listElements.get(i).setAdapter(this);
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		super.getCount();
		if(this.listElements != null){
			return this.listElements.size() + 1;
		}
		else{
			return 1;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (this.listElements != null && position < this.listElements.size()) {
			if (view == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.select_list_item, null);
			}

			BaseListElement listElement = listElements.get(position);
			if (listElement != null) {
				view.setOnClickListener(listElement.getOnClickListener());
				// ImageView icon = (ImageView) view.findViewById(R.id.icon);
				TextView text = (TextView) view.findViewById(R.id.text);

				if (text != null) {
					text.setText(listElement.getText1());
				}

				// Find the user's profile picture custom view
				profilePictureView = (ProfilePictureView) view
						.findViewById(R.id.selection_profile_pic);
				profilePictureView.setCropped(true);
				profilePictureView.setProfileId(listElement.getFbId());
				// Integer.valueOf(listElement.getFbId())
			}
		}
		else{
			if (view == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);				
				view = inflater.inflate(R.layout.button_add_group_element, null);
			}
		}
		return view;
	}

}