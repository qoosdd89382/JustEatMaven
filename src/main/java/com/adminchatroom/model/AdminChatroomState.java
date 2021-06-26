package com.adminchatroom.model;

import java.util.Set;

public class AdminChatroomState {
	
	private String type;
	// the user changing the state
	private String userID;
	// total users
	private Set<String> userIDs;
//	private String adminID;
//	private Set<String> adminIDs;

	public AdminChatroomState(String type,  String userID, Set<String> userIDs) {
//								String adminID, Set<String> adminIDs){
		super();
		this.type = type;
		this.userID = userID;
		this.userIDs = userIDs;
//		this.adminID = adminID;
//		this.adminIDs = adminIDs;
		
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
//
//	public String getAdminID() {
//		return adminID;
//	}
//
//	public void setAdminID(String adminID) {
//		this.adminID = adminID;
//	}

	public String getuserID() {
		return userID;
	}

	public void setuserID(String userID) {
		this.userID = userID;
	}
//
//	public Set<String> getAdminIDs() {
//		return adminIDs;
//	}
//
//	public void setAdminIDs(Set<String> adminIDs) {
//		this.adminIDs = adminIDs;
//	}

	public Set<String> getuserIDs() {
		return userIDs;
	}

	public void setuserIDs(Set<String> userIDs) {
		this.userIDs = userIDs;
	}
	
	

}
