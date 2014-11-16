package signals;

import java.io.File;
import java.io.Serializable;

public class FileTransfer implements Serializable {
	private File file;

	public FileTransfer(File file) {
		super();
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}
