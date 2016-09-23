package egovframework.com.uss.omt.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UploadItemVO {

	private String name;
	
	private CommonsMultipartFile fileData;
	
	private String savedFilename;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CommonsMultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}

	public String getSavedFilename() {
		return savedFilename;
	}

	public void setSavedFilename(String savedFilename) {
		this.savedFilename = savedFilename;
	}

	public String getExtension() {
		if (StringUtils.isBlank(name)) {
			return "";
		}
		int dotPos = StringUtils.lastIndexOf(name, ".");
		return StringUtils.substring(name, dotPos);
	}
}