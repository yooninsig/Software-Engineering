package Controller;
import java.util.Vector;

//유저에 대한 정보를 조작하는 컨트롤러
import Entity.UserInfo;

public class UserController {
	//아이디로 유저 객체 가져오는 함수
	public UserInfo getUser(String id){
		UserInfo ui = new UserInfo();
		return ui.getUser(id);
	}
	
	//로그인 시도할 때 실행되는 메소드
	public int loginTry(String id, String pass) {
		UserInfo ui = new UserInfo();
		return ui.loginTry(id, pass);
	}
	//로그아웃시 실행되는 메소드
	public void userLogout(String id) {
		UserInfo ui = new UserInfo();
		ui.userLogout(id);
	}
	//새로운 유저가 들어오면 실행되는 메소드
	public void newUser(String id, String school) {
		UserInfo ui = new UserInfo();
		ui.newUser(id, school);
	}
	//쪽지 보낼 때 실행되는 메소드
	public void sendNote(String id) {
		
	}
	
	public boolean isLogin(String id){
		UserInfo ui = new UserInfo();
		return ui.isLogin(id);
	}
	
	public String findSchool(String id){
		UserInfo ui = new UserInfo();
		return ui.findSchool(id);
	}
	
	public Vector<UserInfo> getAllUser(){
		return UserInfo.getAllUser();
	}
	
	 public void enterRoom(String id) {
		 UserInfo ui = new UserInfo();
		 ui.enterRoom(id);
	 }
	 
	 public void exitRoom(String id) {
		 UserInfo ui = new UserInfo();
		 ui.exitRoom(id);
	 }
	 
	 public boolean checkMainGUI(String id) {
		 UserInfo ui = new UserInfo();
		 return ui.checkMainGUI(id);	 
	 }
	
}
