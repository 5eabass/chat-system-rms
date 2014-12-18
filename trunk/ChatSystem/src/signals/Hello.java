package signals;

/** 
     * This class Hello is a signal called when we launch a connection to the chat system
     * @return 
     */
public class Hello extends Signal {
	private String username;

	public Hello(String username) {
		super();
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
