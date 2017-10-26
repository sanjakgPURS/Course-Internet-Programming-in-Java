package model;

public class Message {

	private int id = 0;
	private String userName = "";
	private String message = "";
	public int getId() {
		return id;
	}
	public String getUserName() {
		return userName;
	}
	public String getMessage() {
		return message;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
