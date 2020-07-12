package Boundary;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;

public class Test extends JFrame {

	private JFrame noteFrame;
	private JPanel noteContentPane;
	private JButton notExit_btn;
	private JTextArea note_area;
	private JLabel sender_label;
	private JButton send_btn;
	
	public Test() {
		noteFrame = new JFrame("ÂÊÁö ¹ÞÀ¸¼¼¿ä.");
		noteFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		noteFrame.setBounds(100, 100, 440, 300);
		noteContentPane = new JPanel();
		noteContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		noteFrame.setContentPane(noteContentPane);
		noteContentPane.setLayout(null);
		
		sender_label = new JLabel();
		sender_label.setFont(new Font("±¼¸²", Font.BOLD, 14));
		sender_label.setBounds(22, 10, 245, 22);
		noteContentPane.add(sender_label);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 38, 378, 176);
		noteContentPane.add(scrollPane);
		
		note_area = new JTextArea();
		note_area.setFont(new Font("Monospaced", Font.PLAIN, 15));
		scrollPane.setViewportView(note_area);
		
		notExit_btn = new JButton("´Ý±â");
		notExit_btn.setFont(new Font("±¼¸²", Font.BOLD, 13));
		notExit_btn.setBounds(303, 221, 97, 30);
		noteContentPane.add(notExit_btn);
		
		send_btn = new JButton("\uBCF4\uB0B4\uAE30");
		send_btn.setFont(new Font("±¼¸²", Font.BOLD, 13));
		send_btn.setBounds(195, 221, 97, 30);
		noteContentPane.add(send_btn);
	}
}
