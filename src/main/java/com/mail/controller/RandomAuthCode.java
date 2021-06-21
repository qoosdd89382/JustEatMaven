package com.mail.controller;

public class RandomAuthCode {
	
	public String generateCode(){

		// 把10個數字、26個大寫英文、26個小寫英文裝入陣列
		char[] allCode = new char[10 + 26 + 26];

		char number = 48;
		for(int i = 0; i < 10; i++) {
			allCode[i] = number;
			number++;
		}

		char ABC = 65;
		for(int i = 10; i < 10 + 26; i++) {
			allCode[i] = ABC;
			ABC++;
		}
		
		char abc = 97;
		for(int i = 10 + 26; i < 10 + 26 + 26; i++) {
			allCode[i] = abc;
			abc++;
		}
		
//		char[] authCode = new char[8];
		StringBuilder authCode = new StringBuilder(); 
		for(int i = 0; i < 8; i++) {
//			authCode[i] = allCode[(int)(Math.random()*(10+26+26))];
			authCode.append(allCode[(int)(Math.random()*(10+26+26))]);
		}
		
		return authCode.toString();
	}
	
	public static void main(String args[]) {
		System.out.println(new RandomAuthCode().generateCode());
	}
}
