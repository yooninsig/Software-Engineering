package Controller;
import java.util.ArrayList;
import java.util.Vector;

import Entity.RoomInfo;

// 방에 대한 정보를 조작하는 컨트롤러

public class RoomController {

	public ArrayList<RoomInfo> allRoom(){
		return RoomInfo.getRoomList();
	}
	
	public RoomInfo getRoom(int roomNum){
		RoomInfo ri = new RoomInfo();
		return ri.getRoom(roomNum);
	}
	//방 들어갈 때 실행되는 메소드
	public boolean enterRoom(String id, int roomNum) {
		RoomInfo ri = new RoomInfo();
		return ri.enterRoom(id, roomNum);
	}
	//방 삭제시 필요한 메소드
	public void deleteRoom(int RoomNo) {
		RoomInfo ri = new RoomInfo();
		ri.deleteRoom(RoomNo);
	}
	//방 추가하는 메소드
	public int addRoom(String roomName, String school, int max) {
		RoomInfo ri = new RoomInfo();
		return ri.addRoom(roomName, school, max);
		
	}
	//방 나갈 때 실행 되는 메소드
	public void exitRoom(String id, int roomNum) {
		RoomInfo ri = new RoomInfo();
		ri.exitRoom(id, roomNum);
		
	}
	
	//방의 모든 유저 뽑아오는 함수
	public ArrayList<String> inUser(int roomNum){
		RoomInfo ri = new RoomInfo();
		return ri.getInUser(roomNum);
	}
	
	public boolean isFull(int roomNum) {
		RoomInfo ri = new RoomInfo();
		return ri.isFull(roomNum);
	}
}
