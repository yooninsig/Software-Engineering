package Controller;

import java.util.ArrayList;
import java.util.StringTokenizer;

import Entity.RoomInfo;
import Entity.UserInfo;
import ServerPath.NetworkSocket;

public class RequestFrontController {
	
	// loginTry/.id/.password
	public void loginTry(NetworkSocket ns, String msg){
		StringTokenizer st = new StringTokenizer(msg, "/.");
		st.nextToken();
		String id = st.nextToken();
		String password = st.nextToken();
		int confirm;
		String school;
		
		UserController uc = new UserController();
		FriendController fc = new FriendController();
		if(uc.isLogin(id)){
			ns.sendMessage("failLogin/.4");
		}
		else{
			confirm = uc.loginTry(id, password);
			if(confirm == 1){
				school = uc.findSchool(id);
				ns.sendMessage("acceptLogin/." + id + "/." + school);
				ns.setUserId(id);
				ns.broadcast("newUser/." + id);
				for(String friend : fc.findFriend(id)){
					ns.sendMessage("receiveFriendInfo/." + friend);
				}
				for(UserInfo user : UserInfo.getAllUser()){
					
					ns.sendMessage("newUser/." + user.getUserId());
				}
				uc.newUser(id, school);
				for(RoomInfo room : RoomInfo.getRoomList())
				{
					ns.sendMessage("newRoom/." + room.getRoomNum() + "/." + room.getRoomName() + "/." + room.getSchool() + "/." + room.getMax() + "/." + room.getRoomSize());
				}
			}
			else if(confirm == 2){
				ns.sendMessage("failLogin/.2");
			}
			else{
				ns.sendMessage("failLogin/.3");
			}
		}
	}
	
	public void insertFriend(NetworkSocket ns, String fromFriend, String toFriend){
		FriendController fc = new FriendController();
		//되면 1 아니면 0
		int confirm = fc.insertFriend(fromFriend, toFriend);
		if(confirm == 1){
			ns.sendMessage("newFriend/." + toFriend);
		}
		else
			ns.sendMessage("failAddFriend/." + toFriend);
	}
	
	public void userLogout(NetworkSocket ns, String id) {
		UserController uc = new UserController();
		uc.userLogout(id);
		ns.broadcast("logoutUser/." + id);
	}
	
	public void addRoom(NetworkSocket ns, String roomName, String school, int max) {
		RoomController rc = new RoomController();
		int roomNum = rc.addRoom(roomName, school, max);
		int inUser = rc.inUser(roomNum).size();
		ns.broadcast("newRoom/." + roomNum + "/." + roomName+ "/." + school + "/." + max + "/." + inUser);
		ns.sendMessage("roomMaker/." + roomNum);
	}
	
	
	public void exitRoom(NetworkSocket ns, int roomNum) {
		String id = ns.getUserId();
		RoomController rc = new RoomController();
		rc.exitRoom(ns.getUserId(), roomNum);
		UserController uc = new UserController();
		uc.exitRoom(id);
		if(rc.inUser(roomNum).size()==0) {
			rc.deleteRoom(roomNum);
			ns.broadcast("deleteRoom/." + roomNum);
		}
		else {
			ns.broadcast("exitRoom/." + roomNum + "/." + rc.inUser(roomNum).size() + "/." + id);
		}
	}

	public void enterRoom(NetworkSocket ns, int roomNum) {
		String id = ns.getUserId();
		RoomController rc = new RoomController();
		boolean isSuccess = rc.enterRoom(id, roomNum);
		ArrayList<String> inUser = rc.inUser(roomNum);
		UserController uc = new UserController();
		uc.enterRoom(id);
		if(isSuccess) {
			ns.broadcast("enterRoom/." + roomNum + "/." + inUser.size() + "/." + id);
		}
		for(int i = inUser.size()-1 ; i>=0; i--) {
			if(inUser.get(i).equals(id)) {
				continue;
			}
			ns.sendMessage("enterRoom/." + roomNum + "/." + inUser.size() + "/." + inUser.get(i));
		}
	}
	
	public void sendChatting(NetworkSocket ns, int roomNum, String chat) {
		RoomController rc = new RoomController();
		ArrayList<String> inUser = rc.inUser(roomNum);
		String sendId = ns.getUserId();
		for(NetworkSocket user : NetworkSocket.getAllSocket()){
			for(String id : inUser){
				if(user.getUserId().equals(id)){
					user.sendMessage("sendChatting/." + sendId + "/." + chat);
				}
			}
		}
	}
	
	public void sendNote(NetworkSocket ns, String friendTo, String note) {
		//Vector<String>
		String fromId = ns.getUserId();
		UserController uc = new UserController();
		if(uc.isLogin(friendTo)){
			for(NetworkSocket user : NetworkSocket.getAllSocket()) {
				if(user.getUserId().equals(friendTo)){
					user.sendMessage("receiveNote/." + fromId +  "/." + note);
				}
			}
		}
		else{
			ns.sendMessage("errorNote/. 상대방이 로그인중이 아닙니다.");
		}
	}

	public void inviteFriend(NetworkSocket ns, String friendId, int roomNum) {
		String fromId = ns.getUserId();
		UserController uc = new UserController();
		RoomController rc = new RoomController();
		System.out.println("1번");
		if(uc.checkMainGUI(friendId) && !rc.isFull(roomNum)) {
			for(NetworkSocket user : NetworkSocket.getAllSocket()) {
				if(user.getUserId().equals(friendId)){
					user.sendMessage("receiveInvite/." + fromId + "/." + roomNum);
				}
			}
		}
		else {
			ns.sendMessage("falseInvite/."+  fromId + "/. 초대할수없습니다.");
		}	
	}

}
