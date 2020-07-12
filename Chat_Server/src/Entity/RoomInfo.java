package Entity;
import java.util.ArrayList;

//�� ���� 
public class RoomInfo {
	private static ArrayList<RoomInfo> allRoom = new ArrayList<RoomInfo>();
	private ArrayList<String> inUser;
	private int roomNum; //���ȣ
	private String roomName; //���̸�
	private String school; //���б�
	private int max; //�ִ��ο���

	public static int roomNo = 0;
	
	//�� ��ȣ�� �ش� Room ��ȯ
	public RoomInfo getRoom(int roomNum){
		for(RoomInfo room : allRoom){
			if(room.roomNum == roomNum){
				return room;
			}
		}
		return null;
	}
	
	public ArrayList<String> getInUser(int roomNum) {
		RoomInfo ri = getRoom(roomNum);
		return ri.inUser;
	}

	public int getRoomNum() {
		return roomNum;
	}

	public String getRoomName() {
		return roomName;
	}

	public String getSchool() {
		return school;
	}

	public int getMax() {
		return max;
	}
	
	public int getRoomSize(){
		return inUser.size();
	}

	public static ArrayList<RoomInfo> getRoomList(){
		return allRoom;
	}
	
	//������ �濡 �������� ���ϸ� false
	public boolean enterRoom(String id, int roomNo) {
		RoomInfo ri = getRoom(roomNo);
		
		if(isFull(roomNo))
			return false;
		else{
			System.out.println(id);
			ri.inUser.add(id);
			for(int i = 0; i < ri.inUser.size(); i++) {
				System.out.println(ri.inUser.get(i));
			}
			return true;
		}
	}
	
	public void deleteRoom(int roomNo) {
		RoomInfo ri = getRoom(roomNo);
		allRoom.remove(ri);
	}
	
	//������ ���� ���ȣ ��ȯ
	public int addRoom(String roomName, String school, int max) { 
		this.roomName = roomName;
		this.school = school;
		this.max = max;
		inUser = new ArrayList<String>();
		roomNo++;
		roomNum = roomNo;
		allRoom.add(this);
		
		return this.roomNum;
	}

	
	//isEmpty
	public void exitRoom(String id, int roomNum) {
		RoomInfo ri = getRoom(roomNum);
		for(String user : ri.inUser){
			if(user.equals(id)){
				ri.inUser.remove(user);
				break;
			}
		}
	}
	public boolean isFull(int roomNum) {
		RoomInfo ri = getRoom(roomNum);
		if(ri.inUser.size() < ri.max) {
			return false;
		}
		else{
			return true;
		}
	}
}
