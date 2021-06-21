package com.accountinfo.model;

import java.util.*;

public interface AccountInfoDAOInterface {
//測試會員資料CRUD
    public void insert(AccountInfoVO accountInfoVO);
    public void update(AccountInfoVO accountInfoVO);
    public void delete(Integer accountID);
    public AccountInfoVO findByPrimaryKey(Integer accountID);
    public List<AccountInfoVO> getAll();
    
//登入用
    public AccountInfoVO getAccountLogin(String accountMail,String accountPassword);
    public AccountInfoVO getAccountMail(String accountMail);
    public AccountInfoVO getAccountPassword(String accountPassword);
//註冊用
    public void setLevelOneAccountInfoFromRegister(
			String accountMail,String accountNickname,String accountPassword,
			String accountName,Integer accountGender,Date accountBirth,String accountPhone,
			String accountText
    		);
 
}
