package Controller;
//ģ���� ���õ� ������ �����ϴ� ��Ʈ�ѷ�

import java.util.ArrayList;

import Entity.FriendDAO;

public class FriendController {
	
	//ģ�� �߰��ϴ� �޼ҵ�
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
