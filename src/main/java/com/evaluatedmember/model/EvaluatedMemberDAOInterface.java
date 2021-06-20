package com.evaluatedmember.model;

import java.util.List;

import com.eventmember.model.EventMemberVO;

public interface EvaluatedMemberDAOInterface {
	public void insert(EvaluatedMemberVO evaluatedMemberVO);
	public void update(EvaluatedMemberVO evaluatedMemberVO);
//	public void delete();
	public List<EvaluatedMemberVO> getAll();
	public List<EvaluatedMemberVO> getAllByEventID(Integer eventID);
}
