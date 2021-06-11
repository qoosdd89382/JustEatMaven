package com.evaluatedmember.model;

import java.util.List;

public interface EvaluatedMemberDAOInterface {
	public void insert(EvaluatedMemberVO evaluatedMemberVO);
	public void update(EvaluatedMemberVO evaluatedMemberVO);
//	public void delete();
	public List<EvaluatedMemberVO> getAll();
}
