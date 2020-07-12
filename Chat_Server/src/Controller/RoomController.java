package Controller;
import java.util.ArrayList;
import java.util.Vector;

import Entity.RoomInfo;

// �濡 ���� ������ �����ϴ� ��Ʈ�ѷ�

public class RoomController {

	public ArrayList<RoomInfo> allRoom(){
		return RoomInfo.getRoomList();
	}
	
	public RoomInfo getRoom(int roomNum){
		RoomInfo ri = new RoomInfo();
		return ri.getRoom(roomNum);
	}
	//�� �� �� ����Ǵ� �޼ҵ�
	public boolean enterRoom(String id, int roomNum) {
		RoomInfo ri = new RoomInfo();
		return ri.enterRoom(id, roomNum);
	}
	//�� ������ �ʿ��� �޼ҵ�
	public void deleteRoom(int RoomNo) {
		RoomInfo ri = new RoomInfo();
		ri.deleteRoom(RoomNo);
	}
	//�� �߰��ϴ� �޼ҵ�
	public int addRoom(String roomName, String school, int max) {
		RoomInfo ri = new RoomInfo();
		return ri.addRoom(roomName, school, max);
		
	}
	//�� ���� �� ���� �Ǵ� �޼ҵ�
	public void exitRoom(String id, int roomNum) {
		RoomInfo ri = new RoomInfo();
		ri.exitRoom(id, roomNum);
		
	}
	
	//���� ��� ���� �̾ƿ��� �Լ�
	public ArrayList<String> inUser(int roomNum){
		RoomInfo ri = new RoomInfo();
		return ri.getInUser(roomNum);
	}
	
	public boolean isFull(int roomNum) {
		RoomInfo ri = new RoomInfo();
		return ri.isFull(roomNum);
	}
}
