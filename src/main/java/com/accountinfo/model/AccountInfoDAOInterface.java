package com.accountinfo.model;

import java.util.*;

public interface AccountInfoDAOInterface {
//後臺用
    //CRUD
	public void insert(AccountInfoVO accountInfoVO);
    public void update(AccountInfoVO accountInfoVO);
    public void delete(Integer accountID);
    public AccountInfoVO selectOneAccountInfo(Integer accountID);
    public List<AccountInfoVO> selectAllAccountInfo();
    //停權
    public void freezeAccountInfo(Integer accountID);
    public void activeAccountInfo(Integer accountID);


//登入用
    //查詢該帳號密碼取得所有資料
    public AccountInfoVO getAccountMailPasswordForLogin(String accountMail,String accountPassword);
    //基本找資料
    public AccountInfoVO getAccountMail(String accountMail);
    public AccountInfoVO getAccountNickname(String accountNickname);
    //用信箱找資料
    public AccountInfoVO getAccountIDByAccountMail(String accountMail);
    public AccountInfoVO getAccountPasswordByAccountMail(String accountMail);
	public AccountInfoVO getAccountCodeByAccountMail(String accountMail);

    
//會員修改資料用
    public void updateAccountInfoFromChange(AccountInfoVO accountInfoVO);
//會員忘記密碼更改驗證碼
	public void updateAccountCodeFromForget(AccountInfoVO accountInfoVO);
//註冊用
	//寄驗證信會產生會員 管理員要手動消除
    public void setBlankAccountInfoFromRegister(AccountInfoVO accountInfoVO);
    //設定基本會員
    public void setLevelOneAccountInfoFromRegister(AccountInfoVO accountInfoVO);
    
    //暫時沒用===
    public void setLevelTwoAccountInfoFromRegister(
			String accountMail,String accountNickname,String accountPassword,
			String accountName,Integer accountGender,Date accountBirth,String accountPhone,
			String accountText
    		);
    //設定進階會員
    public void setLevelThreeAccountInfoFromRegister(AccountInfoVO accountInfoVO);
    
}
