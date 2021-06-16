package com.friendship.model;

import java.util.List;

import com.accountinfo.model.AccountInfoVO;

public class FriendshipService {
	private FriendshipDAOInterface dao;
	
	public FriendshipService() {
		dao = new FriendshipJDBCDAO();
	}
	
	public FriendshipVO getAccountFriendship(Integer accountID) {
		return dao.getAccountFriendship(accountID);
	}

	public List<AccountInfoVO> getAccountFriendByAccountMail(String account_mail) {
		return dao.getAccountFriendByAccountMail(account_mail);
	}
	
}
