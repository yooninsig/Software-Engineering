package Boundary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import Handler.FriendHandler;
import Handler.RoomHandler;
import Handler.UserHandler;

public class Main extends JFrame implements ActionListener, WindowListener, ListCellRenderer<Object>, MouseListener {
	private JPanel mainContentPane = new JPanel();
	private JButton make_btn = new JButton("\uBC29\uB9CC\uB4E4\uAE30"); // 방만들기 버튼
	private JButton add_btn = new JButton("\uCE5C\uAD6C\uCD94\uAC00"); // 친구추가 버튼
	private JButton exit_btn = new JButton("\uB85C\uADF8\uC544\uC6C3"); // 로그아웃 버튼
	private JTextField find_tf = new JTextField(); // 친구찾기 입력창
	private JList room_list = new JList();
	private JList friend_list = new JList(); // 친구 목록
	private JList user_list = new JList(); // 전체 유저 목록
	private JComboBox<String> school_cb = new JComboBox(schoolList());
	private final JScrollPane scrollPane = new JScrollPane();
	private JLabel myId_label;

	private JFrame addRoom_frame = new JFrame("방 만들기");
	private JPanel addContentPane = new JPanel();
	private JTextField name_tf = new JTextField();
	private JButton addRoom_btn = new JButton("\uBC29\uB9CC\uB4E4\uAE30");
	private JButton addExit_btn = new JButton("\uC885\uB8CC");
	private JComboBox<Integer> addMax_cb = new JComboBox(maxList());
	private JComboBox addSchool_cb = new JComboBox(schoolList());

	private String myId;
	private String mySchool;
	private MySocket mySocket;
	private Chat chatGui;
	private Popup popupGui;

	public Main(MySocket mySocket) {
		this.mySocket = mySocket;
		this.mySocket.setMain(this);

		addWindowListener(this);
		setBounds(100, 100, 850, 550);
		mainContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainContentPane);
		mainContentPane.setLayout(null);

		make_btn.setBounds(398, 467, 229, 34);
		make_btn.addActionListener(this);
		mainContentPane.add(make_btn);

		add_btn.setBounds(639, 374, 183, 34);
		mainContentPane.add(add_btn);
		add_btn.addActionListener(this);

		exit_btn.setBounds(639, 467, 183, 34);
		exit_btn.addActionListener(this);
		mainContentPane.add(exit_btn);
		find_tf.setFont(new Font("굴림", Font.PLAIN, 15));

		find_tf.setBounds(639, 327, 183, 37);
		mainContentPane.add(find_tf);
		find_tf.setColumns(10);

		JLabel lblNewLabel = new JLabel("\uCE5C\uAD6C \uBAA9\uB85D");
		lblNewLabel.setEnabled(false);
		lblNewLabel.setBounds(639, 10, 155, 15);
		mainContentPane.add(lblNewLabel);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(639, 35, 183, 129);
		mainContentPane.add(scrollPane_1);
		friend_list.setFont(new Font("굴림", Font.PLAIN, 15));
		friend_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(friend_list);

		JLabel label = new JLabel("\uC804\uCCB4 \uC811\uC18D\uC790");
		label.setEnabled(false);
		label.setBounds(639, 174, 134, 15);
		mainContentPane.add(label);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(639, 199, 183, 118);
		mainContentPane.add(scrollPane_2);
		user_list.setFont(new Font("굴림", Font.PLAIN, 15));
		user_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_2.setViewportView(user_list);
		scrollPane.setBounds(12, 24, 611, 433);

		mainContentPane.add(scrollPane);
		room_list.setFont(new Font("굴림", Font.PLAIN, 16));
		room_list.setBorder(new EmptyBorder(2, 0, 2, 0));
		room_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(room_list);

		myId_label = new JLabel();
		myId_label.setFont(new Font("굴림", Font.PLAIN, 18));
		myId_label.setBounds(639, 434, 183, 23);
		mainContentPane.add(myId_label);

		school_cb.setBounds(22, 467, 250, 34);
		school_cb.setMaximumRowCount(8);
		mainContentPane.add(school_cb);

		room_list.setCellRenderer(this);
		room_list.addMouseListener(this);
		user_list.addMouseListener(this);
		friend_list.addMouseListener(this);
		
		ItemListener il = new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource() == school_cb) {
					RoomHandler rh = new RoomHandler();
					String sc = (String)school_cb.getSelectedItem();
					if(!sc.equals("누구나")){
					rh.filterSchool((String)school_cb.getSelectedItem());
					room_list.setListData(rh.getFilteredRoom());
					}
					else
						room_list.setListData(rh.getAllRoom());
				}
			}
		};

		school_cb.addItemListener(il);

		chatGui = new Chat(this, mySocket);

		setVisible(false);
	}

	public JList getFriendList() {
		return friend_list;
	}

	public String getMyId() {
		return myId;
	}

	public void setPopup(Popup popup) {
		this.popupGui = popup;
	}

	public void setUserId(String id) {
		this.myId = id;
		myId_label.setText("my Id : " + id);
	}

	public void setSchool(String school) {
		this.mySchool = school;
	}

	public void addRoomGui() {
		addRoom_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addRoom_frame.setBounds(100, 100, 610, 250);
		addContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		addRoom_frame.setContentPane(addContentPane);
		addContentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("\uBC29 \uC81C\uBAA9");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 18));
		lblNewLabel.setBounds(36, 20, 134, 39);
		addContentPane.add(lblNewLabel);

		name_tf.setFont(new Font("굴림", Font.PLAIN, 15));
		name_tf.setBounds(151, 22, 380, 39);
		addContentPane.add(name_tf);
		name_tf.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("\uD559\uAD50 \uC120\uD0DD");
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(36, 94, 156, 39);
		addContentPane.add(lblNewLabel_1);

		addSchool_cb.setFont(new Font("굴림", Font.PLAIN, 15));
		addSchool_cb.setBounds(151, 98, 199, 35);
		addSchool_cb.setMaximumRowCount(8);
		addContentPane.add(addSchool_cb);

		JLabel lblNewLabel_2 = new JLabel("\uC778\uC6D0\uC218");
		lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(385, 96, 91, 35);
		addContentPane.add(lblNewLabel_2);

		addMax_cb.setBounds(460, 94, 91, 35);
		addContentPane.add(addMax_cb);

		addRoom_btn.setBounds(183, 154, 182, 39);
		addRoom_btn.addActionListener(this);
		addContentPane.add(addRoom_btn);

		addExit_btn.setBounds(385, 154, 182, 39);
		addExit_btn.addActionListener(this);
		addContentPane.add(addExit_btn);

		addRoom_frame.setVisible(true);
	}

	public JList getRoomList() {
		return room_list;
	}

	public JList getUserList() {
		return user_list;
	}

	public Vector maxList() {
		Vector<Integer> buf = new Vector<>();
		buf.add(2);
		buf.add(3);
		buf.add(4);
		buf.add(5);
		buf.add(6);
		buf.add(7);
		buf.add(8);
		return buf;
	}

	public Vector schoolList() {
		Vector<String> buf = new Vector<>();
		buf.add("누구나");
		buf.add("서울대학교");
		buf.add("가톨릭대학교");
		buf.add("연세대학교");
		buf.add("고려대학교");
		return buf;
	}

	public void logout() {
		mySocket.sendMessage("logout");
		System.exit(0);
	}

	public void newUser(String id) {
		UserHandler uh = new UserHandler();
		uh.newUser(id);
		FriendHandler fh = new FriendHandler();

		if (fh.isFriend(id)) {
			fh.loginFriend(id);
		}
		user_list.setListData(uh.getAllUser());
	}

	public void logoutUser(String id) {
		UserHandler uh = new UserHandler();
		uh.logoutUser(id);
		FriendHandler fh = new FriendHandler();
		if (fh.isLogon(id)) {
			fh.logoutFriend(id);
		}
		user_list.setListData(uh.getAllUser());
	}

	public void loginFriend(String id) {
		FriendHandler fh = new FriendHandler();
		fh.loginFriend(id);
		friend_list.setListData(fh.getAllFriend());
	}

	public void logoutFriend(String id) {
		FriendHandler fh = new FriendHandler();
		fh.logoutFriend(id);
		friend_list.setListData(fh.getAllFriend());
	}

	public void receiveFriendInfo(String id) {
		FriendHandler fh = new FriendHandler();
		fh.receiveFriendInfo(id);
		friend_list.setListData(fh.getAllFriend());
	}

	public void newFriend(String toFriend) {
		FriendHandler fh = new FriendHandler();
		fh.newFriend(toFriend);
		friend_list.setListData(fh.getAllFriend());
	}

	public void addFriend(String toFriend) {
		FriendHandler fh = new FriendHandler();
		UserHandler uh = new UserHandler();
		if (uh.isLogon(toFriend) && !toFriend.equals(myId) && !fh.isFriend(toFriend)) {
			mySocket.sendMessage("addFriend/." + toFriend);
		} else {
			this.failAddFriend(toFriend);
		}
	}

	public void failAddFriend(String toFriend) {
		JOptionPane jp = new JOptionPane();
		jp.showMessageDialog(mainContentPane, toFriend + " 님 친구 추가에 실패했습니다.");
	}

	public void receiveRoomInfo(int roomNum, String roomName, String school, int max, int inUser) {
		RoomHandler rh = new RoomHandler();
		rh.receiveRoomInfo(roomNum, roomName, school, max, inUser);
		room_list.setListData(rh.getAllRoom());
	}

	public void exitRoom(int roomNum, int size) {
		RoomHandler rh = new RoomHandler();
		rh.exitRoom(roomNum, size);
		room_list.setListData(rh.getAllRoom());
	}

	public void deleteRoom(int roomNum) {
		RoomHandler rh = new RoomHandler();
		rh.deleteRoom(roomNum);
		room_list.setListData(rh.getAllRoom());
	}

	public void addRoom(String roomName) {
		String school = addSchool_cb.getSelectedItem().toString();
		int max = Integer.parseInt(addMax_cb.getSelectedItem().toString());
		mySocket.sendMessage("addRoom/." + roomName + "/." + school + "/." + max);
		name_tf.setText("");
		addRoom_frame.dispose();
	}

	public void myEnterRoom(RoomHandler room) {
		if (!room.isFull(room.getRoomNum())) {
			chatGui.myEnterRoom(room.getRoomNum());
			mySocket.sendMessage("enterRoom/." + room.getRoomNum());
			room_list.setListData(room.getAllRoom());
			setVisible(false);
		} else {
			JOptionPane jp = new JOptionPane();
			jp.showMessageDialog(mainContentPane, room.getRoomNum() + "번 방 입장에 실패했습니다.");
			room_list.setListData(room.getAllRoom());
		}
	}

	public void myEnterRoom(int roomNum) {
		RoomHandler room = new RoomHandler();
		chatGui.myEnterRoom(roomNum);
		mySocket.sendMessage("enterRoom/." + roomNum);
		room_list.setListData(room.getAllRoom());
		setVisible(false);

	}

	public void newRoom(int roomNum, String roomName, String school, int max, int inUser) {
		RoomHandler rh = new RoomHandler();
		rh.newRoom(roomNum, roomName, school, max, inUser);
		room_list.setListData(rh.getAllRoom());
	}

	public void enterRoom(int roomNum, int size) {
		RoomHandler rh = new RoomHandler();
		rh.enterRoom(roomNum, size);
		room_list.setListData(rh.getAllRoom());

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == exit_btn) {
			logout();
			System.exit(0);
		}

		if (e.getSource() == make_btn) {
			addRoomGui();
		}

		if (e.getSource() == add_btn) {
			String toFriend = "";
			toFriend = find_tf.getText();
			if (!toFriend.equals("")) {
				addFriend(toFriend);
			}
		}

		if (e.getSource() == addRoom_btn) {
			String roomName = "";
			roomName = name_tf.getText();
			if (!roomName.equals("")) {
				addRoom(roomName);
			}
		}

		if (e.getSource() == addExit_btn) {
			addRoom_frame.dispose();
		}

	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		logout();
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index,
			boolean isSelected, boolean cellHasFocus) {
		// TODO Auto-generated method stub
		JPanel contentPane = new JPanel();
		if (list == room_list) {
			RoomHandler room = (RoomHandler) value;
			contentPane.setName(Integer.toString(room.getRoomNum()));
			contentPane.setLayout(new BorderLayout());
			contentPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));

			JLabel roomNum = new JLabel(String.format("%5d", room.getRoomNum()));
			roomNum.setOpaque(false);
			roomNum.setFont((new Font("굴림", Font.BOLD, 16)));
			roomNum.setHorizontalAlignment(roomNum.RIGHT);

			JLabel roomName = new JLabel("방제 : " + room.getRoomName());
			roomName.setOpaque(false);
			roomName.setFont(new Font("굴림", Font.BOLD, 18));
			roomName.setHorizontalAlignment(roomName.CENTER);

			JLabel school = new JLabel("학교 : " + room.getSchool());
			school.setOpaque(false);
			school.setFont((new Font("굴림", Font.BOLD, 16)));
			school.setHorizontalAlignment(school.RIGHT);

			JLabel inUser = new JLabel(String.format("인원 : %3d / %3d", room.getInUser(), room.getMax()));
			inUser.setOpaque(false);
			inUser.setHorizontalAlignment(inUser.RIGHT);
			inUser.setFont((new Font("굴림", Font.BOLD, 16)));

			contentPane.add(roomNum, BorderLayout.WEST);
			contentPane.add(roomName, BorderLayout.NORTH);
			contentPane.add(school, BorderLayout.CENTER);
			contentPane.add(inUser, BorderLayout.SOUTH);

			if (isSelected) {
				contentPane.setBackground(room_list.getSelectionBackground());
				/*
				 * int roomNo = room.getRoomNum(); if (!room.isFull(roomNo)) {
				 * chatGui.myEnterRoom(roomNo); mySocket.sendMessage("enterRoom/." +
				 * room.getRoomNum()); room_list.setListData(room.getAllRoom());
				 * setVisible(false); } else { JOptionPane jp = new JOptionPane();
				 * jp.showMessageDialog(mainContentPane, roomNo + "번 방 입장에 실패했습니다.");
				 * room_list.setListData(room.getAllRoom()); }
				 */
			} else {
				contentPane.setOpaque(true);
			}

		}
		return contentPane;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getSource() == room_list) {
			int index = room_list.locationToIndex(e.getPoint());
			if (index != -1 && room_list.getSelectedIndex() == index) {
				RoomHandler room = RoomHandler.getAllRoom().get(index);
				myEnterRoom(room);
			}
		}

		if (e.getSource() == friend_list) {
			int index = friend_list.locationToIndex(e.getPoint());
			if (index != -1) {
				popupGui.show(friend_list, e.getX(), e.getY());
			}
		}
	}

}
