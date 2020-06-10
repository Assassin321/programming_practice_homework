package gui;
import user.User;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;//GUI

import socket.Client;
import tool.InformationTransport;
public class Register extends JFrame implements ActionListener,ItemListener {
	//注册UI
	//print 语句对程序的运行进行分析
	private static final long serialVersionUID = 1L;
	private JButton username;
	private JTextField phoneNumber;//输入槽
	private JPasswordField password;
	private JPasswordField double_psw;//密码输入槽
	private JButton finish;
	private JRadioButton male,female;
	private JFrame registerUI;//注册UI 
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == finish)
		{
			String psws = new String(password.getPassword()).trim();
			System.out.print("test");
			String uname = username.getText().trim();
			System.out.print("test");
			String double_psws =  new String(double_psw.getPassword()).trim();
			System.out.print("test");
			String genders = "";
			String cellNumber = phoneNumber.getText().trim();
			System.out.print("test");
			boolean m =male.isSelected();
			boolean w =female.isSelected();
			System.out.print("test");
			if(w) {
				genders = female.getText();
				}
			if(m) {
				genders = male.getText();
				}
			boolean a1 =("".equals(uname) || uname == null);
			boolean a2 =("".equals(psws) || psws ==null);
			boolean a3 =("".equals(double_psws) || double_psws ==null);
			boolean a4 =("".equals(cellNumber) || cellNumber ==null);
			boolean a5 =(!psws.equals(double_psws));
			if(a1)
			{
				JOptionPane.showMessageDialog(null,"用户名为空");
				return;
			}
			if(a2)
			{
				JOptionPane.showMessageDialog(null,"请输入密码");
				return;
			}
			if(a3)
			{
				JOptionPane.showMessageDialog(null,"请再次输入密码");
				return;
			}
			if(a4)
			{
				JOptionPane.showMessageDialog(null,"电话号码不得为空");
				return;
			}
			if(a5)
			{
				JOptionPane.showMessageDialog(this,"两次输入密码不同");
				double_psw.setText("");
				this.setVisible(false);
			}
			else {//前序已经验证完毕，验证登录注册
				User user = new User(uname,psws,genders,cellNumber);
				InformationTransport message = new InformationTransport();
				message.setCmd("checkregist");
				message.setData(user);
				message.setReceiver(uname);
				message.setSender(uname);
			
				Client client = new Client();
				client.send(message);
				message = client.get();
				if(message != null)
				{if(message.isFlag()==false)
					{
					String cmd ="regist";
						message.setCmd(cmd);
						message.setData(user);
						message.setReceiver(uname);
						message.setSender(uname);
						Client client1 = new Client();
						client1.send(message);
						message = client1.get();
					

					}
					
						if(message.isFlag())
						{
							this.dispose();
							JOptionPane.showMessageDialog(null,"注册成功");
							registerUI.setVisible(false);
							new Start_Login();//登录
						}
					}
				else {
					JOptionPane.showMessageDialog(null,"账号已存在");
					registerUI.setVisible(false);
					new Register();
				}
			}
			
			
		}
		
	}
	public Register() {
		// TODO Auto-generated constructor stub
		this.registerUI = new JFrame("账号注册界面");//建立界面
		this.registerUI.setSize(500,500);  
		this.registerUI.setLocation(200,145);
		this.registerUI.setDefaultCloseOperation(EXIT_ON_CLOSE);//关闭
		JPanel messagePanel = new JPanel();
		JPanel typePanel =new JPanel();
		Container contain = this.registerUI.getContentPane();
		GridLayout location = new GridLayout(1,2);
		contain.setLayout(location);//设置用来放置信息的容器
		contain.add(messagePanel);
		contain.add(typePanel);
		GridLayout messagePanel_location =new GridLayout(10, 1, 0, 10);
		GridLayout typePanel_location =new GridLayout(10, 1, 0, 10);
		messagePanel.setLayout(messagePanel_location);
		typePanel.setLayout(typePanel_location);
		System.out.print("test1");
		int number = (int) Math.round(Math.random() * 49 + 1000); ;
		String str = String.valueOf(number);
		username =new JButton(str);
		typePanel.add(username);
		JLabel name = new JLabel("用户名");
		messagePanel.add(name);
		ButtonGroup gender =new ButtonGroup();//性别选项
		this.female=new JRadioButton("女",true);
		gender.add(this.female);
		this.male=new JRadioButton("男",true);
		gender.add(this.male);
		JLabel sex = new JLabel("性别");
		messagePanel.add(sex);
		System.out.print("test2");
		JPanel usex = new JPanel();
		GridLayout usex_location =new GridLayout(1,2);
		usex.setLayout(usex_location);
		usex.add(this.male);
		usex.add(this.female);
		JLabel psw =new JLabel("密码");
		JLabel psw2 = new JLabel("确认密码");
		messagePanel.add(psw);
		messagePanel.add(psw2);
		System.out.print("test3");
		JPasswordField pswfield = new JPasswordField(10);
		this.password = pswfield;
		this.double_psw = pswfield;
		typePanel.add(usex);
		typePanel.add(password);
		typePanel.add(double_psw);//加入密码，性别框
		JLabel phone = new JLabel("电话号码");
		JTextField textfield= new JTextField(10);
		this.phoneNumber =textfield;
		typePanel.add(this.phoneNumber);
		messagePanel.add(phone);//加入电话框
		this.finish = new JButton("完成");
		messagePanel.add(this.finish);
		this.finish.addActionListener(this);
		this.registerUI.setVisible(true);
	}

	
	public static void main(String[] args) {
		new Register();
	}
}
