package com.adminchatroom.model;

import java.util.List;

public interface AdminChatroomDAOInterface {
	
	public boolean insert(AdminChatroomVO adminChatroom);
	public List<AdminChatroomVO> getAllByText(int adminID, int accountID, String sendText);
	public List<AdminChatroomVO> getAllByMembers(int adminID, int accountID);
	
}
