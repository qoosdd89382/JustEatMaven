package com.friendship.model;

import java.util.*;

import com.friendship.model.FriendshipVO;

public interface FriendshipDAOInterface {
    public void insert(FriendshipVO friendshipVO);
    public void update(FriendshipVO friendshipVO);
    public void delete(Integer friendshipID);
    public FriendshipVO findByPrimaryKey(Integer friendshipID);
    public List<FriendshipVO> getAll();
}
