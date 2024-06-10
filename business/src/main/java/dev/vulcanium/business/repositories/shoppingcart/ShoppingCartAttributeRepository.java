package dev.vulcanium.business.repositories.shoppingcart;

import dev.vulcanium.business.model.shoppingcart.ShoppingCartAttributeItem;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ShoppingCartAttributeRepository extends JpaRepository<ShoppingCartAttributeItem, Long> {


}
