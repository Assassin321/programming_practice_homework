package socket;
import java.io.*;
import java.net.*;
import javax.swing.*;

import tool.InformationTransport;
public class Client {
	//客户端
	//print 语句对程序的运行进行分析
	private String host = "127.0.0.1";//待定
	private int port = 8081;//待定
	private Socket s;
	public Client() {
		// TODO Auto-generated constructor stub
		try {
			s = new Socket(host,port);  
			System.out.print("45646");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "服务端未开启");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "服务端未开启");
		}
		
		
	}
	public void send(InformationTransport message)
	{
		ObjectOutputStream oos = null;
		try {
			if (s == null)
				return;
			oos = new ObjectOutputStream(s.getOutputStream());
			System.out.print("text2");
			oos.writeObject(message);
			System.out.print(message);
			System.out.print(message.getCmd());
			System.out.print(message.getData());
			System.out.print(message.getReceive());
			System.out.print(message.getFriend());
			System.out.print(message.getResult());
			System.out.print(message.getSende());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "服务端未开启");
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "服务端未开启");
		} 
		
	}
	public InformationTransport get()
	{
		InformationTransport message = null;
		ObjectInputStream ois =null;
		System.out.print("text1");
		if (s == null)
			return null;
		try {
			ois =new ObjectInputStream(s.getInputStream());
			System.out.print("text2");
			 message = (InformationTransport)ois.readObject();
			System.out.print(message.getCmd());
			System.out.print(message.getData());
			System.out.print(message.getReceive());
			System.out.print(message.getFriend());
			System.out.print(message.getResult());
			System.out.print(message.getSende());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}catch (ClassNotFoundException e) {
			e.printStackTrace(); 
			return null;
		}
		boolean a= ("message".equals(message.getCmd()));
		if (a)
			System.out.println((String) message.getData());
		return message;
	}
	public Socket getSocket()
	{
		return s;
	}
	public void setSocket(Socket  socket)
	{
		this.s = socket;
	} 
}
