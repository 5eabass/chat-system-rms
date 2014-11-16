package signals;

import java.io.Serializable;

public class Hello implements Serializable {
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
