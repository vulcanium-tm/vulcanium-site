package dev.vulcanium.business.repositories.catalog.category;

import dev.vulcanium.business.model.catalog.category.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


public class PageableCategoryRepositoryImpl implements PageableCategoryRepositoryCustom {

	@PersistenceContext
	private EntityManager em;
	@SuppressWarnings("unchecked")
	@Override
	public Page<Category> listByStore(Integer storeId, Integer languageId, String name, Pageable pageable) {
	  Query query = em.createNamedQuery("CATEGORY.listByStore");
	  Query countQueryResult = em.createNamedQuery("CATEGORY.listByStore.count");
	  query.setParameter(1, storeId);
	  query.setParameter(2, languageId);
	  query.setParameter(3, name == null ? "" : name);
	  query.setMaxResults(pageable.getPageSize());
	  query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		return new PageImpl<Category>(
				query.getResultList(),
				pageable,
				countQueryResult.getMaxResults());
	}

}
