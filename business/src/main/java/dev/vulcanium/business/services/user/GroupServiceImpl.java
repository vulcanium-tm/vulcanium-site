package dev.vulcanium.business.services.user;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.user.Group;
import dev.vulcanium.business.model.user.GroupType;
import dev.vulcanium.business.repositories.user.GroupRepository;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;


@Service("groupService")
public class GroupServiceImpl extends SalesManagerEntityServiceImpl<Integer, Group>
    implements GroupService {

  GroupRepository groupRepository;


  @Inject
  public GroupServiceImpl(GroupRepository groupRepository) {
    super(groupRepository);
    this.groupRepository = groupRepository;

  }


  @Override
  public List<Group> listGroup(GroupType groupType) throws ServiceException {
    try {
      return groupRepository.findByType(groupType);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  public List<Group> listGroupByIds(Set<Integer> ids) throws ServiceException {

      try {
        return ids.isEmpty() ? new ArrayList<Group>() : groupRepository.findByIds(ids);
      } catch (Exception e) {
        throw new ServiceException(e);
      }

  }


  @Override
  public Group findByName(String groupName) throws ServiceException {
    return groupRepository.findByGroupName(groupName);
  }


  @Override
  public List<Group> listGroupByNames(List<String> names) throws ServiceException {
    return groupRepository.findByNames(names);
  }


}
