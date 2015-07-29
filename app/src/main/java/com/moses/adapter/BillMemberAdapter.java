package com.moses.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moses.moses.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BillMemberAdapter extends BaseAdapter {

    private JSONArray listElements;
    private Context context;

    public BillMemberAdapter(JSONArray listElements, Context context) {
        super();
        this.listElements = listElements;
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listElements.length();
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
                    R.layout.member_bill_element, null);
        } else {
            itemView = (LinearLayout) convertView;
        }

        JSONObject node;
        String facebookId = null;
        String userName = null;
        try {
            node = (JSONObject) listElements.get(position);
            JSONObject user = (JSONObject) node.getJSONObject("user");
            facebookId = user.getString("facebook_id");
            userName = user.getString("full_name");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Set profile picture
        //ProfilePictureView profilePictureView = (ProfilePictureView) itemView
        //		.findViewById(R.id.member_list_pic);
        //profilePictureView.setCropped(true);
        //profilePictureView.setProfileId(facebookId);

        // Set name
        TextView text = (TextView) itemView.findViewById(R.id.text);
        text.setText(userName);

        return itemView;
    }
}