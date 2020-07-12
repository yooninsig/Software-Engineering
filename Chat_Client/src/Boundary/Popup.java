package Boundary;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Handler.FriendHandler;
import Handler.RoomHandler;

public class Popup extends JPopupMenu implements ActionListener {

	private JFrame noteFrame;
	private JPanel noteContentPane;
	private JButton close_btn;
	private JButton send_btn;
	private JTextArea note_area;
	private JLabel sender_label;
	private JMenuItem invite;
	private JMenuItem note;

	private JFrame mainFrame;
	private JPanel inviteContentPane;
	private JTextField invite_tf;
	private JButton agree_btn;
	private JButton reject_btn;

	private MySocket mySocket;
	private Main main;
	private Chat chat;

	public Popup(Main main, Chat chat, MySocket mySocket) {
		this.mySocket = mySocket;
		this.main = main;
		this.chat = chat;
		mySocket.setPopup(this);

		invite = new JMenuItem("초대 하기");
		add(invite);
		invite.addActionListener(this);

		note = new JMenuItem("쪽지 보내기");
		add(note);
		note.addActionListener(this);

	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}
	
	public void errorNote(String msg) {
		JOptionPane jp = new JOptionPane();
		jp.showMessageDialog(noteFrame, msg);
	}

	public void noteGui(boolean send, String id, String note) {

		noteFrame = new JFrame("메시지");
		noteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		noteFrame.setBounds(100, 100, 450, 330);
		noteContentPane = new JPanel();
		noteContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		noteFrame.setContentPane(noteContentPane);
		noteContentPane.setLayout(null);

		sender_label = new JLabel();
		sender_label.setFont(new Font("굴림", Font.BOLD, 14));
		sender_label.setBounds(22, 10, 245, 22);
		noteContentPane.add(sender_label);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 38, 378, 176);
		noteContentPane.add(scrollPane);

		note_area = new JTextArea();
		note_area.setFont(new Font("굴림", Font.BOLD, 13));
		note_area.setEnabled(false);
		note_area.setFont(new Font("Monospaced", Font.PLAIN, 15));
		scrollPane.setViewportView(note_area);

		close_btn = new JButton("닫기");
		close_btn.setFont(new Font("굴림", Font.BOLD, 13));
		close_btn.setBounds(303, 221, 97, 30);
		noteContentPane.add(close_btn);
		close_btn.addActionListener(this);

		if (send) {
			send_btn = new JButton("\uBCF4\uB0B4\uAE30");
			send_btn.setFont(new Font("굴림", Font.BOLD, 13));
			send_btn.setBounds(195, 221, 97, 30);
			noteContentPane.add(send_btn);
			note_area.enable(send);
			sender_label.setText(id + "님에게 쪽지");

			ActionListener sb = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if (e.getSource() == send_btn) {
						mySocket.sendMessage("sendNote/." + "/." + id + "/." + note_area.getText());
						noteFrame.dispose();
					}
				}
			};
			send_btn.addActionListener(sb);
		} else {
			note_area.setEnabled(send);
			sender_label.setText(id + "님에게 온 쪽지");
			note_area.append(note);
		}
		ActionListener cb = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				noteFrame.dispose();
			}
		};
		close_btn.addActionListener(cb);

		noteFrame.setVisible(true);
	}

	public void receiveNote(String id, String msg) {
		noteGui(false, id, msg);
	}

	public void sendNote(String toId) {
		noteGui(true, toId, "");
	}

	public void receiveInvite(String fromId, int roomNum) {
		mainFrame = new JFrame("초대 받았습니다.");
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainFrame.setBounds(100, 100, 300, 250);
		inviteContentPane = new JPanel();
		inviteContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainFrame.setContentPane(inviteContentPane);
		inviteContentPane.setLayout(null);

		agree_btn = new JButton("\uC218\uB77D");
		agree_btn.setBounds(22, 95, 97, 23);
		inviteContentPane.add(agree_btn);

		reject_btn = new JButton("\uAC70\uC808");
		reject_btn.setBounds(157, 95, 97, 23);
		inviteContentPane.add(reject_btn);

		invite_tf = new JTextField();
		invite_tf.setHorizontalAlignment(SwingConstants.CENTER);
		invite_tf.setText(fromId + "\uB2D8\uC774 \uCC44\uD2F0\uBC29\uC5D0 \uCD08\uB300\uD558\uC5FF\uC2B5\uB2C8\uB2E4.");
		invite_tf.setBounds(12, 23, 256, 62);
		inviteContentPane.add(invite_tf);
		invite_tf.setColumns(10);

		ActionListener ac = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == agree_btn) {
					RoomHandler rh = new RoomHandler();
					rh = rh.getRoom(roomNum);
					main.myEnterRoom(rh);
					mainFrame.dispose();
				}
				if (e.getSource() == reject_btn) {
					mainFrame.setVisible(false);
				}
			}
		};
		agree_btn.addActionListener(ac);
		reject_btn.addActionListener(ac);

		mainFrame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == invite) {
			int index = chat.getFriendList().getSelectedIndex();
			if (index == -1) {
				return;
			}
			FriendHandler fh = FriendHandler.getAllFriend().get(index);
			mySocket.sendMessage("inviteFriend/." + fh.getFriendId() + "/." + chat.getRoomNum());
		}

		if (e.getSource() == note) {
			int index = main.getFriendList().getSelectedIndex();
			if (index != -1) {
				FriendHandler fh = FriendHandler.getAllFriend().get(index);
				sendNote(fh.getFriendId());
			}
		}

		if (e.getSource() == reject_btn) {

		}

	}

}
