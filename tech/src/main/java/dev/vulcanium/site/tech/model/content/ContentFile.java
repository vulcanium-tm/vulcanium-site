package dev.vulcanium.site.tech.model.content;

/**
 * Model object used in webservice
 * when creating files
 */
public class ContentFile extends ContentPath {

private static final long serialVersionUID = 1L;
private byte[] file;


public byte[] getFile() {
	return file;
}
public void setFile(byte[] file) {
	this.file = file;
}


}
