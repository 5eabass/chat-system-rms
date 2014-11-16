package signals;

import java.io.Serializable;

public class HelloOK implements Serializable {
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
