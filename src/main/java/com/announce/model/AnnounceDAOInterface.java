package com.announce.model;

import java.util.*;

import com.announce.model.AnnounceVO;

public interface AnnounceDAOInterface {
    public void insert(AnnounceVO announceVO);
    public void update(AnnounceVO announceVO);
    public void delete(Integer announceID);
    public AnnounceVO findByPrimaryKey(Integer announceID);
    public List<AnnounceVO> getAll();
}
