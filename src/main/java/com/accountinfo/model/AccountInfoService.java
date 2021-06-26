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
	
	public AccountInfoVO updateAccountInfo(
		String accountMail,String accountNickname,String accountPassword,Boolean accountState,Integer accountLevel,
		String accountName,Integer accountGender,Date accountBirth,String accountPhone,
		String accountText
		) {
		AccountInfoVO accountInfoVO = new AccountInfoVO();

		accountInfoVO.setAccountMail(accountMail);
		accountInfoVO.setAccountNickname(accountNickname);
		accountInfoVO.setAccountPassword(accountPassword);
		accountInfoVO.setAccountState(accountState);
		accountInfoVO.setAccountLevel(accountLevel);
		
		accountInfoVO.setAccountName(accountName);
		accountInfoVO.setAccountGender(accountGender);
		accountInfoVO.setAccountBirth(accountBirth);
		accountInfoVO.setAccountPhone(accountPhone);
		accountInfoVO.setAccountText(accountText);
		
		dao.update(accountInfoVO);
		
		return accountInfoVO;
	}
	
	public AccountInfoVO selectOneAccountInfo(Integer accountID) {
		return dao.selectOneAccountInfo(accountID);
	}
	
	public List<AccountInfoVO> selectAllAccountInfo(){
		return dao.selectAllAccountInfo();
	}
	
	public void deleteAccountInfo(Integer accountID) {
		dao.delete(accountID);
	}
	
//=====待修正
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
//=====待修正
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

	

	

//=====================================================

//登入用
	//傳入比對過的信箱密碼 回傳該帳號的所有資料
	public AccountInfoVO getAccountInfoForLogin(String accountMail,String accountPassword) {
		return dao.getAccountMailPasswordForLogin(accountMail,accountPassword);
	}
	//輸入信箱 回傳 帳號資料的 VO物件
	public AccountInfoVO getAccountMail(String accountMail) {
		return dao.getAccountMail(accountMail);
	}
	//輸入暱稱 回傳 帳號資料的 VO物件
	public AccountInfoVO getAccountNickname(String accountNickname) {
		return dao.getAccountNickname(accountNickname);
	}
	//輸入信箱 回傳該信箱帳號的 含密碼的VO物件 用來比對使用者的輸入資料是否正確
	public AccountInfoVO getAccountPassword(String accountMail) {
		return dao.getAccountPassword(accountMail);
	}
	public AccountInfoVO getAccountIDByAccountMail(String accountMail){
		return dao.getAccountIDByAccountMail(accountMail);
	}
	public AccountInfoVO getAccountCodeByAccountMail(String accountMail){
		return dao.getAccountCodeByAccountMail(accountMail);
	}
	
//review
//會員修改資料用
	public void updateAccountInfoFromChange(
			String accountPassword,String accountName,Integer accountGender,
			Date accountBirth,String accountPhone,byte[] accountPic,
			String accountText,Integer accountID){
		AccountInfoVO accountInfoVO = new AccountInfoVO();
		accountInfoVO.setAccountPassword(accountPassword);
		accountInfoVO.setAccountName(accountName);
		accountInfoVO.setAccountGender(accountGender);
		
		accountInfoVO.setAccountBirth(accountBirth);
		accountInfoVO.setAccountPhone(accountPhone);
		accountInfoVO.setAccountPic(accountPic);

		accountInfoVO.setAccountText(accountText);
		accountInfoVO.setAccountID(accountID);
		
		dao.updateAccountInfoFromChange(accountInfoVO);
	}
//註冊用
	//傳入 可用信箱 帳號 隨機驗證碼到資料庫儲存
	public AccountInfoVO setBlankAccountInfoFromRegister(String accountMail, String accountNickname, String accountCode) {
		AccountInfoVO accountInfoVO = new AccountInfoVO();
		accountInfoVO.setAccountMail(accountMail);
		accountInfoVO.setAccountNickname(accountNickname);
		accountInfoVO.setAccountCode(accountCode);
		dao.setBlankAccountInfoFromRegister(accountInfoVO);
		return accountInfoVO;
	}
	
	
	//傳入基本會員輸入的資料在VO物件往資料庫送
	public void setLevelOneAccountInfoFromRegister(
		String accountMail,String accountNickname,String accountPassword,String accountName,
		Integer accountGender,Date accountBirth,String accountText,Integer accountID){
		AccountInfoVO accountInfoVO = new AccountInfoVO();
		//傳入檢查的參數，並設定一般會員的數值
		accountInfoVO.setAccountMail(accountMail);
		accountInfoVO.setAccountNickname(accountNickname);
		accountInfoVO.setAccountPassword(accountPassword);
		accountInfoVO.setAccountName(accountName);
		accountInfoVO.setAccountGender(accountGender);
		accountInfoVO.setAccountBirth(accountBirth);
		accountInfoVO.setAccountText(accountText);
		accountInfoVO.setAccountID(accountID);
		dao.setLevelOneAccountInfoFromRegister(accountInfoVO);
	}
	
	public void setLevelTwoAccountInfoFromRegister(
//			Integer accountID,
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
			accountInfoVO.setAccountLevel(new Integer(2));
			
			accountInfoVO.setAccountName(accountName);
			accountInfoVO.setAccountGender(accountGender);
			accountInfoVO.setAccountBirth(accountBirth);
			accountInfoVO.setAccountPhone(accountPhone);
			accountInfoVO.setAccountPic(null);
			
			accountInfoVO.setAccountIDcardFront(null);
			accountInfoVO.setAccountIDcardBack(null);
			accountInfoVO.setAccountText(accountText);
			
			dao.setLevelTwoAccountInfoFromRegister(
					accountMail,accountNickname,accountPassword,
					accountName,accountGender,accountBirth,accountPhone,
					accountText);
		}
	
	public void setLevelThreeAccountInfoFromRegister(
			String accountPhone,
			byte[] accountPic,
			byte[] accountIDcardFront,
			byte[] accountIDcardBack,
			Integer accountID
			){
			AccountInfoVO accountInfoVO = new AccountInfoVO();
		//	accountInfoVO.setAccountID(accountID);
		

			accountInfoVO.setAccountLevel(new Integer(3));
			
			accountInfoVO.setAccountPhone(accountPhone);
			accountInfoVO.setAccountPic(accountPic);
			
			accountInfoVO.setAccountIDcardFront(accountIDcardFront);
			accountInfoVO.setAccountIDcardBack(accountIDcardBack);
			accountInfoVO.setAccountID(accountID);
			
			dao.setLevelThreeAccountInfoFromRegister(accountInfoVO);
					
	
	}
}
