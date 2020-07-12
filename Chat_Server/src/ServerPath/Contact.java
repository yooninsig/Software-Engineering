package ServerPath;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Contact extends JFrame implements ActionListener, Runnable{
		
		public static void main(String[] args){
			new Contact();
		}
	
		private JPanel contentPane;
		private JTextField ip_tf = new JTextField(); //������ �ؽ�Ʈ �ʵ�
		private JTextField port_tf = new JTextField(); //��Ʈ �ؽ�Ʈ �ʵ�
		JButton contact_btn = new JButton("\uC811\uC18D"); //���� ��ư
		private JTextArea status_area = new JTextArea(); //���� ���� �ؽ�Ʈ �����
		private final JScrollPane scrollPane = new JScrollPane();
	  
		private ServerSocket server;
		private NetworkSocket netSocket;

		public Contact() { //Contact������ GUI�����ϰ� ���� �Լ�.
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 320, 500);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
	      
			JLabel lblNewLabel = new JLabel("IP");
			lblNewLabel.setBounds(43, 50, 57, 15);
			contentPane.add(lblNewLabel);
			ip_tf.setEditable(false);
			ip_tf.setText("\uC11C\uBC84\uC6A9 \uC785\uB2C8\uB2E4.");
	      
			ip_tf.setBounds(43, 75, 218, 21);
			contentPane.add(ip_tf);
			ip_tf.setColumns(10);
	      
			JLabel lblNewLabel_1 = new JLabel("PORT");
			lblNewLabel_1.setBounds(43, 128, 57, 15);
			contentPane.add(lblNewLabel_1);
	      
			port_tf.setBounds(43, 153, 218, 21);
			contentPane.add(port_tf);
			port_tf.setColumns(10);
	      
			contact_btn.setBounds(43, 192, 218, 23);
			contentPane.add(contact_btn);
			scrollPane.setBounds(43, 237, 218, 215);
	      
			contentPane.add(scrollPane);
			scrollPane.setViewportView(status_area);
			status_area.setEditable(false);
	      
			contact_btn.addActionListener(this);
			port_tf.addActionListener(this);
			
			this.setVisible(true); //GUI�� �����ְ�
	   }

	   public void addStatus(String msg){ //status_area�� �޽��� �߰�
	      status_area.append(msg + "\n");
	   }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == contact_btn) {
			 String port = port_tf.getText();
			 try {
				server = new ServerSocket(Integer.parseInt(port.trim()));
				addStatus("������ ���Ƚ��ϴ�. " + "��Ʈ : " + port);
				Thread th = new Thread(this);
				th.start();
				
			} catch (NumberFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		if(e.getSource() == port_tf) {
			contact_btn.doClick();
			}
		}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try{
				Socket userSocket = server.accept();
				addStatus(userSocket.getInetAddress() + " ����");
				addStatus("���� ���� : " + NetworkSocket.getAllSocket().size());
				NetworkSocket ns = new NetworkSocket(userSocket);
				ns.setContact(this);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	
}
