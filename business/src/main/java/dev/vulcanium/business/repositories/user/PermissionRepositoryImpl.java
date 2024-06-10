package dev.vulcanium.business.repositories.user;

import dev.vulcanium.business.model.user.Permission;
import dev.vulcanium.business.model.user.PermissionCriteria;
import dev.vulcanium.business.model.user.PermissionList;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;


public class PermissionRepositoryImpl implements PermissionRepositoryCustom {

	
    @PersistenceContext
    private EntityManager em;
    
	@Override
	public PermissionList listByCriteria(PermissionCriteria criteria) {
		PermissionList permissionList = new PermissionList();

        
		StringBuilder countBuilderSelect = new StringBuilder();
		countBuilderSelect.append("select count(p) from Permission as p");
		
		StringBuilder countBuilderWhere = new StringBuilder();
		
		
		if(criteria.getGroupIds()!=null && criteria.getGroupIds().size()>0) {
			countBuilderSelect.append(" INNER JOIN p.groups grous");
			countBuilderWhere.append(" where grous.id in (:cid)");
		}
		
	
		Query countQ = em.createQuery(
				countBuilderSelect.toString() + countBuilderWhere.toString());

		if(criteria.getGroupIds()!=null && criteria.getGroupIds().size()>0) {
			countQ.setParameter("cid", criteria.getGroupIds());
		}
		

		Number count = (Number) countQ.getSingleResult ();

		permissionList.setTotalCount(count.intValue());
		
        if(count.intValue()==0)
        	return permissionList;

		
		StringBuilder qs = new StringBuilder();
		qs.append("select p from Permission as p ");
		qs.append("join fetch p.groups grous ");
		
		if(criteria.getGroupIds()!=null && criteria.getGroupIds().size()>0) {
			qs.append(" where grous.id in (:cid)");
		}
		
		qs.append(" order by p.id asc ");
		
    	String hql = qs.toString();
		Query q = em.createQuery(hql);


    	if(criteria.getGroupIds()!=null && criteria.getGroupIds().size()>0) {
    		q.setParameter("cid", criteria.getGroupIds());
    	}
    	
    	if(criteria.getMaxCount()>0) {
    		
    		
	    	q.setFirstResult(criteria.getStartIndex());
	    	if(criteria.getMaxCount()<count.intValue()) {
	    		q.setMaxResults(criteria.getMaxCount());
	    		permissionList.setTotalCount(criteria.getMaxCount());
	    	}
	    	else {
	    		q.setMaxResults(count.intValue());
	    		permissionList.setTotalCount(count.intValue());
	    	}
    	}
    	
    	@SuppressWarnings("unchecked")
		List<Permission> permissions =  q.getResultList();
    	permissionList.setPermissions(permissions);
    	
    	return permissionList;
	}   

}
