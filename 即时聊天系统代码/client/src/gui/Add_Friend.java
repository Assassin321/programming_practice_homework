package gui;
import user.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

import socket.Client;
import tool.InformationTransport;
public class Add_Friend extends JFrame implements ActionListener {
	//加好友UI
	//print 语句对程序的运行进行分析
	private static final long serialVersionUID = 1L;
	private JFrame addfriend;
	private JTextField firendUsername;
	private JButton sendFriend;
	private User user;// 当前用户
	private Client client;// 客户端 
	public Add_Friend( User owner, Client c) {
		this.client =c;
		this.user = owner;
		this.addfriend = new JFrame("添加好友");
		this.addfriend.setSize(400,300);
		this.addfriend.setLocation(100,45);
		this.addfriend.setDefaultCloseOperation(2);
		JPanel messagePanel = new JPanel();
		JPanel typePanel =new JPanel();
		Container contain = this.addfriend.getContentPane();
		GridLayout contain_location=new GridLayout(1,2);
		contain.setLayout(contain_location);//设置用来放置信息的容器
		contain.add(messagePanel);
		contain.add(typePanel);
		GridLayout messagePanel_location =new GridLayout(4, 1, 0, 4);
		GridLayout typePanel_location =new GridLayout(5, 1, 0, 5);
		messagePanel.setLayout(messagePanel_location);
		typePanel.setLayout(typePanel_location);
		JLabel friendname = new JLabel("好友ID");
		messagePanel.add(friendname);
		firendUsername = new JTextField(10);
		typePanel.add(firendUsername);
		addfriend.setVisible(true);
		sendFriend = new JButton ("发送");
		messagePanel.add(sendFriend);
		this.sendFriend.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		boolean a = (e.getSource() == sendFriend);//发送给好友
		if(a){
			String name = firendUsername.getText().trim();
			if("".equals(name) || name == null) {
				JOptionPane.showMessageDialog(null, "请输入账号！！");
				return;
			}
			InformationTransport message = new InformationTransport();
		
			message.setCmd("requeste_add_friend");//请求添加朋友
			String data =name;
			message.setData(data);
			message.setReceiver(data);
			
			String getu = user.getUsername();
			message.setSender(getu);		
			client.send(message); //发送数据

			this.dispose(); 
		}

	}
}
