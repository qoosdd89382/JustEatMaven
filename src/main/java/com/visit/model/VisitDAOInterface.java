package com.visit.model;

import java.util.List;

import com.visit.model.VisitVO;

public interface VisitDAOInterface {
    public void insert(VisitVO visitVO);
    public void update(VisitVO visitVO);
    public void delete(Integer visitID);
    public VisitVO findByPrimaryKey(Integer visitID);
    public List<VisitVO> getAll();
}
