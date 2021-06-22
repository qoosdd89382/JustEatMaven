package com.admininfo.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/TestBCrypt")
public class TestBCrypt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// Hash a password for the first time
		String password = "111111";

		// gensalt's log_rounds parameter determines the complexity
		// the work factor is 2**log_rounds, and the default is 10
		String hashed = BCrypt.hashpw(password, BCrypt.gensalt());	// BCrypt.hashpw(password, BCrypt.gensalt(12));
		System.out.println(hashed);
		
		// Check that an unencrypted password matches one that has
		// previously been hashed
		if (BCrypt.checkpw(password, hashed))	// BCrypt.checkpw(password, BCrypt.hashpw(password, BCrypt.gensalt())
			System.out.println("It matches");
		else
			System.out.println("It does not match");
	}

}
