package com.yoku.yokuWebsiteHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "notify" path)
 */
@Path("notify")
@Singleton()
public class NotifyApi {
	
	private static String mysql_pass = null;
	
	public NotifyApi(String pass) {
		setMySqlPassword(pass);
	}
	
	public static void setMySqlPassword(String pass) {
		mysql_pass = pass;
	}

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to
	 * the client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String notify(@QueryParam("email") String email) {
		
		// can't do anything without password
		if (mysql_pass == null) {
			return "failed";
		}
		
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection(
					"jdbc:mysql://yokudbinstance.cqduivf0dksm.us-east-1.rds.amazonaws.com:3306/yoku_website", 
					"yokuadmin", "yoku2015!");

			Statement stmt = con.createStatement();

			stmt.executeUpdate("insert into tbl_notify values (\"" + email + "\")");

			con.close();

		} catch (Exception e) {
			System.out.println(e);
			return "failed";
		}
		
		return "success";
	}
}