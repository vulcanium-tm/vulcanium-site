package dev.vulcanium.business.repositories.content;

import dev.vulcanium.business.model.content.Content;
import dev.vulcanium.business.model.content.ContentDescription;
import dev.vulcanium.business.model.content.ContentType;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;


public class ContentRepositoryImpl implements ContentRepositoryCustom {

	
    @PersistenceContext
    private EntityManager em;
    
	@Override
	public List<ContentDescription> listNameByType(List<ContentType> contentType, MerchantStore store, Language language) {
		


			StringBuilder qs = new StringBuilder();

			qs.append("select c from Content c ");
			qs.append("left join fetch c.descriptions cd join fetch c.merchantStore cm ");
			qs.append("where c.contentType in (:ct) ");
			qs.append("and cm.id =:cm ");
			qs.append("and cd.language.id =:cl ");
			qs.append("and c.visible=true ");
			qs.append("order by c.sortOrder");

			String hql = qs.toString();
			Query q = this.em.createQuery(hql);

	    	q.setParameter("ct", contentType);
	    	q.setParameter("cm", store.getId());
	    	q.setParameter("cl", language.getId());
	

			@SuppressWarnings("unchecked")
			List<Content> contents = q.getResultList();
			
			List<ContentDescription> descriptions = new ArrayList<ContentDescription>();
			for(Content c : contents) {
					String name = c.getDescription().getName();
					String url = c.getDescription().getSeUrl();
					ContentDescription contentDescription = new ContentDescription();
					contentDescription.setName(name);
					contentDescription.setSeUrl(url);
					contentDescription.setContent(c);
					descriptions.add(contentDescription);
					
			}
			
			return descriptions;

	}
	
	@Override
	public ContentDescription getBySeUrl(MerchantStore store,String seUrl) {

			StringBuilder qs = new StringBuilder();

			qs.append("select c from Content c ");
			qs.append("left join fetch c.descriptions cd join fetch c.merchantStore cm ");
			qs.append("where cm.id =:cm ");
			qs.append("and c.visible =true ");
			qs.append("and cd.seUrl =:se ");


			String hql = qs.toString();
			Query q = this.em.createQuery(hql);

	    	q.setParameter("cm", store.getId());
	    	q.setParameter("se", seUrl);
	

	    	Content content = (Content)q.getSingleResult();
			

			if(content!=null) {
					return content.getDescription();
			}
			
			@SuppressWarnings("unchecked")
			List<Content> results = q.getResultList();
	        if (results.isEmpty()) {
	        	return null;
	        } else if (results.size() >= 1) {
	        		content = results.get(0);
	        }
	        
			if(content!=null) {
				return content.getDescription();
			}
	        
			
			return null;

	}
    

}