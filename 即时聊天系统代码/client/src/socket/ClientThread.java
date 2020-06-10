package socket;
import user.User;

import javax.swing.*;

import tool.InformationTransport;
public class ClientThread extends Thread{
//客户端多线程
	//print 语句对程序的运行进行分析
	private Client c;
	private JTextArea chat;//多行文本框，用作聊天框
	private boolean online = true;
	User user;
	public ClientThread(Client client , JTextArea chating,User user) {
		this.c= client;
		this.chat = chating;
		this.user = user;
		// TODO Auto-generated constructor stub
	}
	public boolean isOnline() 
	{
		return this.online; 
	}
	public void setOnline(boolean online)
	{
		this.online = online;
	}
	@Override public void run()//继承自thread.run
	{
		while(online)
		{
			InformationTransport message = c.get();//从服务器接收数据
			System.out.print(message.getCmd());
			System.out.print(message.getData());
			System.out.print(message.getReceive());
			System.out.print(message.getFriend());
			System.out.print(message.getResult());
			System.out.print(message.getSende());
			if(message != null)//非空,接收信息
			{
				if(message.isFlag())
				{
					boolean a1=("message".equals(message.getCmd()));
					boolean a2=("requeste_add_friend".equals(message.getCmd()));
					boolean a3=("accept_add_friend".equals(message.getCmd()));
					boolean a4=("updatefriendlist".equals(message.getCmd())) ;
					boolean a5=("refuse_to_add".equals(message.getCmd()));
					boolean a6=("request_delete_friend".equals(message.getCmd()));
					if(a1) {
						if(message.isFlag() == false) {
							JOptionPane.showMessageDialog(null, message.getResult()); 
							return;
						}	
							String name =message.getSende()+" 说：" ;
							String text = (String) message.getData()+ "\n";
							String words = name+text;
							chat.append(words);	
						return;
					} 
						if(a2) {
							if(message.isFlag() == false) {
								JOptionPane.showMessageDialog(null, message.getResult()); 
								return;
							}
							else {
							String sendername = message.getSende();
							int accept = JOptionPane.showConfirmDialog(null, "是否同意" + sendername + "的好友请求", "好友请求", JOptionPane.YES_NO_OPTION);
							if(accept == 0) {
								message.setCmd("accept_add_friend");
							} else {
								message.setCmd("refuse_add_friend");			
							}
							String getu = user.getUsername();
							message.setSender(getu);
							message.setReceiver(sendername);
							c.send(message);
							
							return;
						}
					}
						if(a3) {
								//JOptionPane.showMessageDialog(null, cmd.getResult());
								InformationTransport newmessage= new InformationTransport();
								String cmd = "updatefriendlist";
								String getu =user.getUsername();
								newmessage.setCmd(cmd);
								newmessage.setReceiver(getu);
								newmessage.setSender(getu); 
								newmessage.setData(user);
								c.send(newmessage);
								System.out.print(message.getCmd());
								System.out.print(message.getData());
								System.out.print(message.getReceive());
								System.out.print(message.getFriend());
								System.out.print(message.getResult());
								System.out.print(message.getSende());
								return;
					}
						if(a4){
							if(message.isFlag() == false) {
								JOptionPane.showMessageDialog(null, message.getResult()); 
								return;
							}
							System.out.print(user.getFriend());
							User tmp = (User)message.getData();
							user.setFriendsList(tmp.getFriend());
							System.out.print(message.getFriend());
			
							
							return;
						}
						if(a5){
							JOptionPane.showMessageDialog(null, message.getResult());
							return;
						}
						if(a6)
						{
							InformationTransport newmessage= new InformationTransport();
							String cmd = "request_delete_friend";
							newmessage.setCmd(cmd);
							String getu =user.getUsername();
							newmessage.setReceiver(getu);
							newmessage.setSender(getu); 
							newmessage.setData(user);
							c.send(newmessage);
							System.out.print(message.getCmd());
							System.out.print(message.getData());
							System.out.print(message.getReceive());
							System.out.print(message.getFriend());
							System.out.print(message.getResult());
							System.out.print(message.getSende());
							JOptionPane.showMessageDialog(null, message.getResult());
							return;
						}
					JOptionPane.showMessageDialog(chat, message.getResult());//报错
				} 
			else {
				JOptionPane.showMessageDialog(chat, message.getResult());//报错
				}
			}
		}

	}


}