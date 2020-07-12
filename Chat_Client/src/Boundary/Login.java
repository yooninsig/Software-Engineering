package Boundary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame implements ActionListener {
	public Login() {
	}
	private Socket socket;
	private MySocket mySocket;
	private JButton login_btn = new JButton("로그인");
	private JTextField id_tf = new JTextField();
	private JTextField password_tf = new JPasswordField();
	private JButton contact_btn = new JButton("접속");
	private JTextField ip_tf = new JTextField();
	private JTextField port_tf = new JTextField();
	private JPanel loginContentPane	= new JPanel();
	private JPanel contactContentPane = new JPanel();
	private Main main;

	public void login() {

		setBounds(100, 100, 370, 540);
		
		loginContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(loginContentPane);
		loginContentPane.setLayout(null);
		
		login_btn.setBounds(245, 281, 80, 71);
		login_btn.setBorderPainted(false);
		login_btn.setFocusPainted(false);
		login_btn.setOpaque(false);
		loginContentPane.add(login_btn);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(42, 281, 36, 33);
		loginContentPane.add(lblNewLabel);
		
		id_tf.setBounds(88, 284, 145, 28);
		loginContentPane.add(id_tf);
		id_tf.setColumns(10);
		
		JLabel label = new JLabel("\uBE44\uBC00\uBC88\uD638");
		label.setBounds(30, 321, 54, 39);
		loginContentPane.add(label);
		
		password_tf.setBounds(88, 322, 145, 29);
		loginContentPane.add(password_tf);

		login_btn.addActionListener(this);
		id_tf.addActionListener(this);
		password_tf.addActionListener(this);
		
		this.setVisible(true);
	}
	

	public void contact() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 320, 300);
		contactContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contactContentPane);
		contactContentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("IP");
		lblNewLabel.setBounds(43, 50, 57, 15);
		contactContentPane.add(lblNewLabel);
		
		ip_tf.setBounds(43, 75, 218, 21);
		contactContentPane.add(ip_tf);
		ip_tf.setColumns(10);
		ip_tf.setText("211.222.57.28");
		
		JLabel lblNewLabel_1 = new JLabel("PORT");
		lblNewLabel_1.setBounds(43, 128, 57, 15);
		contactContentPane.add(lblNewLabel_1);
		
		port_tf.setBounds(43, 153, 218, 21);
		contactContentPane.add(port_tf);
		port_tf.setColumns(10);

		contact_btn.setBounds(43, 192, 218, 23);
		contactContentPane.add(contact_btn);
		
		contact_btn.addActionListener(this);
		ip_tf.addActionListener(this);
		port_tf.addActionListener(this);
		
		this.setVisible(true);
	}

	//	loginTry/.id/.password
	public void loginTry(String id, String password) {
		mySocket.sendMessage("loginTry/." + id + "/." + password);
	}
	
	public void acceptLogin(String id, String school) {
		main.setUserId(id);
		main.setSchool(school);
		main.setVisible(true);
		this.dispose();
	}
	
	//	failLogin/.confirm
	//	1 : 성공, 2 : 없는정보, 3 : 비밀번호, 4 : 접속중인 아이디
	public void failLogin(int confirm) {
		JOptionPane jp = new JOptionPane();
		 switch(confirm) {
		 case 4: jp.showMessageDialog(loginContentPane, "이미 접속중인 아이디 입니다.");
			 break;
		 case 2: jp.showMessageDialog(loginContentPane, "아이디 정보가 없습니다.");
		 	break;
		 default : jp.showMessageDialog(loginContentPane, "비밀번호가 일치하지 않습니다.");
		 }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == login_btn) {
			String id = "";
			String password = "";
			id = id_tf.getText();
			password = password_tf.getText();
			password_tf.setText("");
			if(!id.equals("") || !password.equals("")) {
				loginTry(id, password);
			}
		}
		
		if(e.getSource() == id_tf) {
			login_btn.doClick();
		}
		
		if(e.getSource() == password_tf) {
			login_btn.doClick();
		}
		
		if(e.getSource() == contact_btn) {
			String ip = "";
			ip = ip_tf.getText();
			String port = "";
			port = port_tf.getText();
			
			if(!(ip.equals("") || port.equals(""))) {
				mySocket = new MySocket();
				mySocket.connect(ip, port);
			}
			
			if(mySocket.isConnected()) {
				mySocket.userNetwork();
				mySocket.setLogin(this);
				contactContentPane.setVisible(false);
				main = new Main(mySocket);
				login();
			}
		}
		
		if(e.getSource() == ip_tf) {
			contact_btn.doClick();
		}
		
		if(e.getSource() == port_tf) {
			contact_btn.doClick();
		}
	}
}
