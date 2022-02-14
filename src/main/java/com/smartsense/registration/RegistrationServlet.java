package com.smartsense.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    @Override
    	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		// TODO Auto-generated method stub
//    		super.doGet(request, response);
    		
    		String uname = request.getParameter("name");
    		String upasswd = request.getParameter("pass");
    		String uemail = request.getParameter("email");
    		String ucontact = request.getParameter("contact");
    		Connection con = null;
    		RequestDispatcher reqDispatcher = null;
    		
    		
//    		PrintWriter out = response.getWriter();
//    		out.print(uname);
//    		out.print(upasswd);
//    		out.print(uemail);
//    		out.print(ucontact);
    		
    		try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/customer", "root", "welcome123");
				PreparedStatement statement = con.prepareStatement("insert into users(uname, upasswd, uemail, ucontact) values (?,?,?,?)");
				statement.setString(1, uname);
    			statement.setString(2, upasswd);
    			statement.setString(3, uemail);
    			statement.setString(4, ucontact);
    			
    			int rowCount = statement.executeUpdate();
    			
    			reqDispatcher = request.getRequestDispatcher("registration.jsp");
    			
    			if (rowCount>0) {
    				request.setAttribute("status", "success");
					
				} else {
					request.setAttribute("status", "failed");
				}
    			
				reqDispatcher.forward(request, response);
    			
			} 
    		
    		catch (Exception e) {
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
