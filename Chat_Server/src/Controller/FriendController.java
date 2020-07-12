package Controller;
//친구와 관련된 정보를 조작하는 컨트롤러

import java.util.ArrayList;

import Entity.FriendDAO;

public class FriendController {
	
	//친구 추가하는 메소드
	public int insertFriend(String fromFriend, String toFriend) {
		FriendDAO friendDAO = new FriendDAO();
		int inputFriend = friendDAO.intsertFriend(fromFriend, toFriend);
		if(inputFriend == 1){
			return inputFriend;
		} else{
			return inputFriend;
		}
	}
	
	public ArrayList<String> findFriend(String fromFriend){
		FriendDAO friendDAO = new FriendDAO();
		return friendDAO.findFriend(fromFriend);
	}
}
