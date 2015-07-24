package com.moses.moses.Moses.Bin;

public class BillBin {
	String[] userIds;
	float[] uservalues;
	String name;
	String description;
	String groupId;
	String duedate;

	public String getDuedate() {
		return duedate;
	}

	public void setDuedate(String duedate) {
		this.duedate = duedate;
	}

	public BillBin(String[] userIds, float[] uservalues, String name,
			String description, String groupId, float amount, String duedate) {
		super();
		this.userIds = userIds;
		this.uservalues = uservalues;
		this.name = name;
		this.description = description;
		this.groupId = groupId;
		this.amount = amount;
		this.duedate = duedate;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	float amount;

	public String[] getUserIds() {
		return userIds;
	}

	public void setUserIds(String[] userIds) {
		this.userIds = userIds;
	}

	public float[] getUservalues() {
		return uservalues;
	}

	public void setUservalues(float[] uservalues) {
		this.uservalues = uservalues;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

}
