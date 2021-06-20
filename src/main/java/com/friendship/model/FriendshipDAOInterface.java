package com.friendship.model;

import java.util.*;

import com.accountinfo.model.AccountInfoVO;
import com.friendship.model.FriendshipVO;

public interface FriendshipDAOInterface {
    public void insert(FriendshipVO friendshipVO);
    public void update(FriendshipVO friendshipVO);
    public void delete(Integer friendshipID);
    public FriendshipVO findByPrimaryKey(Integer friendshipID);
    public List<FriendshipVO> getAll();
//好友頁面用
    public FriendshipVO getAccountFriendship(Integer accountID);
    public List<AccountInfoVO> getAccountFriendByAccountMail(String account_mail);
}
