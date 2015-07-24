package com.moses.moses.Moses.Moses;

import com.moses.moses.Moses.Adapter.NavBarAdapter;
import com.moses.moses.Moses.Bin.GroupBin;
import com.moses.moses.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class GroupViewActivity extends Activity {
	// Nav Bar icons
	int[] mThumbIds = { R.drawable.icons_home_blue,
			R.drawable.icons_group_gray, R.drawable.icons_bill_gray,
			R.drawable.icons_configuration_gray };
	private Context mContext;
	private GroupBin group;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_view);
		mContext = getApplicationContext();
		
		// Setup NavBar Gridview
		GridView navBar = (GridView) findViewById(R.id.navBar);

		navBar.setAdapter(new NavBarAdapter(this, mThumbIds));
		navBar.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter,
					android.view.View v, int position, long id) {
				Intent intent;
				switch (position) {
				// Home
				case 0:
					intent = new Intent(mContext, MainActivity.class);
					startActivity(intent);
					finish();
					break;

				// New Group
				case 1:

					intent = new Intent(mContext, CreateGroupActivity.class);
					startActivity(intent);
					finish();
					break;

				// New Bill
				case 2:
					intent = new Intent(mContext, CreateBillActivity.class);
					startActivity(intent);
					finish();
					break;

				// Configuration
				case 3:
					intent = new Intent(mContext, ConfigurationActivity.class);
					startActivity(intent);
					finish();
					break;

				default:
					break;
				}
			}
		});
	}

	@Override
	public void onBackPressed() {
		startMainActivity();
	}

	public void startMainActivity() {
		startActivity(new Intent(this, MainActivity.class));
		finish();
	}
}
