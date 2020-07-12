package Entity;
// 친구 관련 DB와 연결되는 클래스.

import java.sql.*;
import java.util.ArrayList;

public class FriendDAO {
	
	public int intsertFriend(String fromFriend, String toFriend){
		//성공 = 1 실패 = 0
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String url = "jdbc:mysql://localhost:3306/chatdb";
		String uid = "root";
		String upw = "mysql";
		
		String sql = "insert into Friend (userFrom, userTo) values (?,?)";
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection(url, uid, upw);
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, fromFriend);
			pstmt.setString(2, toFriend);
			
			int i = pstmt.executeUpdate();
			
			if(i == 1){
				result = 1;
			} else{
				result = 0;
			}
			
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			try{
				if(conn != null) conn.close();
				if(pstmt != null) pstmt.close();
			} catch(Exception e2){
				e2.printStackTrace();
			}
		}
		
		return result;
	}
	
	public ArrayList<String> findFriend(String fromFriend){
		
		ArrayList<String> myFriend = new ArrayList<String>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String url = "jdbc:mysql://localhost:3306/chatdb";
		String uid = "root";
		String upw = "mysql";
		
		String sql = "select userTo from Friend where userFrom = ?";
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection(url, uid, upw);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fromFriend);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				myFriend.add(rs.getString("userTo"));
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			try{
				if(conn != null) conn.close();
				if(pstmt != null) pstmt.close();
				if(rs != null) rs.close();
			} catch(Exception e2){
				e2.printStackTrace();
			}
		} 
		return myFriend;
	}
}
