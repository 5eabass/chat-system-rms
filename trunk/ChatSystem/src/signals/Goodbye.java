package signals;

/** 
     * This class Goodbye is a signal called when we launch a disconnection from the chat system 
     * @return 
     */
public class Goodbye extends Signal {
	private String username;

	public Goodbye(String username) {
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
