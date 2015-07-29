package com.moses.util;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;

public abstract class BaseListElement {
    private int icon;
    private String text;
    private int requestCode;
    private String fbId;
    private BaseAdapter adapter;

    public abstract View.OnClickListener getOnClickListener();

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getText1() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        return text;
    }

    public void setText(String text) {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        this.text = text;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public BaseAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
    }

    public BaseListElement(int icLauncher, String text, int requestCode, String fbId) {
        super();
        this.icon = icLauncher;
        this.text = text;
        this.requestCode = requestCode;
        this.fbId = fbId;
    }

    public void onActivityResult(Intent data) {
    }

    public void onSaveInstanceState(Bundle bundle) {
    }

    public boolean restoreState(Bundle savedState) {
        return false;
    }

    protected void notifyDataChanged() {
        adapter.notifyDataSetChanged();
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

}
