package dev.vulcanium.business.services.user;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.user.Group;
import dev.vulcanium.business.model.user.Permission;
import dev.vulcanium.business.model.user.PermissionCriteria;
import dev.vulcanium.business.model.user.PermissionList;
import dev.vulcanium.business.repositories.user.PermissionRepository;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityServiceImpl;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;



@Service("permissionService")
public class PermissionServiceImpl extends
		SalesManagerEntityServiceImpl<Integer, Permission> implements
		PermissionService {

	private PermissionRepository permissionRepository;


	@Inject
	public PermissionServiceImpl(PermissionRepository permissionRepository) {
		super(permissionRepository);
		this.permissionRepository = permissionRepository;

	}

	@Override
	public List<Permission> getByName() {
		return null;
	}


	@Override
	public Permission getById(Integer permissionId) {
		return permissionRepository.findOne(permissionId);

	}


	@Override
	public void deletePermission(Permission permission) throws ServiceException {
		permission = this.getById(permission.getId());//Prevents detached entity error
		permission.setGroups(null);
		
		this.delete(permission);
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Permission> getPermissions(List<Integer> groupIds)
			throws ServiceException {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Set ids = new HashSet(groupIds);
		return permissionRepository.findByGroups(ids);
	}

	@Override
	public PermissionList listByCriteria(PermissionCriteria criteria)
			throws ServiceException {
		return permissionRepository.listByCriteria(criteria);
	}

	@Override
	public void removePermission(Permission permission,Group group) throws ServiceException {
		permission = this.getById(permission.getId());//Prevents detached entity error
	
		permission.getGroups().remove(group);
		

	}

	@Override
	public List<Permission> listPermission() throws ServiceException {
		return permissionRepository.findAll();
	}



}
