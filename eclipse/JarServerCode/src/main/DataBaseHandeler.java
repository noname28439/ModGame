package main;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TimeZone;

import settings.Settings;

public class DataBaseHandeler {

	static Connection conn = null;
	
	public static String sha256(String base) {
	    try{
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hash = digest.digest(base.getBytes("UTF-8"));
	        StringBuffer hexString = new StringBuffer();

	        for (int i = 0; i < hash.length; i++) {
	            String hex = Integer.toHexString(0xff & hash[i]);
	            if(hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }

	        return hexString.toString();
	    } catch(Exception ex){
	       throw new RuntimeException(ex);
	    }
	}
	
	public static void load() {
		try {
			
			
			Class.forName("org.sqlite.JDBC");
			
			String path = "jdbc:sqlite:file:"+Settings.userDB_file_Path + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=" + TimeZone.getDefault().getID();
			System.out.println(path);
			conn = DriverManager.getConnection(path);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	 
	public static ArrayList<SQLResult> read() {
		try {
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM users";
		
			ResultSet res = stmt.executeQuery(sql);
					
			ArrayList<SQLResult> sqlResults = new ArrayList<>();
			
			while (res.next()) {
				sqlResults.add(new SQLResult(res.getString(1), res.getString(2), res.getString(3)));
			}
			
			for(int i = 0; i<sqlResults.size();i++) {
				System.err.println("READ --> "+sqlResults.get(i).controllString());
			}
			
			return sqlResults;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	  public static int checkPlayerAccess(String name, String pw) {
			try {
				Statement stmt = conn.createStatement();
				String sql = "SELECT * FROM users WHERE username='"+name+"'";
			
				ResultSet res = stmt.executeQuery(sql);
						
				ArrayList<SQLResult> sqlResults = new ArrayList<>();
				
				boolean found = false;
				
				String foundName;
				String foundPw = null;
				
				while (res.next()) {
					found = true;
					foundName = res.getString(1);
					foundPw = res.getString(2);
				}
				
				if(!found) {
					return 2;
				}else {
					if(sha256(pw).equalsIgnoreCase(foundPw)) {
						return 0;
					}else {
						return 1;
					}
				}
				
				
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return 187;
		}
	  
	
	
}


class SQLResult {
	
	public String username, password, email;
	
	public SQLResult(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;	
	}
	
	public String controllString() {
		return "SQL-Result["+"username:"+username+"|password:"+password+"|email:"+email+"]";
	}
	
}
