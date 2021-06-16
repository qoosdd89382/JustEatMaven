package com.friendship.model;

public class FriendshipService {
	private FriendshipDAOInterface dao;
	
	public FriendshipService() {
		dao = new FriendshipJDBCDAO();
	}
	
	public FriendshipVO getAccountFriendship(Integer accountID) {
		return dao.getAccountFriendship(accountID);
	}

}
