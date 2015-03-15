package com.moses.Moses;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;

import com.moses.Adapters.GroupListAdapter;
import com.moses.Adapters.NavBarAdapter;
import com.moses.Adapters.StatsViewAdapter;
import com.moses.DAO.GroupDAO;
import com.moses.DAO.UserDAO;

public class MainActivity extends FragmentActivity {
	private static Context mContext;

	// List view
	private static ListView lv;

	// Listview Adapter
	static GroupListAdapter adapter;

	// Search EditText
	EditText inputSearch;

	// ArrayList for Listview
	ArrayList<HashMap<String, String>> productList;

	// Nav Bar icons
	int[] mThumbIds = { R.drawable.icons_home_blue,
			R.drawable.icons_group_gray, R.drawable.icons_bill_gray,
			R.drawable.icons_configuration_gray };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = getApplicationContext();

		lv = (ListView) findViewById(R.id.list_view);
		inputSearch = (EditText) findViewById(R.id.inputSearch);
		
		// Set up click listener for the list view
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(mContext, GroupViewActivity.class);
				startActivity(intent);
				finish();
			}
		}); 
		
		// Update items in list
		updateList();

		// Setup Stats Gridview
		GridView stats = (GridView) findViewById(R.id.stats);
		stats.setAdapter(new StatsViewAdapter(this));
		stats.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter,
					android.view.View v, int position, long id) {
			}
		});

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

	public static void updateList() {
		if (GroupDAO.getGroups() != null) {
			// Adding items to listview
			adapter = new GroupListAdapter(mContext);
			lv.setAdapter(adapter);
		}
	}
}