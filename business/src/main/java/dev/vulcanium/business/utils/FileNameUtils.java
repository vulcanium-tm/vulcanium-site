package dev.vulcanium.business.utils;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Determines if a file name seems to be valid.
 * This utility opens the door to the validation of a file name and see if it meets the following
 * <p>
 * - has an extension
 * - has a name
 * - ... your own rules ...
 */
@Component
public class FileNameUtils {

public boolean validFileName(String fileName) {
	
	boolean validName = true;
	
	if(StringUtils.isEmpty(FilenameUtils.getExtension(fileName))) {
		validName = false;
	}
	
	if(StringUtils.isEmpty(FilenameUtils.getBaseName(fileName))) {
		validName = false;
	}
	
	return validName;
}

}
