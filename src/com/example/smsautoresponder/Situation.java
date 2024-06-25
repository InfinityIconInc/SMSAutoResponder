package com.example.smsautoresponder;

public class Situation {
	private int S_id;
	private String S_msg;
	private String S_name;
	private int iActive;

	public Situation() {
		super();
		this.S_id = 0;
		this.S_msg = "";
		this.S_name = "";
		this.iActive = 0;

	}

	public Situation(int S_id, String S_name, String S_msg, int iActive) {
		super();
		this.S_id = S_id;
		this.S_msg = S_msg;
		this.S_name = S_name;
		this.iActive = iActive;
	}

	public int getSituation_id() {
		return S_id;
	}

	public void setSituation_id(int S_id) {
		this.S_id = S_id;
	}

	public String getSituation_msg() {
		return S_msg;
	}

	public void setSituation_msg(String S_msg) {
		this.S_msg = S_msg;
	}

	public String getSituation_name() {
		return S_name;
	}

	public void setSituation_name(String S_name) {
		this.S_name = S_name;
	}

	public int getiActive() {
		return iActive;
	}

	public void setiActive(int iActive) {
		this.iActive = iActive;
	}	

}
