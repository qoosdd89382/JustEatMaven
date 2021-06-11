package com.friendship.model;

import java.io.Serializable;

//������2021.0521.1000
//none

//table Friendship
public class FriendshipVO implements Serializable{
//	private static final long serialVersionUID = 1L;
	private Integer accountID;
	private Integer friendID;
	private Integer friendshipState;
	
	public FriendshipVO() {
		super();
	}
	
	public Integer getAccountID() {
		return accountID;
	}
	public void setAccountID(Integer accountID) {
		this.accountID = accountID;
	}
	public Integer getFriendID() {
		return friendID;
	}
	public void setFriendID(Integer friendID) {
		this.friendID = friendID;
	}
	public Integer getFriendshipState() {
		return friendshipState;
	}
	public void setFriendshipState(Integer friendshipState) {
		this.friendshipState = friendshipState;
	}
}
