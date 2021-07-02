package com.evaluatedmember.model;

import java.util.List;

public class EvaluatedMemberService {
	private EvaluatedMemberJDBCDAO dao;
	
	public EvaluatedMemberService() {
		dao = new EvaluatedMemberJDBCDAO();
	}
	
	public EvaluatedMemberVO addEvaluatedMember(int accepterAccountID,int giverAccountID,int eventID,int giveScore) {
		EvaluatedMemberVO evaluatedMemberVO = new EvaluatedMemberVO();
		evaluatedMemberVO.setAccepterAccountID(accepterAccountID);
		evaluatedMemberVO.setGiverAccountID(giverAccountID);
		evaluatedMemberVO.setEventID(eventID);
		evaluatedMemberVO.setGiveScore(giveScore);
//		evaluatedMemberVO.setGiveScore(eventID);
		dao.insert(evaluatedMemberVO);
		return evaluatedMemberVO;
	}
	
	public EvaluatedMemberVO updateEvaluatedMember(int accepterAccountID,int giverAccountID,int eventID,int giveScore) {
		EvaluatedMemberVO evaluatedMemberVO = new EvaluatedMemberVO();
		evaluatedMemberVO.setAccepterAccountID(accepterAccountID);
		evaluatedMemberVO.setGiverAccountID(giverAccountID);
		evaluatedMemberVO.setEventID(eventID);
		evaluatedMemberVO.setGiveScore(giveScore);
		dao.update(evaluatedMemberVO);
		return evaluatedMemberVO;
	}
	
	public List<EvaluatedMemberVO> getAll(){
		return dao.getAll();
	}
	

	public List<EvaluatedMemberVO> getAllByEventID(Integer eventID){
		return dao.getAllByEventID(eventID);
	}

}
