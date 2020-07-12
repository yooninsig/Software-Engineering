package Boundary;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Handler.FriendHandler;
import javax.swing.ListSelectionModel;

public class Chat extends JFrame implements ActionListener, WindowListener, MouseListener{
	
	private JPanel contentPane;
	private JTextField chat_tf; //메시지 입력 필드
	private JButton send_btn; //전송 버튼
	private JButton exit_btn; //나가기 버튼
	private JTextArea chat_area; //해당 방의 대화창
	private JList room_user; //유저리스트
	private JList room_friend; //방 친구목록
	private Vector<String> roomUser; //유저목록
	
	private int roomNum;
	private Main main;
	private MySocket mySocket;
	private Popup popup;

	public int getRoomNum() {
		return this.roomNum;
	}
	
	public JButton getExit(){
		return exit_btn;
	}
	
	public JList getFriendList() {
		return room_friend;
	}
	
	public Vector<String> getRoomUser(){
		return roomUser;
	}
	
	public Chat(Main main, MySocket mySocket){ //GUI 세팅 및 보여주기

		
		this.main = main;
		this.mySocket = mySocket;
		this.roomNum = 0;
		mySocket.setChat(this);
		
		roomUser = new Vector<>();
		room_user = new JList();
		room_user.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		addWindowListener(this);
		setBounds(100, 100, 720, 498);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		chat_tf  = new JTextField();
		chat_tf.setFont(new Font("굴림", Font.PLAIN, 13));
		chat_tf.setBounds(32, 402, 330, 29);
		contentPane.add(chat_tf);
		chat_tf.addActionListener(this);
		chat_tf.setColumns(10);
		
		send_btn = new JButton("\uC804\uC1A1");
		send_btn.setBounds(375, 403, 97, 29);
		contentPane.add(send_btn);
		send_btn.addActionListener(this);
		
		JLabel lblNewLabel = new JLabel("\uBC29\uC778\uC6D0 \uBAA9\uB85D");
		lblNewLabel.setBounds(504, 34, 129, 21);
		contentPane.add(lblNewLabel);
		
		exit_btn = new JButton("\uB098\uAC00\uAE30"); 
		exit_btn.setBounds(504, 405, 169, 23);
		exit_btn.addActionListener(this);
		contentPane.add(exit_btn);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(32, 34, 440, 358);
		contentPane.add(scrollPane_2);
		chat_area = new JTextArea();
		scrollPane_2.setViewportView(chat_area);
		chat_area.setFont(new Font("Monospaced", Font.BOLD, 14));
		chat_area.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(504, 246, 188, 149);
		contentPane.add(scrollPane);
		
		room_friend = new JList();
		room_friend.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		room_friend.setFont(new Font("굴림", Font.BOLD, 14));
		scrollPane.setViewportView(room_friend);
		FriendHandler fh = new FriendHandler();
		room_friend.setListData(fh.getAllFriend());
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(504, 65, 188, 133);
		contentPane.add(scrollPane_1);
		
		room_user.setFont(new Font("굴림", Font.BOLD, 14));
		scrollPane_1.setViewportView(room_user);
		
		
		JLabel lblNewLabel_1 = new JLabel("\uCE5C\uAD6C \uBAA9\uB85D");
		lblNewLabel_1.setBounds(504, 219, 97, 15);
		contentPane.add(lblNewLabel_1);
		
		room_friend.addMouseListener(this);

		popup = new Popup(main, this, mySocket);
		main.setPopup(popup);
		mySocket.setChat(this);
	}
	
	public void myEnterRoom(int roomNo) {
		this.roomNum = roomNo;
		chat_area.setText("");
		roomUser = new Vector<>();
		room_user.removeAll();
		setVisible(true);
	}

	public void exitRoom(String id) {
		for(String user : roomUser) {
			if(user.equals(id)) {
				roomUser.remove(user);
				break;
			}
		}
		room_user.setListData(roomUser);
	}
	
	public void enterRoom(String id) {
		roomUser.add(id);
		room_user.setListData(roomUser);
	}
	
	public void receiveChatting(String msg) {
		chat_area.append(msg + "\n");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == chat_tf){
			send_btn.doClick();
		}
		
		if(e.getSource() == send_btn){
			String msg = chat_tf.getText();
			chat_tf.setText("");
			mySocket.sendMessage("sendChatting/." + roomNum + "/." + msg);
		}
		
		if(e.getSource() == exit_btn){
			mySocket.sendMessage("exitRoom/." + roomNum);
			roomNum = 0;
			dispose();
			main.setVisible(true);
		}
	}
	@Override
	public void windowOpened(WindowEvent e) {}
	@Override
	public void windowClosing(WindowEvent e) {
		mySocket.sendMessage("exitRoom/." + roomNum);
		main.logout();
	}
	@Override
	public void windowClosed(WindowEvent e) {}
	@Override
	public void windowIconified(WindowEvent e) {}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowDeactivated(WindowEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getSource() == room_friend) {
			System.out.println("눌러서 잠금해제");
			int index = room_friend.locationToIndex(e.getPoint());
			if (index != -1) {
				popup.show(room_friend, e.getX(), e.getY());
			}
		}
	}
}
