package socket;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class Server {
//服务器
	//print 语句对程序的运行进行分析
	public void startService()
	{
		try {
			ServerSocket serversocket = new ServerSocket(8081);  
			Socket socket = null;
			while(true)//循环链接，每链接一个都用start方法化成进程
				{
				socket =  serversocket.accept();
				System.out.print("connect");
				ServerThread st = new ServerThread(socket);
				st.start();//自thread继承
				
						
				  
				}
			}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
				}
	}
}


