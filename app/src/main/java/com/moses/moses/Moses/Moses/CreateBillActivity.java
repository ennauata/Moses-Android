package com.moses.moses.Moses.Moses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;

import com.moses.moses.Moses.Adapter.BillMemberAdapter;
import com.moses.moses.Moses.Adapter.GroupSpinnerAdapter;
import com.moses.moses.Moses.Adapter.NavBarAdapter;
import com.moses.moses.Moses.Bin.BillBin;
import com.moses.moses.Moses.Bin.GroupBin;
import com.moses.moses.Moses.DAO.BillsDAO;
import com.moses.moses.Moses.DAO.UserDAO;
import com.moses.moses.Moses.Utils.Callback;
import com.moses.moses.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class CreateBillActivity extends Activity {
	private Context mContext;
	private int[] mThumbIds = { R.drawable.icons_home_gray,
			R.drawable.icons_group_gray, R.drawable.icons_bill_blue,
			R.drawable.icons_configuration_gray };
	private ListView listView;
	private JSONArray memberList;
	private EditText inputBillName;
	private EditText inputBillDescription;
	private EditText inputValue;
	private String currGroupId;
	
	public String getCurrGroupId() {
		return currGroupId;
	}

	public void setCurrGroupId(String currGroupId) {
		this.currGroupId = currGroupId;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_bill);
		mContext = getApplicationContext();

		// Setup NavBar Gridview
		GridView navBar = (GridView) findViewById(R.id.navBar);

		navBar.setAdapter(new NavBarAdapter(this, mThumbIds));
		navBar.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter,
					View v, int position, long id) {
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
		// Set up input views
		inputBillName = (EditText)findViewById(R.id.inputBillName);
		inputBillDescription = (EditText)findViewById(R.id.inputBillDescription);
		inputValue = (EditText)findViewById(R.id.inputValue);
		
		// Setup Done Button
		Button doneB = (Button) findViewById(R.id.done);
		doneB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {					
				BillBin bill = new BillBin(new String[]{"1", "2"}, new float[]{200, -1}, inputBillName.getText().toString(), inputBillDescription.getText().toString(), currGroupId, Float.parseFloat(inputValue.getText().toString()), "2014-01-01T23:28:56.782Z");
				try {
					BillsDAO.getBillInstance().setBillDaoCallback(new Callback(){

						@Override
						public void callbackCall() {
							// TODO Auto-generated method stub
							try {
								BillsDAO.getInstance().requestBills();
								startMainActivity();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					});
					BillsDAO.getInstance().createBill(bill);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		// Set up spinner
		Spinner spinner = (Spinner) findViewById(R.id.group_spinner);
		ArrayList<GroupBin> resource = null;
		//resource = GroupDAO.getInstance().getGroups();
		final GroupSpinnerAdapter spinnerAdapter = new GroupSpinnerAdapter(resource, mContext);
		spinner.setAdapter(spinnerAdapter);
		
		// Set spinner listener
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		    		// Get spinner initial position
				GroupBin group = spinnerAdapter.getListElements().get(position);
				setCurrGroupId(String.valueOf(group.getId()));
				try {
					requestUserInGroup(String.valueOf(group.getId()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		// Set up list of members
		listView = (ListView) findViewById(R.id.member_list);
	}

	public void requestUserInGroup(String groupId) throws IOException{
		// Request group members
		//GroupDAO.getInstance().setGroupDaoCallback(new Callback() {
			
		//	@Override
		//	public void callbackCall() {
				// TODO Auto-generated method stub
		//		try {
		//			updateMembersList();
		//		} catch (JSONException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		//	}
		//});
		//GroupDAO.getInstance().requestUserInGroup(groupId);
	}
	
	public void updateMembersList() throws JSONException{
		JSONObject response = new JSONObject(UserDAO.getInstance().getResponse());
		memberList = response.getJSONArray("results");
		
		BillMemberAdapter billListAdapter = new BillMemberAdapter(memberList, mContext);
		listView.setAdapter(billListAdapter);
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
