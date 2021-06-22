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
//後臺用
	public void insertAccountInfo(
			String accountMail,String accountNickname,String accountPassword,Boolean accountState,Integer accountLevel,
			String accountName,Integer accountGender,Date accountBirth,String accountPhone,byte[] accountPic,
			byte[] accountIDcardFront,byte[] accountIDcardback,String accountText,String accountRegisterTime
			) {
		
		
	}
	
	public AccountInfoVO selectOneAccountInfo(Integer accountID) {
		return dao.selectOneAccountInfo(accountID);
	}
	
	public List<AccountInfoVO> selectAllAccountInfo(){
		return dao.selectAllAccountInfo();
	}
	
	
	
	
	
	
	
	
	public AccountInfoVO addAccountInfo(
//			Integer accountID,
			String accountMail,String accountNickname,String accountPassword,Boolean accountState,Integer accountLevel,
			String accountName,Integer accountGender,Date accountBirth,String accountPhone,byte[] accountPic,
			byte[] accountIDcardFront,byte[] accountIDcardback,String accountText,String accountRegisterTime
			){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime localDateTime = LocalDateTime.parse(accountRegisterTime,formatter);
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
		accountInfoVO.setAccountRegisterTime(timestampdate);
		
		dao.insert(accountInfoVO);
		
		return accountInfoVO;
	}
	
	public AccountInfoVO updateAccountInfoVO(
//			Integer accountID,
			String accountMail,String accountNickname,String accountPassword,Boolean accountState,Integer accountLevel,
			String accountName,Integer accountGender,Date accountBirth,String accountPhone,byte[] accountPic,
			byte[] accountIDcardFront,byte[] accountIDcardback,String accountText,String accountRegisterTime
			){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime localDateTime = LocalDateTime.parse(accountRegisterTime,formatter);
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
		accountInfoVO.setAccountRegisterTime(timestampdate);
		
		dao.update(accountInfoVO);
		
		return accountInfoVO;
	}
	public void deleteAccountInfo(Integer accountID) {
		dao.delete(accountID);
	}
	

	

//=====================================================
//review
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
	public AccountInfoVO getAccountIDByAccountMail(String accountMail){
		return dao.getAccountIDByAccountMail(accountMail);
	}
//會員修改資料用
	public void updateAccountInfoFromChange(
			String accountMail,String accountNickname,String accountPassword,
			String accountName,Integer accountGender,Date accountBirth,String accountPhone,
			String accountText,
			Integer accountID
		){
		AccountInfoVO accountInfoVO = new AccountInfoVO();
	//	accountInfoVO.setAccountID(accountID);
	
		accountInfoVO.setAccountMail(accountMail);
		accountInfoVO.setAccountNickname(accountNickname);
		accountInfoVO.setAccountPassword(accountPassword);
//		accountInfoVO.setAccountState(new Boolean("1"));
//		accountInfoVO.setAccountLevel(new Integer(1));
		
		accountInfoVO.setAccountName(accountName);
		accountInfoVO.setAccountGender(accountGender);
		accountInfoVO.setAccountBirth(accountBirth);
		accountInfoVO.setAccountPhone(accountPhone);
//		accountInfoVO.setAccountPic(null);
		
//		accountInfoVO.setAccountIDcardFront(null);
//		accountInfoVO.setAccountIDcardBack(null);
		accountInfoVO.setAccountText(accountText);
		
		dao.updateAccountInfoFromChange(
				accountMail,accountNickname,accountPassword,
				accountName,accountGender,accountBirth,accountPhone,
				accountText,accountID);
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
