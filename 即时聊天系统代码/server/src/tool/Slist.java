package tool;
import java.net.Socket;
import java.util.*;

import socket.Sockets;
public class Slist {
//socket集合
	private static HashMap<String, Socket> map = new HashMap<String, Socket>();
	public static void addS(Sockets st)
	{
		map.put(st.getName(), st.getSocket());//入表
	}
	public static void deleteS(String name) {
		if(map.get(name) != null) {
			map.remove(name);
		}
		return;
	}
	public static Socket getS(String name){
		return map.get(name);
	}
	public static HashMap<String, Socket> getMap(){
		return map;
	}
}