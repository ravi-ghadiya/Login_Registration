package com.smartsense.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LognServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uemail = request.getParameter("email");
		String upasswd = request.getParameter("password");
		Connection con = null;
		HttpSession session = null;
		RequestDispatcher dispatcher = null;
	
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/customer?useSSL=false", "root", "welcome123");
			PreparedStatement stmt = con.prepareStatement("select * from users where uemail=? and upasswd=?");
			stmt.setString(1, uemail);
			stmt.setString(2, upasswd);
			
			ResultSet rs = stmt.executeQuery();
			
			
			if (rs.next()) {
				session = request.getSession();
				session.setAttribute("uname", rs.getString("uname"));
				dispatcher = request.getRequestDispatcher("index.jsp");
				
			} else {
				request.setAttribute("loginStatus", "failed");
				dispatcher = request.getRequestDispatcher("login.jsp");
			}
			
			dispatcher.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
