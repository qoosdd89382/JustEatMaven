package com.accountinfo.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.eventinfo.model.EventInfoVO;

public class AccountInfoService {
	private AccountInfoDAOInterface dao;
	
	public AccountInfoService() {
		dao = new AccountInfoJDBCDAO();
	}
	
	public AccountInfoVO addAccountInfo(
//			Integer accountID,
			String accountMail,String accountNickname,String accountPassword,Boolean accountState,Integer accountLevel,
			String accountName,Integer accountGender,Date accountBirth,String accountPhone,byte[] accountPic,
			byte[] accountIDcardFront,byte[] accountIDcardback,String accountText,String accountRegistTime
			){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime localDateTime = LocalDateTime.parse(accountRegistTime,formatter);
		Timestamp timestampdate = Timestamp.valueOf(localDateTime);
		
		AccountInfoVO accountInfoVO = new AccountInfoVO();
//		accountInfoVO.setAccountID(accountID);

		accountInfoVO.setAccountMail(accountMail);
		accountInfoVO.setAccountNickname(accountNickname);
		accountInfoVO.setAccountPassword(accountPassword);
		accountInfoVO.setAccountState(accountState);
		accountInfoVO.setAccountLevel(accountLevel);
		
		accountInfoVO.setAccountName(accountName);
		accountInfoVO.setAccountGender(accountGender);
		accountInfoVO.setAccountBirth(accountBirth);
		accountInfoVO.setAccountPhone(accountPhone);
		accountInfoVO.setAccountPic(accountPic);
		
		accountInfoVO.setAccountIDcardFront(accountIDcardFront);
		accountInfoVO.setAccountIDcardBack(accountIDcardback);
		accountInfoVO.setAccountText(accountText);
		accountInfoVO.setAccountRegistTime(timestampdate);
		
		dao.insert(accountInfoVO);
		
		return accountInfoVO;
	}
	
	public AccountInfoVO updateAccountInfoVO(
//			Integer accountID,
			String accountMail,String accountNickname,String accountPassword,Boolean accountState,Integer accountLevel,
			String accountName,Integer accountGender,Date accountBirth,String accountPhone,byte[] accountPic,
			byte[] accountIDcardFront,byte[] accountIDcardback,String accountText,String accountRegistTime
			){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime localDateTime = LocalDateTime.parse(accountRegistTime,formatter);
		Timestamp timestampdate = Timestamp.valueOf(localDateTime);
		
		AccountInfoVO accountInfoVO = new AccountInfoVO();
//		accountInfoVO.setAccountID(accountID);

		accountInfoVO.setAccountMail(accountMail);
		accountInfoVO.setAccountNickname(accountNickname);
		accountInfoVO.setAccountPassword(accountPassword);
		accountInfoVO.setAccountState(accountState);
		accountInfoVO.setAccountLevel(accountLevel);
		
		accountInfoVO.setAccountName(accountName);
		accountInfoVO.setAccountGender(accountGender);
		accountInfoVO.setAccountBirth(accountBirth);
		accountInfoVO.setAccountPhone(accountPhone);
		accountInfoVO.setAccountPic(accountPic);
		
		accountInfoVO.setAccountIDcardFront(accountIDcardFront);
		accountInfoVO.setAccountIDcardBack(accountIDcardback);
		accountInfoVO.setAccountText(accountText);
		accountInfoVO.setAccountRegistTime(timestampdate);
		
		dao.update(accountInfoVO);
		
		return accountInfoVO;
	}
	public void deleteAccountInfo(Integer accountID) {
		dao.delete(accountID);
	}
	
	public AccountInfoVO getAccountID(Integer accountID) {
		return dao.findByPrimaryKey(accountID);
	}
	
	public List<AccountInfoVO> getAll(){
		return dao.getAll();
	}
	
//登入用
	public AccountInfoVO getAccountInfo(String accountMail,String accountPassword) {
		return dao.getAccountLogin(accountMail,accountPassword);
	}
	
	public AccountInfoVO getAccountMail(String accountMail) {
		return dao.getAccountMail(accountMail);
	}
	public AccountInfoVO getAccountPassword(String accountMail) {
		return dao.getAccountPassword(accountMail);
	}
//註冊用
	public void setLevelOneAccountInfoFromRegister(
//		Integer accountID,
		String accountMail,String accountNickname,String accountPassword,
		String accountName,Integer accountGender,Date accountBirth,String accountPhone,
		String accountText
		){
		AccountInfoVO accountInfoVO = new AccountInfoVO();
	//	accountInfoVO.setAccountID(accountID);
	
		accountInfoVO.setAccountMail(accountMail);
		accountInfoVO.setAccountNickname(accountNickname);
		accountInfoVO.setAccountPassword(accountPassword);
		accountInfoVO.setAccountState(new Boolean("1"));
		accountInfoVO.setAccountLevel(new Integer(1));
		
		accountInfoVO.setAccountName(accountName);
		accountInfoVO.setAccountGender(accountGender);
		accountInfoVO.setAccountBirth(accountBirth);
		accountInfoVO.setAccountPhone(accountPhone);
		accountInfoVO.setAccountPic(null);
		
		accountInfoVO.setAccountIDcardFront(null);
		accountInfoVO.setAccountIDcardBack(null);
		accountInfoVO.setAccountText(accountText);
		
		dao.setLevelOneAccountInfoFromRegister(
				accountMail,accountNickname,accountPassword,
				accountName,accountGender,accountBirth,accountPhone,
				accountText);
	}
	
	
}
