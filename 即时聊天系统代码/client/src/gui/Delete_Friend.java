package gui;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import socket.Client;
import tool.InformationTransport;
import user.User;

public class Delete_Friend extends JFrame implements ActionListener {
	//删除好友UI
	//print 语句对程序的运行进行分析

	private static final long serialVersionUID = 1L;
	private JFrame addfriend;
	private JTextField firendUsername;
	private JButton deleteFriend;
	private User user;// 当前用户
	private Client client;// 客户端 
	public Delete_Friend( User owner, Client c) {
		this.client =c;
		this.user = owner;
		this.addfriend = new JFrame("删除好友");
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
		deleteFriend = new JButton ("删除");
		messagePanel.add(deleteFriend);
		this.deleteFriend.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == deleteFriend){//删除好友
			String name = firendUsername.getText().trim();
			if("".equals(name) || name == null) {
				JOptionPane.showMessageDialog(null, "请输入账号！！");
				return;
			}
			InformationTransport message = new InformationTransport();
			
			message.setCmd("request_delete_friend");//请求添加朋友
			
			String data =name;
			message.setData(data);
			message.setReceiver(data);
			String getu = user.getUsername();
			message.setSender(getu);		
			client.send(message); //发送数据
			System.out.print(message.getCmd());
			System.out.print(message.getData());
			System.out.print(message.getReceive());
			System.out.print(message.getFriend());
			System.out.print(message.getResult());
			System.out.print(message.getSende());
			this.dispose(); 
		}

	}
}
