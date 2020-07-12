package Handler;

import java.util.Vector;

public class UserHandler {
	private static Vector<UserHandler> allUser = new Vector<>();
	private String userId;
	
	public String getUserId() {
		return this.userId;
	}
	
	public String toString(){
		return userId;
	}
	
	public static Vector<UserHandler> getAllUser(){
		return UserHandler.allUser;
	}
	
	public void newUser(String id) {
		this.userId = id;
		allUser.add(this);
	}
	
	public void logoutUser(String id) {
		for(int i = 0; i < allUser.size(); i++) {
			if(allUser.get(i).userId.equals(id)) {
				allUser.remove(i);
				return;
			}
		}
	}
	
	public boolean isLogon(String id) {
		for(UserHandler user : allUser) {
			if(id.equals(user.getUserId())) {
				return true;
			}
		}
		return false;
	}
	
}
