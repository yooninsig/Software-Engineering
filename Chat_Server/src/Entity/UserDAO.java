package Entity;
// 유저 관련 DB와 연결되는 클래스.

import java.sql.*;

public class UserDAO {

	int loginTry(String id, String pw) {
		
		int regNum = 0;
		
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		String url ="jdbc:mysql://localhost:3306/chatdb";
		String uid ="root";
		String upwd ="mysql";
		String sql ="select userpassword from User where userid = ?";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, uid, upwd);
			pst = conn.prepareStatement(sql);
			pst.setString(1, id);
			
			rs = pst.executeQuery();
			
			if(rs.next()) {
				String dbPw = rs.getString("UserPassword");
				
				if(dbPw.equals(pw)) {
					regNum = 1; //성공
				} else {
					regNum = 3; //비번
				}
			} else {
				regNum = 2; //아이디
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn != null) conn.close();
				if(pst != null) pst.close();
				if(rs != null) rs.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		
		return regNum;
	}
	
	public String findSchool(String id) {
		
		String regSchool = "";
		
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		String url ="jdbc:mysql://localhost:3306/chatdb";
		String uid ="root";
		String upwd ="mysql";
		String sql ="select userSchool from User where userid = ?";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, uid, upwd);
			pst = conn.prepareStatement(sql);
			pst.setString(1, id);
			
			rs = pst.executeQuery();
			
			if(rs.next()) {
				String dbSchool = rs.getString("UserSchool");
				regSchool = dbSchool;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn != null) conn.close();
				if(pst != null) pst.close();
				if(rs != null) rs.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return regSchool;
	}
}
