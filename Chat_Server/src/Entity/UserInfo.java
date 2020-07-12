package Entity;
import java.util.Vector;


// user 임시 저장 클래스

public class UserInfo{
	
	 private static Vector<UserInfo> allUser = new Vector<>();
	 private String userId;
	 private String school;
	 boolean isMainGUI = true;
	 
	 public static Vector<UserInfo> getAllUser(){
		 return UserInfo.allUser;
	 }
	 
	 public int getUserSize(){
		 return allUser.size();
	 }
	 
	 public String getUserId(){
		 return this.userId;
	 }
	 
	 public String getSchool(){
		 return this.school;
	 }
	 
	 public UserInfo getUser(String id){
		 for(UserInfo user : allUser){
			 if(user.userId.equals(id)){
				 return user;
			 }
		 }
		 return null;
	 }
	 
	 //로그인 시도하는 메소드
	 public int loginTry(String id, String pass) {
		 for(UserInfo user : allUser){
			 if(user.userId.equals(id)){
				 return 4;
			 }
		 }
		 
		 UserDAO dao = new UserDAO();
		 int checkLogin = dao.loginTry(id, pass);
		 
		 if(checkLogin == 1) {
			 String checkSchool = dao.findSchool(id);
			 return checkLogin;
		 } else {
			 return checkLogin;
		 }
	 }
	 //로그아웃시 시도하는 메소드
	 public void userLogout(String id) {
		 UserInfo user = getUser(id);
		 allUser.remove(user);
	 }
	 //새로운 유저 접근시 필요한 메소드
	 public void newUser(String id, String school) {
		 this.userId = id;
		 this.school = school;
		 allUser.add(this);
	 }
	 
	 public boolean isLogin(String id){
		 UserInfo user = getUser(id);
		 return user != null;
	 }
	 
	 public String findSchool(String id){
		 UserDAO dao = new UserDAO();
		 String school = dao.findSchool(id);
		 return school;
	 }
	 
	 public void enterRoom(String id) {
		 UserInfo user = getUser(id);
		 user.isMainGUI = false;
	 }
	 
	 public void exitRoom(String id) {
		 UserInfo user = getUser(id);
		 user.isMainGUI = true;
	 }
	 
	 public boolean checkMainGUI(String id) {
		 UserInfo user = getUser(id);
		 return user.isMainGUI;
	 }
	 
}
