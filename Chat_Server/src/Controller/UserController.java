package Controller;
import java.util.Vector;

//������ ���� ������ �����ϴ� ��Ʈ�ѷ�
import Entity.UserInfo;

public class UserController {
	//���̵�� ���� ��ü �������� �Լ�
	public UserInfo getUser(String id){
		UserInfo ui = new UserInfo();
		return ui.getUser(id);
	}
	
	//�α��� �õ��� �� ����Ǵ� �޼ҵ�
	public int loginTry(String id, String pass) {
		UserInfo ui = new UserInfo();
		return ui.loginTry(id, pass);
	}
	//�α׾ƿ��� ����Ǵ� �޼ҵ�
	public void userLogout(String id) {
		UserInfo ui = new UserInfo();
		ui.userLogout(id);
	}
	//���ο� ������ ������ ����Ǵ� �޼ҵ�
	public void newUser(String id, String school) {
		UserInfo ui = new UserInfo();
		ui.newUser(id, school);
	}
	//���� ���� �� ����Ǵ� �޼ҵ�
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
