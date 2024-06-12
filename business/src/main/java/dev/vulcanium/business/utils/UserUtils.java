package dev.vulcanium.business.utils;

import dev.vulcanium.business.model.user.Group;
import dev.vulcanium.business.model.user.User;

import java.util.List;

public class UserUtils {

public static boolean userInGroup(User user,String groupName) {
	
	
	
	List<Group> logedInUserGroups = user.getGroups();
	for(Group group : logedInUserGroups) {
		if(group.getGroupName().equals(groupName)) {
			return true;
		}
	}
	
	return false;
	
}

}
