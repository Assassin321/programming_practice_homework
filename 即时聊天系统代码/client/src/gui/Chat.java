package gui;
import user.User;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import socket.Client;
import socket.ClientThread;
import tool.InformationTransport;
public class Chat extends JFrame implements ActionListener {
	//聊天界面UI
	//print 语句对程序的运行进行分析
	private static final long serialVersionUID = 1L;
	private String friend;
	private Client client;
	private User owner;
	private ClientThread thread;// 接收信息
	private JTextArea chatUI;
	private JTextField txt_message;
	private JButton send_button;
	private JButton img_button;
	private JPanel panel;
	public Chat(User owners, String friends, Client clients) {
		owner = owners;
		friend = friends;
		client =clients; 
		BorderLayout blt = new BorderLayout();
		setLayout(blt); 
		panel = new JPanel();
		JButton sbut= new JButton ("发送信息");
		JButton imgbut = new JButton("选择图片");
		img_button = imgbut;
		send_button = sbut;
		txt_message = new JTextField(20);	
		System.out.print("111");	
		chatUI = new JTextArea();
		chatUI.setEditable(false);
		 JScrollBar jsb = new JScrollBar(JScrollBar.VERTICAL);
		chatUI.add(jsb);
		String bc = BorderLayout.CENTER;
		add(chatUI, bc);
		panel.add(txt_message);
		panel.add(send_button);
		panel.add(img_button);
		String bs= BorderLayout.SOUTH;
		add(panel,bs);
		send_button.addActionListener(this);
		setTitle(owner.getUsername() +" 与 " + friend + "聊天中");
		System.out.print(owner.getUsername() +" 与 " + friend + "聊天中");	
		setSize(499, 498);
		int close = JFrame.DISPOSE_ON_CLOSE;
		setDefaultCloseOperation(close);
		setLocationRelativeTo(null);
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				thread.setOnline(false);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				thread.setOnline(false);
			}
		});
		// 开启客户端接收信息线程
		thread = new ClientThread(client, chatUI,owner);
		thread.start();
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		boolean a =(arg0.getSource()==send_button);//发送信息图片
		if(a)
		{
			String name = owner.getUsername()+" 说：" ;
			String text =  txt_message.getText() + "\n";
			String words = name+text;
			chatUI.append(words);
			InformationTransport info =new InformationTransport();
			InformationTransport message = info;
		
			String cmd = "message";
	
			message.setCmd(cmd);
			String getu = owner.getUsername();
			message.setSender(getu);
			String f = friend;
			
			message.setReceiver(f);
			String data = txt_message.getText();
			message.setData(data);
			
			ArrayList<String> friends = owner.getFriend();
			message.setFriends(friends);
			client.send(message);
		
			txt_message.setText(null);
		}
		
	}

}
