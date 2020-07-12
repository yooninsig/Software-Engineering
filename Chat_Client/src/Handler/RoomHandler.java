package Handler;

import java.util.Vector;

public class RoomHandler {
	private static Vector<RoomHandler> allRoom = new Vector<>();
	private int roomNo;
	private String roomName;
	private int max;
	private int inUser;
	private String school;
	private static Vector<RoomHandler> roomBuff = new Vector<>();

	public Vector<RoomHandler> getFilteredRoom(){
		return roomBuff;
	}
	
	public int getRoomNum() {
		return roomNo;
	}

	public String getRoomName() {
		return roomName;
	}

	public int getMax() {
		return max;
	}

	public int getInUser() {
		return inUser;
	}

	public String getSchool() {
		return school;
	}

	public void filterSchool(String school) {
		Vector<RoomHandler> buf = new Vector<>();
		RoomHandler rh = new RoomHandler();
		for (RoomHandler room : rh.getAllRoom()) {
			if (room.getSchool().equals(school)) {
				buf.add(room);
			}
		}
		roomBuff = buf;
	}

	public RoomHandler getRoom(int roomNum) {
		for (RoomHandler room : allRoom) {
			if (room.roomNo == roomNum)
				return room;
		}
		return null;
	}

	public String toString() {
		return String.format("%5d %20s %10s %2d %2d ", roomNo, roomName, school, inUser, max);
	}

	public static Vector<RoomHandler> getAllRoom() {
		return allRoom;
	}

	public void newRoom(int roomNo, String roomName, String school, int max, int inUser) {
		this.roomNo = roomNo;
		this.roomName = roomName;
		this.school = school;
		this.max = max;
		this.inUser = inUser;
		allRoom.add(this);
	}

	public void receiveRoomInfo(int roomNum, String roomName, String school, int max, int inUser) {
		this.roomNo = roomNum;
		this.roomName = roomName;
		this.school = school;
		this.max = max;
		this.inUser = inUser;
		allRoom.add(this);
	}

	public void deleteRoom(int roomNo) {
		for (int i = 0; i < allRoom.size(); i++) {
			if (allRoom.get(i).roomNo == roomNo) {
				allRoom.remove(i);
				return;
			}
		}
	}

	public void enterRoom(int roomNo, int inUser) {
		for (RoomHandler room : allRoom) {
			if (roomNo == room.roomNo)
				room.inUser = inUser;
		}
	}

	public void exitRoom(int roomNo, int size) {
		for (RoomHandler room : allRoom) {
			if (roomNo == room.roomNo) {
				room.inUser = size;
				return;
			}
		}
	}

	public boolean isFull(int roomNo) {
		for (RoomHandler room : allRoom) {
			if (roomNo == room.roomNo)
				return room.inUser >= room.max;
		}
		return false;
	}
}
