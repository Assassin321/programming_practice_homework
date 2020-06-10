package socket;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import service.Userdata;
import tool.InformationTransport;
import tool.Slist;
import user.User;
public class ServerThread extends Thread  {
//多线程操作
	//print 语句对程序的运行进行分析
	private Socket s;
	
	public ServerThread(Socket socket) {
		// TODO Auto-generated constructor stub
		this.s= socket;
	}
	@Override
	public void run()//继承自thread.run
	{
		ObjectOutputStream oos = null;
		ObjectInputStream ois =null; 
		ObjectOutputStream oos1 = null; 
		//客户端发来的数据和要发出的数据
		while(s!=null)//存在
		{
			try { 
				ois = new ObjectInputStream(s.getInputStream());
				InformationTransport message = (InformationTransport) ois.readObject();
				if ("checkregist".equals(message.getCmd())) //验证是否注册
				{
					Userdata userservice = new Userdata();
					User user = (User) message.getData();//客户端发来的信息
					message.setFlag(userservice.registerUserExist(user));
					System.out.print(message.getCmd());
					System.out.print(message.getData());
					System.out.print(message.getReceive());
					System.out.print(message.getFriend());
					System.out.print(message.getResult());
					System.out.print(message.getSende());
					if(message.isFlag())//判断注册过
					{
						message.setResult("已注册");
					}
					else
					{
						System.out.println("验证成功\n");
						System.out.print("允许注册");
						
					}	
				}
				boolean a =("login".equals(message.getCmd()));
				boolean b =("regist".equals(message.getCmd()));
				boolean c =("message".equals(message.getCmd()));
				boolean d =("requeste_add_friend".equals(message.getCmd()));
				boolean e =("accept_add_friend".equals(message.getCmd()));
				boolean f =("updatefriendlist".equals(message.getCmd()));
				boolean g = ("refuse_to_add".equals(message.getCmd()));
				boolean h =("request_delete_friend".equals(message.getCmd()));
				boolean i =("message".equals(message.getCmd()));
				boolean j =("login".equals(message.getCmd()));
				boolean k = ("accept_add_friend".equals(message.getCmd()));
				boolean l = ("checkregist".equals(message.getCmd()));
				boolean m = ("regist".equals(message.getCmd()));
				boolean n = ("requeste_add_friend".equals(message.getCmd()));
				boolean o = ("request_delete_friend".equals(message.getCmd()));
				boolean p = ("refuse_to_add".equals(message.getCmd()));
				boolean q = ("updatefriendlist".equals(message.getCmd()));			
				if (a) {
					Userdata userservice =new Userdata();
					User user = (User) message.getData();//客户端发来的信息
					message.setFlag(userservice.existUser(user));
				
					if(message.isFlag()==true)
					{
						Sockets socketthread = new Sockets();
						socketthread.setName(message.getSende());//发送方作为socket类的名字
						socketthread.setSocket(s);
						Slist.addS(socketthread);
						message.setData(userservice.getFriendsList(user));
						message.setResult("登录成功");
				
					}
					else message.setResult("登录失败");
					
					
				}
				
				if (b) {//注册请求
					Userdata userservice = new Userdata();
					User user = (User) message.getData();//客户端发来的信息
					message.setFlag(userservice.registerUser(user));
	
					if(message.isFlag()) {
						ArrayList<String> friendlist= new	ArrayList<String> ();
						friendlist.add(message.getSende());
						user.setFriendsList(friendlist);
						Sockets socketthread = new Sockets();
						socketthread.setName(message.getSende());//发送方作为socket类的名字
						socketthread.setSocket(s);
						Slist.addS(socketthread);
						message.setData(userservice.getFriendsList(user));
						message.setResult("注册成功");
					
					} else {
						message.setResult("注册失败");
					}
				}
				if (c) {
					// 如果要发送的用户在线 发送信息
					if (Slist.getS(message.getReceive()) != null)//存在
					{
						message.setFlag(true);
						
					} else {
						message.setFlag(false);
						message.setResult("用户离线"); 
					}
				}
				if(d) {
					//添加好友
					if(Slist.getS(message.getReceive()) != null) {
						message.setFlag(true);
						message.setResult("对方收到请求");
					
					} else {
						message.setFlag(false);
						message.setResult("用户不在线");
					}
				}
				
				//同意添加好友请求
				if(e) {
					Userdata userservice = new Userdata();
					message.setFlag(userservice.addFriend(message.getReceive(), message.getSende()));
				
					if(message.isFlag()) {
						message.setResult("好友添加成功");
					} else {
						message.setResult("已经成为好友");
					}
				} 
				
				//更新朋友列表
				if(f) {
					Userdata userservice = new Userdata();
					User user = (User)message.getData();
					message.setCmd("updatefriendlist");
					
					user = userservice.getFriendsList(user);//获取user用户的朋友列表
					message.setFlag(true);
					message.setData(user);
					
				}
				
				//拒绝添加好友
				if(g) {
					//检查是否在线
					if(Slist.getS(message.getReceive()) != null) {
						message.setFlag(true);
						message.setResult("对方拒绝");
					
					} else {
						message.setFlag(false);
						message.setResult("对方不在线");
					}
				}
				if(h)
						{
						Userdata userservice = new Userdata();
						message.setFlag(userservice.deleteFriend(message.getReceive(), message.getSende()));
					
						if(message.isFlag()) {
							message.setResult("删除成功");
						} 
						}
				if(i)//发消息 
				{
					if(message.isFlag()) 
					{
					oos= new ObjectOutputStream( Slist.getS( message.getReceive()).getOutputStream() );//get socket OutputStream 
					}
					else {
						//JOptionPane.showMessageDialog(null,  "对方不在线，请稍后再发！");
						message.setCmd("not_online");
						String sender = message.getSende();
						message.setReceiver(sender);
						oos = new ObjectOutputStream(Slist.getS(message.getSende()).getOutputStream());
					
						
					}
				}
				
				if(j)
				{
					oos = new ObjectOutputStream(s.getOutputStream());//发送给本人
				}
				if (k) {
					//无论是否成功插入数据库都要将结果反馈，但有可能最初请求的客户下线了
					oos = new ObjectOutputStream(s.getOutputStream());
					if(Slist.getS(message.getReceive()) != null) {
						oos1 = new ObjectOutputStream(Slist.getS(message.getReceive()).getOutputStream());
					
					}
				}
				
				if (l) {
					oos = new ObjectOutputStream(s.getOutputStream());
				}
				
				
				if (m) {
	
					oos = new ObjectOutputStream(s.getOutputStream());
					
				}
				if (n) {
					//在线，将请求发给receiver
					if(message.isFlag()) {
						oos = new ObjectOutputStream(Slist.getS(message.getReceive()).getOutputStream());
					} else {
						//不管在不在线都要向发送方提示消息发送成功
						oos1 = new ObjectOutputStream(s.getOutputStream());
					}
				}
				if(o){
					//在线，将请求发给receiver
					
						oos1 = new ObjectOutputStream(s.getOutputStream());
					
				}
				if (p) {
					if(message.isFlag()) {
						oos = new ObjectOutputStream(Slist.getS(message.getReceive()).getOutputStream());
					}else {
						oos1 = new ObjectOutputStream(s.getOutputStream());
					}
				}
				if("updatefriendlist".equals(message.getCmd())) {
					oos = new ObjectOutputStream(s.getOutputStream());
				}
				//拒绝添加好友请求将数据发送给 receiver
				oos.writeObject(message);
			} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 catch (IOException e) {
				// TODO Auto-generated catch block
				 s =null;
				e.printStackTrace();
			} 
			
		}
		
	}


}
