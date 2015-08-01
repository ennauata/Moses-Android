package com.moses.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.moses.adapter.NavBarAdapter;
import com.moses.R;

public class ConfigurationActivity extends Activity {
    // Nav Bar icons
    int[] mThumbIds = {R.drawable.icons_home_gray,
            R.drawable.icons_group_gray, R.drawable.icons_bill_gray,
            R.drawable.icons_configuration_blue};
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
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
