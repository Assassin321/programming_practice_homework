package socket;
import javax.swing.*;//图形包

public class Start {
	//print 语句对程序的运行进行分析
	
	public static void main(String[] args) {
		new startServerThread().start();
		JOptionPane.showMessageDialog(null, "启动服务器");
		System.out.print("启动服务器成功");
	}

}

class startServerThread extends Thread
{   @Override
	public void run()
	{
		Server s = new Server();
		s.startService();
		System.out.print("启动服务器成功1111");
	}
}
