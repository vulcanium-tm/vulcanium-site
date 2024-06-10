package dev.vulcanium.business.repositories.catalog.product.relationship;

import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.relationship.ProductRelationship;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import java.util.List;


public interface ProductRelationshipRepositoryCustom {

	List<ProductRelationship> getByType(MerchantStore store, String type,
			Language language);

	List<ProductRelationship> getByType(MerchantStore store, String type,
			Product product, Language language);

	List<ProductRelationship> getByGroup(MerchantStore store, String group);

	List<ProductRelationship> getGroups(MerchantStore store);

	List<ProductRelationship> getByType(MerchantStore store, String type);
	
	List<ProductRelationship> getGroupByType(MerchantStore store, String type);

	List<ProductRelationship> listByProducts(Product product);

	List<ProductRelationship> getByType(MerchantStore store, String type,
			Product product);
	
	List<ProductRelationship> getByTypeAndRelatedProduct(MerchantStore store, String type,
			Product product);

}
