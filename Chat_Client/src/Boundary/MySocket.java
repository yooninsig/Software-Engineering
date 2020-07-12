package Boundary;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class MySocket extends Thread {
	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;
	private InputStream is;
	private OutputStream os;

	private Login login;
	private Main mainGui;
	private Chat chatGui;
	private Popup popupGui;

	public void setLogin(Login login) {
		this.login = login;
	}

	public void setChat(Chat chat) {
		this.chatGui = chat;
	}

	public void setMain(Main main) {
		this.mainGui = main;
	}

	public void setPopup(Popup popup) {
		this.popupGui = popup;
	}

	public void connect(String ip, String port) {
		try {
			socket = new Socket(ip, Integer.parseInt(port.trim())); // �����ǿ� ��Ʈ �޾Ƽ� ����

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void userNetwork() {
		try { // ������ ��Ʈ�� ���� �õ�.
			is = socket.getInputStream();
			dis = new DataInputStream(is);

			os = socket.getOutputStream();
			dos = new DataOutputStream(os);

			this.start(); // ���� run�Լ� ����.
		} catch (IOException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	@Override
	public void run() {
		while (socket.isConnected()) { // ������ ���� ������ ������ ����.
			try {
				String msg = dis.readUTF(); // �����κ��� �޽����� ������ �޴´�.
				System.out.println(msg);
				StringTokenizer st = new StringTokenizer(msg, "/.");
				String protocol = st.nextToken();

				// failLogin/.1�Ǵ� 0 �Ǵ� -1
				if (protocol.equals("failLogin")) {
					login.failLogin(Integer.parseInt(st.nextToken()));
				}

				// acceptLogin/.id/.School
				else if (protocol.equals("acceptLogin")) {
					login.acceptLogin(st.nextToken(), st.nextToken());
				}

				// newUser/.id
				else if (protocol.equals("newUser")) {
					mainGui.newUser(st.nextToken());
				}

				// logoutUser/.id
				else if (protocol.equals("logoutUser")) {
					mainGui.logoutUser(st.nextToken());
				}

				// receiveFriendInfo/.id
				else if (protocol.equals("receiveFriendInfo")) {
					mainGui.receiveFriendInfo(st.nextToken());
				}

				// failAddFriend/.toFriend
				else if (protocol.equals("failAddFriend")) {
					mainGui.failAddFriend(st.nextToken());
				}

				// loginFriend/.id
				else if (protocol.equals("loginFriend")) {
					mainGui.loginFriend(st.nextToken());
				}

				// logoutFriend/.id
				else if (protocol.equals("logoutFriend")) {
					mainGui.logoutFriend(st.nextToken());
				}

				// newFriend/.id
				else if (protocol.equals("newFriend")) {
					mainGui.newFriend(st.nextToken());
				}

				// exitRoom/.roomNum/.inUser/.userId
				else if (protocol.equals("exitRoom")) {
					int roomNum = Integer.parseInt(st.nextToken());
					int inUser = Integer.parseInt(st.nextToken());
					String id = st.nextToken();
					mainGui.exitRoom(roomNum, inUser);
					if (chatGui != null && chatGui.getRoomNum() == roomNum) {
						chatGui.exitRoom(id);
						chatGui.receiveChatting(roomNum + " �� �濡 " + id + " ���� �����ϼ̽��ϴ�.");
					}
				}

				// enterRoom/.roomNum/.inUser/.userId
				else if (protocol.equals("enterRoom")) {
					int roomNum = Integer.parseInt(st.nextToken());
					int inUser = Integer.parseInt(st.nextToken());
					String id = st.nextToken();
					mainGui.enterRoom(roomNum, inUser);
					if (chatGui != null && chatGui.getRoomNum() == roomNum) {
						chatGui.enterRoom(id);
						String buffer = (roomNum + " �� �濡 " + id + " ���� �����ϼ̽��ϴ�.");
						chatGui.receiveChatting(buffer);
					}
				}

				// deleteRoom/.roomNum
				else if (protocol.equals("deleteRoom")) {
					int roomNum = Integer.parseInt(st.nextToken());
					mainGui.deleteRoom(roomNum);
					if (chatGui != null && chatGui.getRoomNum() == roomNum) {
						chatGui.getExit().doClick();
					}
				}

				// newRoom/.roomNum/.roomName/.school/.max/.inUser
				else if (protocol.equals("newRoom")) {
					int roomNum = Integer.parseInt(st.nextToken());
					String roomName = st.nextToken();
					String school = st.nextToken();
					int max = Integer.parseInt(st.nextToken());
					int inUser = Integer.parseInt(st.nextToken());
					mainGui.newRoom(roomNum, roomName, school, max, inUser);
				}

				// receiveRoomInfo/.roomNum/.roomName/.school/.max/.inUser
				else if (protocol.equals("receiveRoomInfo")) {
					int roomNum = Integer.parseInt(st.nextToken());
					String roomName = st.nextToken();
					String school = st.nextToken();
					int max = Integer.parseInt(st.nextToken());
					int inUser = Integer.parseInt(st.nextToken());
					mainGui.receiveRoomInfo(roomNum, roomName, school, max, inUser);
				}

				// sendChatting/.userId/.message
				else if (protocol.equals("sendChatting")) {
					String id = st.nextToken();
					String message = st.nextToken();
					if (chatGui != null) {
						chatGui.receiveChatting(id + " : " + message);
					}
				}
				// sendNote/.fromId/.msg
				else if (protocol.equals("sendNote")) {
					popupGui.receiveNote(st.nextToken(), st.nextToken());
				}

				// inviteFriend/.fromId/.roomNum
				else if (protocol.equals("receiveInvite")) {
					String sender = st.nextToken();
					int roomNum = Integer.parseInt(st.nextToken());
					popupGui.receiveInvite(sender, roomNum);
				}

				// falseInvite/.id/.str
				else if (protocol.equals("falseInvite")) {
					String id = st.nextToken();
					String str = st.nextToken();
					chatGui.receiveChatting(id + "���� " + str);
				}

				// receiveNote/.id/.message
				else if (protocol.equals("receiveNote")) {
					String id = st.nextToken();
					String message = st.nextToken();
					popupGui.receiveNote(id, message);
				}
				
				// errorNote/.message
				else if (protocol.equals("erroNote")) {
					String message = st.nextToken();
					popupGui.errorNote(message);
				}
				
				// roomMaker/.roomNum
				else if(protocol.equals("roomMaker")) {
					int roomNum = Integer.parseInt(st.nextToken());
					mainGui.myEnterRoom(roomNum);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void sendMessage(String str) { // �������� �޽��� ������ �Լ�.
		try {
			dos.writeUTF(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean isConnected() {
		return socket.isConnected();
	}

}
