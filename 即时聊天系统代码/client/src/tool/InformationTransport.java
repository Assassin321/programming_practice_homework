package tool;
import java.io.Serializable;
import java.util.ArrayList;
public class InformationTransport implements Serializable {
//client 和server间传输数据
	private static final long serialVersionUID = 1L;
	private String receiver=null;//返回slist的hash map的key
	private String sender=null;
	private Object data = null;
	private boolean flag = false;// 指令结果
	private String cmd = null;// 服务端指令
	private String result = null;//处理结果
	ArrayList<String> friends = null;
	public String getReceive() {
		return receiver;
	}
	public void setReceiver(String receive) {
		this.receiver = receive;
	}
	public String getSende() {
		return sender;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public ArrayList<String> setFriends(ArrayList<String> friends){
		return this.friends = friends;
	}
	public void setSender(String send) {
		this.sender = send;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getCmd() {
		return cmd;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	public ArrayList<String> getFriend(){
		return friends;
	}

}
