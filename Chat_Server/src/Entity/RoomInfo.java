package Entity;
import java.util.ArrayList;

//룸 정보 
public class RoomInfo {
	private static ArrayList<RoomInfo> allRoom = new ArrayList<RoomInfo>();
	private ArrayList<String> inUser;
	private int roomNum; //방번호
	private String roomName; //방이름
	private String school; //방학교
	private int max; //최대인원수

	public static int roomNo = 0;
	
	//방 번호로 해당 Room 반환
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
	
	//유저가 방에 접속하지 못하면 false
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
	
	//생성된 방의 방번호 반환
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
