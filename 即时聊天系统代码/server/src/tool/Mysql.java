package tool;
import java.sql.*;
public class Mysql {
//数据库操作类
	//print 语句对程序的运行进行分析
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
	private static final String DB_URL = "jdbc:mysql://localhost:3306/chatserver?useSSL=false&serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASSWORD = "553603102";
	private static Connection con = null;
	static
	{ 
	try{
        // 注册 JDBC 驱动
        Class.forName(JDBC_DRIVER);
    }catch(Exception e){
        // 处理 Class.forName 错误 
        e.printStackTrace();}
    }
	public static Connection getCon()
	{
		return con;
	}
	public static Connection getConnection()
	{
	 	if(con == null)
		{
			try {
				con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
				System.out.print("connect to mysql");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return con;
	}
}
