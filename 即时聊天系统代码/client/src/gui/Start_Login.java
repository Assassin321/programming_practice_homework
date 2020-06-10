package gui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import socket.Client;
import tool.InformationTransport;
import user.User;

class Start_Login extends JFrame implements ActionListener {
	//登录，启动clientUI
	//print 语句对程序的运行进行分析
	private static final long serialVersionUID = 1L;
	private JLabel bq_name, bq_pwd;
	private JButton login,regist;
	private JTextField text_name;
	private JPasswordField text_pwd;
	private JTabbedPane choose;
	private JPanel choose1, login_South;
	public void actionPerformed(ActionEvent e) {
		/*
		 * 如果点击了登录按钮 首先判断帐号或者密码是否为空 然后封装为CommandTranser对象 向服务器发送数据 服务器通过与数据库的比对
		 * 来验证帐号密码
		 */
		boolean a=(e.getSource() == login);
		boolean a1 =(e.getSource() == regist);
		if (a) {
			String username = text_name.getText().trim();
			String password = new String(text_pwd.getPassword()).trim();
			boolean eu = ("".equals(username) || username == null);
			if (eu) {
				JOptionPane.showMessageDialog(null, "请输入帐号！！");
				return;
			}
			boolean ep = ("".equals(password) || password == null);
			if (ep) {
				JOptionPane.showMessageDialog(null, "请输入密码！！");
				return;
			}
			User user = new User(username, password);
			InformationTransport message = new InformationTransport();
			message.setCmd("login");
			message.setData(user);
			message.setReceiver(username);
			message.setSender(username);
			
			// 实例化客户端 并且发送数据 这个client客户端 直到进程死亡 否则一直存在
			Client client = new Client();
			client.send(message);
			message = client.get();
			User users = (User) message.getData();
			
			if (message != null) {
				boolean b=message.isFlag();
				if (b) { 
					this.dispose();
					JOptionPane.showMessageDialog(null, "登陆成功！");
					// 显示好友列表界面
					new Friends(users, client); 
				} else {
					String result =message.getResult();
					JOptionPane.showMessageDialog(this, result);
				}
			}
				
		} else if(a1) {
			new Register();
		}

	}

	public Start_Login() { 
		login_South = new JPanel();
		login = new JButton("登录");
		login_South.add(login);
		choose = new JTabbedPane();
		choose1 = new JPanel();
		choose.add("普通用户", choose1);
		choose1.setLayout(new GridLayout(3, 3));
		int jc =JLabel.CENTER;
		bq_name = new JLabel("账号",jc );
		bq_pwd = new JLabel("密码", jc);
		regist = new JButton("注册账号");
		text_name = new JTextField();
		text_pwd = new JPasswordField();
		regist = new JButton("注册账号");
		regist.setForeground(Color.blue);
		choose1.setLayout(new GridLayout(3, 3));
		choose1.add(bq_name);
		choose1.add(text_name);
		choose1.add(bq_pwd);
		choose1.add(text_pwd);
		choose1.add(regist);
		String bc =BorderLayout.CENTER;
		add(choose,bc); 
		String bs=BorderLayout.SOUTH;
		add(login_South,bs);
		login.addActionListener(this);
		regist.addActionListener(this);
		setVisible(true);
		setBounds(340, 270, 300, 280);
		setResizable(false);
		int close =JFrame.EXIT_ON_CLOSE;
		setDefaultCloseOperation(close);
		setTitle("wechat");
	} 

	
	public static void main(String[] args) {
		new Start_Login();
	}
}
