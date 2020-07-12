package ServerPath;
// 클라이언트 - 서버 통로 되는 클래스.

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

import Controller.RequestFrontController;
import Entity.UserInfo;

public class NetworkSocket extends Thread{
	//users에서 소켓연결 모든유저 포함
	private static Contact contact;
	private Socket userSocket = null;
	private String id;

	DataOutputStream dos = null;
	DataInputStream dis = null;
	InputStream is = null;
	OutputStream os = null;
	private static ArrayList<NetworkSocket> allSocket = new ArrayList<NetworkSocket>();//
	
	public static ArrayList<NetworkSocket> getAllSocket(){
		return allSocket;
	}
	
	public void setUserId(String id){
		this.id = id;
	}
	
	public String getUserId(){
		return this.id;
	}
	
	public NetworkSocket(Socket userSocket) {
		// TODO Auto-generated constructor stub
		this.userSocket = userSocket;
		userNetworking();
	}

	public static void setContact(Contact contact){
		NetworkSocket.contact = contact;
		
	}
	
	//클라이언트 소켓으로 메세지를 보내준다.
	public void sendMessage(String msg) {
		try{
			dos.writeUTF(msg);
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	//클라이언트 쪽으로 for문 이용해서 유저들에게 보내준다.
	public void broadcast(String msg) {
		for(NetworkSocket user : allSocket){
			user.sendMessage(msg);
		}
		
	}
	//연결 확인 메소드
	public boolean isConnected() {
		return !userSocket.isClosed();
	}
	
	public void userNetworking() {
		try{
			is = userSocket.getInputStream();
			dis = new DataInputStream(is);
			
			os = userSocket.getOutputStream();
			dos = new DataOutputStream(os);
			
			allSocket.add(this);
			
		} catch(IOException e){
			e.printStackTrace();
			logout();
		}
		this.start();
	}
	
	public void logout() {
		is = null;
		dis = null;
		os = null;
		dos = null;
		try {
			userSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		allSocket.remove(this);
		this.stop();
	}
	
	@Override
	public void run() {
		while(userSocket.isConnected()){ //무한반복으로
			try {
				String msg = dis.readUTF(); //메시지를 읽음
				contact.addStatus(msg);
				StringTokenizer st = new StringTokenizer(msg, "/.");
				String protocol = st.nextToken();
				
				// loginTry/.id
				if(protocol.equals("loginTry")) {
					String id = st.nextToken();
					RequestFrontController rfc = new RequestFrontController();
					rfc.loginTry(this, msg);
					contact.addStatus("현재 인원 : " + UserInfo.getAllUser().size());
					contact.addStatus("현재 소켓 : " + allSocket.size());
				}
				// addFriend/.toFriend
				else if(protocol.equals("addFriend")) {
					String id = st.nextToken();
					RequestFrontController rfc = new RequestFrontController();
					rfc.insertFriend(this, this.id, id);
				}
				// userLogout/.
				else if(protocol.equals("logout")) {
					String id = this.id;
					RequestFrontController rfc = new RequestFrontController();
					rfc.userLogout(this, id);
					contact.addStatus("현재 인원 : " + UserInfo.getAllUser().size());
					contact.addStatus("현재 소켓 : " + allSocket.size());
				}
				// addRoom/.roomName/.school/.max
				else if(protocol.equals("addRoom")) {
					String roomName = st.nextToken();
					String school = st.nextToken();
					int max = Integer.parseInt(st.nextToken());
					RequestFrontController rfc = new RequestFrontController();
					rfc.addRoom(this,roomName, school, max);
				}
				// exitRoom/.roomnum
				else if(protocol.equals("exitRoom")) {
					int roomNum = Integer.parseInt(st.nextToken());
					RequestFrontController rfc = new RequestFrontController();
					rfc.exitRoom(this, roomNum);
				}
				
				//enterRoom/.roomNum
				else if(protocol.equals("enterRoom")) {
					int roomNum = Integer.parseInt(st.nextToken());
					RequestFrontController rfc = new RequestFrontController();
					rfc.enterRoom(this, roomNum);
				}
				//sendChatting/. roomNum/. chat
				else if(protocol.equals("sendChatting")) {
					int roomNum = Integer.parseInt(st.nextToken());
					String chat = st.nextToken();
					RequestFrontController rfc = new RequestFrontController();
					rfc.sendChatting(this, roomNum, chat);
				}
				//sendNote/. friendTo/. note
				else if(protocol.equals("sendNote")) {
					String friendto = st.nextToken();
					String note  = st.nextToken();
					RequestFrontController rfc = new RequestFrontController();
					rfc.sendNote(this, friendto, note);
				}
				//inviteFriend/. friendId/. roomNum/.
				else if(protocol.equals("inviteFriend")) {
					String friendId = st.nextToken();
					int roomNum  = Integer.parseInt(st.nextToken());
					if( roomNum == 0) {
						return;
					}
					RequestFrontController rfc = new RequestFrontController();
					rfc.inviteFriend(this, friendId, roomNum);
				}

				//Request는 읽은 메시지를 구별해서 함수 실행할 클래스임. 자세한 내용은 Request에 있음
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				this.logout();
			}
		}
	}
}
