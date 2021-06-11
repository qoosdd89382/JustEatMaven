package com.adminchatroom.model;

import java.util.List;

public interface AdminChatroomDAOInterface {
	
	public boolean insert(AdminChatroomVO adminChatroom);
	public List<AdminChatroomVO> getAllByText(int accountID, int adminID, String sendText);
	public List<AdminChatroomVO> getAllByMembers(int accountID, int adminID);
	
}
