package dev.vulcanium.business.utils;

import dev.vulcanium.business.store.api.exception.ServiceRuntimeException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;

public class SanitizeUtils {

/**
 * should not contain /
 */
private static List<Character> blackList = Arrays.asList(';','%', '&', '=', '|', '*', '+', '_',
		'^', '%','$','(', ')', '{', '}', '<', '>', '[',
		']', '`', '\'', '~','\\', '?','\'');

private final static String POLICY_FILE = "antisamy-slashdot.xml";

private static Policy policy = null;

static {
	try {
		ClassLoader loader = Policy.class.getClassLoader();
		InputStream configStream = loader.getResourceAsStream(POLICY_FILE);
		policy = Policy.getInstance(configStream);
		
	} catch (Exception e) {
		throw new ServiceRuntimeException(e);
	}
}

private SanitizeUtils() {
}

public static String getSafeString(String value) {
	
	try {
		
		if(policy == null) {
			throw new ServiceRuntimeException("Error in " + SanitizeUtils.class.getName() + " html sanitize utils is null");		}
		
		AntiSamy as = new AntiSamy();
		CleanResults cr = as.scan(value, policy);
		
		return cr.getCleanHTML();
		
	} catch (Exception e) {
		throw new ServiceRuntimeException(e);
	}
	
	
	
}


public static String getSafeRequestParamString(String value) {
	
	StringBuilder safe = new StringBuilder();
	if(StringUtils.isNotEmpty(value)) {
		for(int i=0; i<value.length(); i++) {
			char current = value.charAt(i);
			if(!blackList.contains(current)) {
				safe.append(current);
			}
		}
	}
	return StringEscapeUtils.escapeXml11(safe.toString());
}
}
