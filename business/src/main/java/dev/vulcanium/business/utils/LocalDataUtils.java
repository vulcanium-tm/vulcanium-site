package dev.vulcanium.business.utils;

import java.util.HashMap;
import java.util.Map;

public class LocalDataUtils{

private static final Map<String, String> localStorage = new HashMap<>();

public static void setLocalData(String key, String value){
	try{
		localStorage.put(key, value);
	} catch (Exception e){
		System.err.println("Error setting local data: " + e.getMessage());
	}
}

public static void removeLocalData(String key){
	try{
		localStorage.remove(key);
	} catch (Exception e){
		System.err.println("Error removing local data: " + e.getMessage());
	}
}

public static String getLocalData(String key){
	try{
		return localStorage.get(key);
	} catch (Exception e){
		System.err.println("Error getting local data: " + e.getMessage());
		return null;
	}
}

public static boolean isValidValue(Object value){
	return value!=null && !value.toString().isEmpty();
}

public static String isCheckValueAndSetParams(String params, String value){
	return isValidValue(value) ? params + value : "";
}

public static boolean hasProperty(Map<String, ?> object, String key){
	return object!=null && key!=null && object.containsKey(key);
}

public static boolean isValidObject(Map<String, ?> items){
	return items!=null && !items.isEmpty();
}

public static String getValueFromObject(Map<String, ?> object, String key){
	if (hasProperty(object, key)){
		Object value = object.get(key);
		if (isValidValue(value)){
			return value.toString();
		}
	}
	return "";
}
}
