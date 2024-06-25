package com.example.smsautoresponder;

public class ResponderHistory {
	private int iResponderHistoryID;
	private String strResponderHistoryDate;
	private String strResponderHistoryTime;
	private String strResponderHistoryMessage;
	private String strResponderHistoryRespondedMessage;
	private String strResponderHistoryNumber;

	public ResponderHistory(int iResponderHistoryID,
			String strResponderHistoryDate, String strResponderHistoryTime,
			String strResponderHistoryMessage,
			String strResponderHistoryRespondedMessage,
			String strResponderHistoryNumber) {
		super();
		this.iResponderHistoryID = iResponderHistoryID;
		this.strResponderHistoryDate = strResponderHistoryDate;
		this.strResponderHistoryTime = strResponderHistoryTime;
		this.strResponderHistoryMessage = strResponderHistoryMessage;
		this.strResponderHistoryRespondedMessage = strResponderHistoryRespondedMessage;
		this.strResponderHistoryNumber = strResponderHistoryNumber;
	}

	public int getiResponderHistoryID() {
		return iResponderHistoryID;
	}

	public void setiResponderHistoryID(int iResponderHistoryID) {
		this.iResponderHistoryID = iResponderHistoryID;
	}

	public String getStrResponderHistoryDate() {
		return strResponderHistoryDate;
	}

	public void setStrResponderHistoryDate(String strResponderHistoryDate) {
		this.strResponderHistoryDate = strResponderHistoryDate;
	}

	public String getStrResponderHistoryTime() {
		return strResponderHistoryTime;
	}

	public void setStrResponderHistoryTime(String strResponderHistoryTime) {
		this.strResponderHistoryTime = strResponderHistoryTime;
	}

	public String getStrResponderHistoryMessage() {
		return strResponderHistoryMessage;
	}

	public void setStrResponderHistoryMessage(String strResponderHistoryMessage) {
		this.strResponderHistoryMessage = strResponderHistoryMessage;
	}

	public String getStrResponderHistoryRespondedMessage() {
		return strResponderHistoryRespondedMessage;
	}

	public void setStrResponderHistoryRespondedMessage(
			String strResponderHistoryRespondedMessage) {
		this.strResponderHistoryRespondedMessage = strResponderHistoryRespondedMessage;
	}

	public String getStrResponderHistoryNumber() {
		return strResponderHistoryNumber;
	}

	public void setStrResponderHistoryNumber(String strResponderHistoryNumber) {
		this.strResponderHistoryNumber = strResponderHistoryNumber;
	}
}
