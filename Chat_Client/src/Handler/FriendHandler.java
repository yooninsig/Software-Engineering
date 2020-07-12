package Handler;

import java.util.Vector;

public class FriendHandler {
	
	private static Vector<FriendHandler> allFriend = new Vector<>();
	private String friendId;
	private Boolean isLogon;
	
	public static Vector<FriendHandler> getAllFriend(){
		return FriendHandler.allFriend;
	}
	
	public String getFriendId(){
		return this.friendId;
	}
	
	public Boolean isLogon() {
		return this.isLogon;
	}
	
	public String toString() {
		String logon = isLogon? "ON" : "OFF";
		String parseId;
		
		if(friendId.length() >= 20) {
			parseId = friendId.substring(0, 20);
		}
		
		return String.format("%20s    %-4s", friendId, logon);
	}
	
	public void receiveFriendInfo(String id) {
		FriendHandler fh = new FriendHandler();
		fh.friendId = id;
		fh.isLogon = false;
		allFriend.add(fh);
	}
	
	public boolean isFriend(String id) {
		for(FriendHandler friend : allFriend) {
			if(friend.friendId.equals(id))
				return true;
		}
		return false;
	}
	
	public void newFriend(String id) {
		this.friendId = id;
		this.isLogon = true;
		allFriend.add(this);
	}

	public boolean isLogon(String id) {
		for (int i = 0; i < allFriend.size(); i++) {
			if (allFriend.get(i).friendId.equals(id)) {
				return allFriend.get(i).isLogon;
			}
		}
		return false;
	}

	public void loginFriend(String id) {
		for (int i = 0; i < allFriend.size(); i++) {
			if (allFriend.get(i).friendId.equals(id)) {
				allFriend.get(i).isLogon = true;
			}
		}
	}

	public void logoutFriend(String id){
        for(int i = 0; i < allFriend.size(); i++) {
        	if(allFriend.get(i).friendId.equals(id)) {
        		allFriend.get(i).isLogon = false;
        	}
        }
	}
}