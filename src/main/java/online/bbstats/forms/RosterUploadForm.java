package online.bbstats.forms;

import org.springframework.web.multipart.MultipartFile;

public class RosterUploadForm {
	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
