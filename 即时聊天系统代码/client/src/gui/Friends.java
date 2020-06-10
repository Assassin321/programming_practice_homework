package gui;
import java.util.*;

import user.User;

import java.awt.*;
import javax.swing.*;
import socket.Client;
import socket.ClientThread;

import java.awt.event.*;
public class Friends extends JFrame implements ActionListener {
	//好友列表UI
	//print 语句对程序的运行进行分析
	private static final long serialVersionUID = 1L;
	private JPanel friend;
	private JScrollPane jsp_F;//下拉
	private User user;
	private JTabbedPane jtp;
	private Client c;
	private JButton addFriend;
	private JButton deleteFriend;

	public Friends(User user, Client client) {
		this.user= user;
		this.c= client;
		friend =new JPanel();
		jtp = new JTabbedPane();
		GridLayout friend_location =new GridLayout(50, 1, 4, 4);
		friend.setLayout(friend_location);
		addFriend = new JButton("添加好友");
		deleteFriend = new JButton("删除好友");
		System.out.print("fdsadasfds");	
		friend.add(addFriend);
		friend.add(deleteFriend);
		addFriend.addActionListener(this);
		deleteFriend.addActionListener(this);
		int numofFriend = user.getFriendsNum();//获取我的好友的数量
		final JLabel friendsname[];
		friendsname= new JLabel[numofFriend];
		String insert = new String();
		ArrayList<String> friendslist = new ArrayList<String>(user.getFriend());
		int num = numofFriend+1 - 1;
		System.out.print("111");	
		for (int i = 0; i < num; i++) {
			insert = (String)friendslist.get(i);
			System.out.print("1");	
			friendsname[i] = new JLabel(insert, JLabel.LEFT);
			System.out.print("test");	
			friendsname[i].addMouseListener(new MyMouseListener());
			System.out.print("2");	
			friend.add(friendsname[i]);
		}
		jsp_F = new JScrollPane (friend);
		jsp_F.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jtp.add("好友列表",jsp_F);
		add(jtp);
		String getu= user.getUsername();
		setTitle("欢迎"+getu);
		setSize(300, 595);
		setLocation(1090, 98);
		int close = JFrame.EXIT_ON_CLOSE;
		setDefaultCloseOperation(close);
		setResizable(false);
		setVisible(true);
	}



	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		boolean k=(arg0.getSource()== addFriend);
		boolean m =(arg0.getSource()== deleteFriend);
		if(k)//添加好友UI
		{
			new Add_Friend(user,c);
		}
		if(m)//删除好友UI
		{
			new Delete_Friend(user,c);
		}
	}
	
	class MyMouseListener extends MouseAdapter {//点击打开聊天界面UI

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			boolean a = (arg0.getClickCount() == 2);
			if (a) {
				JLabel jl= (JLabel) arg0.getSource();
				JLabel jlabel = jl;
				new Chat(user, jlabel.getText(), c);
			}
		}
	}


	
}
