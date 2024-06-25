package com.example.smsautoresponder;

public class Contact {
	private int R_id;
	private String R_number;
	private String R_contact;

	
	public Contact() {
		super();
		this.R_id = 0;
		this.R_contact = "";
		this.R_number = "";
		
	}

	public Contact(int R_id, String R_number, String R_contact) {
		super();
		this.R_id = R_id;
		this.R_number =R_number;
		this.R_contact = R_contact;

	}
     public int getResponder_id() {
		return this.R_id;
	}

	public void setResponder_id(int R_id) {
		this.R_id = R_id;
	}

	public String getResponder_number() {
		return this.R_number;
	}

	public void setResponder_number(String R_number) {
		this.R_number = R_number;
	}

	public String getResponder_contact() {
		return this.R_contact;
	}

	public void setResponder_contact(String R_contact) {
		this.R_contact = R_contact;
	}
}

