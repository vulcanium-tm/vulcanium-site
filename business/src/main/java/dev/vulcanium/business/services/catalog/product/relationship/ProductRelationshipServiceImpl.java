package dev.vulcanium.business.services.catalog.product.relationship;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.relationship.ProductRelationship;
import dev.vulcanium.business.model.catalog.product.relationship.ProductRelationshipType;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.repositories.catalog.product.relationship.ProductRelationshipRepository;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityServiceImpl;
import java.util.List;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

@Service("productRelationshipService")
public class ProductRelationshipServiceImpl extends
		SalesManagerEntityServiceImpl<Long, ProductRelationship> implements
		ProductRelationshipService {

	
	private ProductRelationshipRepository productRelationshipRepository;
	
	@Inject
	public ProductRelationshipServiceImpl(
			ProductRelationshipRepository productRelationshipRepository) {
			super(productRelationshipRepository);
			this.productRelationshipRepository = productRelationshipRepository;
	}
	
	@Override
	public void saveOrUpdate(ProductRelationship relationship) throws ServiceException {
		
		if(relationship.getId()!=null && relationship.getId()>0) {
			
			this.update(relationship);
			
		} else {
			this.create(relationship);
		}
		
	}
	
	
	@Override
	public void addGroup(MerchantStore store, String groupName) throws ServiceException {
		ProductRelationship relationship = new ProductRelationship();
		relationship.setCode(groupName);
		relationship.setStore(store);
		relationship.setActive(true);
		this.save(relationship);
	}
	
	@Override
	public List<ProductRelationship> getGroups(MerchantStore store) {
		return productRelationshipRepository.getGroups(store);
	}
	
	@Override
	public void deleteGroup(MerchantStore store, String groupName) throws ServiceException {
		List<ProductRelationship> entities = productRelationshipRepository.getByGroup(store, groupName);
		for(ProductRelationship relation : entities) {
			this.delete(relation);
		}
	}
	
	@Override
	public void deactivateGroup(MerchantStore store, String groupName) throws ServiceException {
		List<ProductRelationship> entities = getGroupDefinition(store, groupName);
		for(ProductRelationship relation : entities) {
			relation.setActive(false);
			this.saveOrUpdate(relation);
		}
	}
	
	@Override
	public void activateGroup(MerchantStore store, String groupName) throws ServiceException {
		List<ProductRelationship> entities = getGroupDefinition(store, groupName);
		for(ProductRelationship relation : entities) {
			relation.setActive(true);
			this.saveOrUpdate(relation);
		}
	}
	
	public void deleteRelationship(ProductRelationship relationship)  throws ServiceException {
		
		//throws detached exception so need to query first
		relationship = this.getById(relationship.getId());
		if(relationship != null) {
			delete(relationship);
		}
		
		
		
	}
	
	@Override
	public List<ProductRelationship> listByProduct(Product product) throws ServiceException {

		return productRelationshipRepository.listByProducts(product);

	}
	
	
	@Override
	public List<ProductRelationship> getByType(MerchantStore store, Product product, ProductRelationshipType type, Language language) throws ServiceException {

		return productRelationshipRepository.getByType(store, type.name(), product, language);

	}
	
	@Override
	public List<ProductRelationship> getByType(MerchantStore store, ProductRelationshipType type, Language language) throws ServiceException {
		return productRelationshipRepository.getByType(store, type.name(), language);
	}
	
	@Override
	public List<ProductRelationship> getByType(MerchantStore store, ProductRelationshipType type) throws ServiceException {

		return productRelationshipRepository.getByType(store, type.name());

	}
	
	@Override
	public List<ProductRelationship> getByGroup(MerchantStore store, String groupName) throws ServiceException {

		return productRelationshipRepository.getByType(store, groupName);

	}
	
	@Override
	public List<ProductRelationship> getByGroup(MerchantStore store, String groupName, Language language) throws ServiceException {

		return productRelationshipRepository.getByType(store, groupName, language);

	}
	
	@Override
	public List<ProductRelationship> getByType(MerchantStore store, Product product, ProductRelationshipType type) throws ServiceException {
		

		return productRelationshipRepository.getByType(store, type.name(), product);
				
		
	}

	@Override
	public List<ProductRelationship> getGroupDefinition(MerchantStore store, String name) {
		return productRelationshipRepository.getByGroup(store, name);
	}

	@Override
	public List<ProductRelationship> getByType(MerchantStore store, Product product, String name)
			throws ServiceException {
		return productRelationshipRepository.getByTypeAndRelatedProduct(store, name, product);
	}



}
