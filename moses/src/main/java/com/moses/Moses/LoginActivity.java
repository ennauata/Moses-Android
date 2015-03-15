package com.moses.Moses;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.AppEventsLogger;
import com.facebook.model.GraphUser;
import com.moses.Fragments.LoginFragment;
import com.moses.Utils.RemoveFragmentListener;

public class LoginActivity extends FragmentActivity implements
		RemoveFragmentListener {
	private Fragment loginFragment;
	private GraphUser userInfo;
	private Boolean loggedin = false;

	public GraphUser getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(GraphUser userInfo) {
		this.userInfo = userInfo;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		//ActionBar actionBar = getActionBar();
		//actionBar.hide();

		
		super.onCreate(savedInstanceState);
		if (!isTaskRoot()) {
			finish();
			return;
		} else {
			if (savedInstanceState == null) {
				// Add the fragment on initial activity setup
				loginFragment = new LoginFragment();
				getSupportFragmentManager().beginTransaction()
						.add(android.R.id.content, loginFragment).commit();
			} else {
				// Or set the fragment from restored state info
				loginFragment = (LoginFragment) getSupportFragmentManager()
						.findFragmentById(android.R.id.content);
			}
		}
	}

	@Override
	public void onFragmentSuicide() {
		if (loggedin == false) {
			loggedin = true;
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// Logs 'install' and 'app activate' App Events.
		AppEventsLogger.activateApp(this);
	}

	@Override
	protected void onPause() {
		super.onPause();

		// Logs 'app deactivate' App Event.
		AppEventsLogger.deactivateApp(this);
	}
}
