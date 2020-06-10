package user;
import java.io.Serializable;
import java.util.*;
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String sex; 
	private String phone; 
	private int friends_num = 0;
	private ArrayList<String> friendslist;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	public User(String username, String password, String sex, String phone) {
		this.username = username;
		this.password = password;
		this.sex = sex; 
		this.phone = phone;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setgender(String sex) {
		this.sex = sex;
	}
	public int getFriendsNum() {
		return friends_num;
	}
	public void setFriendsNum(int friends_num) {
		this.friends_num = friends_num;
	}
	public void setFriendsList(ArrayList<String> friendList) {
		Collections.sort(friendList);
		this.friendslist =  friendList;
		this.friends_num = friendList.size();
	}
	public String getPhoneNumber() {
		return phone;
	}

	public void setPhoneNumber(String phone) {
		this.phone = phone;
	}
	

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getgender() {
		return sex;
	}


	public ArrayList<String> getFriend() {
		return friendslist;
	}
	
}
