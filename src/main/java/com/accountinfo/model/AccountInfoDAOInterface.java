package com.accountinfo.model;

import java.util.*;

public interface AccountInfoDAOInterface {
//後臺用
    public void insert(AccountInfoVO accountInfoVO);
    public void update(AccountInfoVO accountInfoVO);
    public void delete(Integer accountID);
    public AccountInfoVO selectOneAccountInfo(Integer accountID);
    public List<AccountInfoVO> selectAllAccountInfo();

//登入用
    public AccountInfoVO getAccountMailPasswordForLogin(String accountMail,String accountPassword);
    public AccountInfoVO getAccountMail(String accountMail);
    public AccountInfoVO getAccountNickname(String accountNickname);
    public AccountInfoVO getAccountPassword(String accountPassword);
    public AccountInfoVO getAccountIDByAccountMail(String accountMail);
	public AccountInfoVO getAccountCodeByAccountMail(String accountMail);

    
//會員修改資料用
    public void updateAccountInfoFromChange(AccountInfoVO accountInfoVO);
//註冊用
    public void setBlankAccountInfoFromRegister(AccountInfoVO accountInfoVO);
    
    public void setLevelOneAccountInfoFromRegister(AccountInfoVO accountInfoVO);
    
    //暫時沒用===
    public void setLevelTwoAccountInfoFromRegister(
			String accountMail,String accountNickname,String accountPassword,
			String accountName,Integer accountGender,Date accountBirth,String accountPhone,
			String accountText
    		);
    
    public void setLevelThreeAccountInfoFromRegister(AccountInfoVO accountInfoVO);
    
}
