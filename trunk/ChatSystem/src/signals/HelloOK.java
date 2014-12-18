package signals;

/** 
     * This class HelloOK is a signal called when we notify a remote user that we have well received his connection to the chat 
     * @return 
     */
public class HelloOK extends Signal {
	private String username;

	public HelloOK(String username) {
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
