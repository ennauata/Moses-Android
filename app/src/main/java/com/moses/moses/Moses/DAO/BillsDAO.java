package com.moses.moses.Moses.DAO;

import android.util.Log;

import com.moses.moses.Moses.Bin.BillBin;
import com.moses.moses.Moses.Utils.Callback;
import com.moses.moses.Moses.Utils.WebServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class BillsDAO {
	private static BillsDAO billInstance;
	private static ArrayList<BillBin> bills;
	private String response;
	private static WebServiceHandler billWebHandler;
	private Callback billDaoCallback;

	public static BillsDAO getBillInstance() {
		return billInstance;
	}

	public static void setBillInstance(BillsDAO billInstance) {
		BillsDAO.billInstance = billInstance;
	}

	public static WebServiceHandler getBillWebHandler() {
		return billWebHandler;
	}

	public static void setBillWebHandler(WebServiceHandler billWebHandler) {
		BillsDAO.billWebHandler = billWebHandler;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public static ArrayList<BillBin> getBills() {
		return bills;
	}

	public static void setBills(ArrayList<BillBin> bills) {
		BillsDAO.bills = bills;
	}

	public static BillsDAO getInstance() {
		if (billInstance == null) {
			billInstance = new BillsDAO();
			bills = new ArrayList<BillBin>();
			billWebHandler = new WebServiceHandler();
		}
		return billInstance;
	}

	public void requestBills() throws IOException, JSONException {
		billWebHandler.setGetRequestCallback(new Callback() {

			@Override
			public void callbackCall() {
				Log.i("Proc", response);
				try {
					bills = new ArrayList<BillBin>();
					JSONObject jResponse = new JSONObject(response);
					JSONArray results = new JSONArray();

					results = jResponse.getJSONArray("results");
					for (int i = 0; i < results.length(); i++) {

						// String[] userIds;
						// float[] uservalues;
						JSONObject jBill = new JSONObject();
						jBill = (JSONObject) results.get(i);

						String name = jBill.getString("name");
						String description = jBill.getString("description");
						String groupId = jBill.getString("group");
						float amount = (float) jBill.getDouble("amount");
						String duedate = jBill.getString("deadline");

						BillBin bill = new BillBin(null, null, name,
								description, groupId, amount, duedate);

						bills.add(bill);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		billWebHandler.sendGet("http://mosesapp.me/bill_user/"
				+ UserDAO.getInstance().getUserServiceInfo().getString("id"),
				"requestBill");
	}
	
	public void createBill(BillBin bill) throws IOException{
		BillsDAO.getBillWebHandler().setPostRequestCallback(new Callback() {
	
			@Override
			public void callbackCall() {
				BillsDAO.getInstance().getBillDaoCallback().callbackCall();
				Log.i("Proc", getResponse().toString());
			}
		});
		billWebHandler.setBill(bill);
		billWebHandler.sendPost("http://mosesapp.me/bill/",
				"createBill");	
	}

	public Callback getBillDaoCallback() {
		return billDaoCallback;
	}

	public void setBillDaoCallback(Callback billDaoCallback) {
		this.billDaoCallback = billDaoCallback;
	}
}
