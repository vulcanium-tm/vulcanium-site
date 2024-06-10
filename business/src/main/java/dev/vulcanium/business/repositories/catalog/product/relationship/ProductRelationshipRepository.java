package dev.vulcanium.business.repositories.catalog.product.relationship;

import dev.vulcanium.business.model.catalog.product.relationship.ProductRelationship;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRelationshipRepository extends JpaRepository<ProductRelationship, Long>, ProductRelationshipRepositoryCustom {

}
