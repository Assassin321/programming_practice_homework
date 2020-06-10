package socket;
import java.net.Socket;
public class Sockets {
//链接成功后保存的socket类
	private Socket s;//value
	private String name;//哈希map的key
	public Sockets() {
		super();
	}
	public Sockets(Socket socket, String name) {
		super();
		s = socket;
		this.name = name;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Socket getSocket()
	{
		return s;
	}
	public void setSocket(Socket socket)
	{
		s = socket;
	}
}
