package com.announce.model;

import java.util.*;

import com.announce.model.AnnounceVO;

public interface AnnounceDAOInterface {
    public int insert(AnnounceVO announceVO);
    public void update(AnnounceVO announceVO);
    public void delete(Integer announceID);
    public AnnounceVO findByPrimaryKey(Integer announceID);
    public List<AnnounceVO> getAll();
    
    public List<AnnounceVO> getAnnounce();
}
