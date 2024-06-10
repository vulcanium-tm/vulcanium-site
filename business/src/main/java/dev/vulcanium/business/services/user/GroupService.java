package dev.vulcanium.business.services.user;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.user.Group;
import dev.vulcanium.business.model.user.GroupType;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;
import java.util.List;
import java.util.Set;


public interface GroupService extends SalesManagerEntityService<Integer, Group> {


	List<Group> listGroup(GroupType groupType) throws ServiceException;
	List<Group> listGroupByIds(Set<Integer> ids) throws ServiceException;
	List<Group> listGroupByNames(List<String> names) throws ServiceException;
	Group findByName(String groupName) throws ServiceException;

}
