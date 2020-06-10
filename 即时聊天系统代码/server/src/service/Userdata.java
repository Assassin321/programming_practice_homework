package service;
import java.util.ArrayList;

import tool.Mysql;

import java.sql.*;

import user.User;
public class Userdata {
//对客户端信息进行的处理
	//print 语句对程序的运行进行分析
	public Userdata() {
		// TODO Auto-generated constructor stub
	}
	public boolean existUser(User user)
	{ 
		PreparedStatement pStatement = null;//mysql的api 
		Connection con = null;//与数据库建立会话
		ResultSet rs = null;//结果返回
		con = Mysql.getConnection();//链接到数据库
		String mysql =  "select * from user where username=? and password =?";
		try {
			pStatement = con.prepareStatement(mysql);
			pStatement.setString(1, user.getUsername());//查询用户名和密码
			pStatement.setString(2, user.getPassword());
			System.out.print(mysql);
			rs = pStatement.executeQuery();
			boolean c =(rs.next()== true);
			if (c) {//next是rs的迭代放法，true就证明user exist
				user.setPassword(rs.getString(2));
				user.setPhoneNumber(rs.getString(4));
				user.setgender(rs.getString(3));
				return true;//可登录
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {// 查询完毕关闭
				boolean a =(pStatement != null);
				boolean b =(rs != null);
				if (a)
					pStatement.close();
				if (b) 
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return false;
	}
	public User getFriendsList(User user) {
		PreparedStatement pStatement = null; 
		Connection conn = null; 
		ResultSet rs = null; 
		conn = Mysql.getConnection();
		String sql = "select * from " + user.getUsername() + "_friends";
		ArrayList<String> friendslist = new ArrayList<String>(); 
		
		try {
			
			pStatement = conn.prepareStatement(sql);
			rs = pStatement.executeQuery();
			System.out.print(sql);
			int count = 0;
			while(rs.next()) {
				friendslist.add(rs.getString(1));	//获取好友name
				count++;
			}
			user.setFriendsNum(count);
			user.setFriendsList(friendslist);
			return user;
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				boolean a =(pStatement != null);
				boolean b =(rs != null);
				if(b) {
					rs.close();
				}
				if(a) {
					pStatement.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}
	
	public boolean registerUserExist(User user) {
		Connection con = null;
		PreparedStatement pStatement= null;
		ResultSet rs = null;
		con = Mysql.getConnection();//链接到数据库
		String mysql =  "select * from user where username=?";//用户名重复
		try {
			pStatement = con.prepareStatement(mysql);
			pStatement.setString(1, user.getUsername());
			System.out.print(mysql);
			rs = pStatement.executeQuery();
			boolean a =(rs.next()== true);
			if (a) {//next是rs的迭代放法，true就证明user exist
				return true;//不可注册
			}
			else {
				String creatfriendstabsql = "CREATE TABLE " + user.getUsername() + "_friends " + "( name VARCHAR(45) NOT NULL)";
				PreparedStatement pStatement0 = null;
				pStatement0=con.prepareStatement(creatfriendstabsql);
				pStatement0.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {// 查询完毕关闭
				boolean a =(pStatement != null);
				boolean b =(rs != null);
				if (a)
					{pStatement.close();}
				if (b) 
					{rs.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			return false;
			
	}
	public boolean registerUser(User user) {
		Mysql mysql = new Mysql();
		mysql.getConnection();//链接到数据库
		PreparedStatement pStatement= null;
		String sql2 = "insert into " + user.getUsername() + "_friends (name) values(?)";
		String sql = "insert into user(username,password,gender,phoneNumber) values (?,?,?,?)";
		try {
			pStatement = mysql.getCon().prepareStatement(sql);
			
			pStatement.setString(1, user.getUsername());
	
			pStatement.setString(2, user.getPassword());
		
			pStatement.setString(3, user.getgender());
			
			pStatement.setString(4, user.getPhoneNumber());
	
			pStatement.executeUpdate(); 
			PreparedStatement pStatement1 = null;
			pStatement1=mysql.getCon().prepareStatement(sql2);

			pStatement1.setString(1, user.getUsername());
		
			int i =pStatement1.executeUpdate();
			System.out.print(i);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				boolean a =(pStatement != null);
	
				if (a)
					pStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
	//添加好友
		public boolean addFriend(String sender, String receiver) {
			PreparedStatement pStatement1 = null; 
			PreparedStatement pStatement2 = null; 
			Connection conn = null; 
			conn = Mysql.getConnection();
			String sql1 = "insert into " + sender + "_friends (name) values(?)";
			String sql2 = "insert into " + receiver + "_friends (name) values(?)";
			try {
				
			pStatement1 = conn.prepareStatement(sql1);
	
			pStatement2 = conn.prepareStatement(sql2);

			pStatement1.setString(1, receiver);
			
			pStatement2.setString(1, sender);
			
			pStatement1.executeUpdate();
		
			pStatement2.executeUpdate();

				
			} catch(SQLException e) {
				e.printStackTrace();
			} finally{
				try {	
					boolean a =(pStatement1 != null);
					boolean b =(pStatement2 != null);
					if(a) {
						pStatement1.close();
					}
					if(b) {
						pStatement2.close();
					}
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
			return false;
		}
		public boolean deleteFriend(String sender, String receiver) {
			PreparedStatement pStatement1 = null; 
			PreparedStatement pStatement2 = null; 
			Connection conn = null; 
			conn = Mysql.getConnection();
			String sql1 = "delete from "+sender+ "_friends where name = ?";
			String sql2 = "delete from " + receiver + "_friends where name = ?";
			try {
				
			pStatement1 = conn.prepareStatement(sql1);

			pStatement2 = conn.prepareStatement(sql2);

			pStatement1.setString(1, receiver);

			pStatement2.setString(1, sender);

			pStatement1.executeUpdate();
			
			pStatement2.executeUpdate();
		
				
			} catch(SQLException e) {
				e.printStackTrace();
			} finally{
				try {
					boolean a =(pStatement1 != null);
					boolean b =(pStatement2 != null);
					if(a) {
				
						pStatement1.close();
					}
					if(b) {
			
						pStatement2.close();
					}
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
			return false;
		}
	
	
}
