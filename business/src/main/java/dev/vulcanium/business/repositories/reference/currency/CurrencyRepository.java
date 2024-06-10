package dev.vulcanium.business.repositories.reference.currency;

import dev.vulcanium.business.model.reference.currency.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository <Currency, Long> {

	
	Currency getByCode(String code);
}
