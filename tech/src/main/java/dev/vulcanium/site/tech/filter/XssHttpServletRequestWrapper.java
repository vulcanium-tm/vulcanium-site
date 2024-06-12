package dev.vulcanium.site.tech.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import dev.vulcanium.business.utils.SanitizeUtils;

/**
 * Cross Site Scripting filter enforcing html encoding of request parameters
 *
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

public XssHttpServletRequestWrapper(HttpServletRequest request) {
	super(request);
	
}



@Override
public String getHeader(String name) {
	String value = super.getHeader(name);
	if (value == null)
		return null;
	return cleanXSS(value);
}


public String[] getParameterValues(String parameter) {
	String[] values = super.getParameterValues(parameter);
	if (values == null) {
		return null;
	}
	int count = values.length;
	String[] encodedValues = new String[count];
	for (int i = 0; i < count; i++) {
		encodedValues[i] = cleanXSS(values[i]);
	}
	return encodedValues;
}

@Override
public String getParameter(String parameter) {
	String value = super.getParameter(parameter);
	if (value == null) {
		return null;
	}
	return cleanXSS(value);
}

private String cleanXSS(String value) {
	return SanitizeUtils.getSafeString(value);
}

}
