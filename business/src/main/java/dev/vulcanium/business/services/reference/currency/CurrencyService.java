package dev.vulcanium.business.services.reference.currency;

import dev.vulcanium.business.model.reference.currency.Currency;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;

public interface CurrencyService extends SalesManagerEntityService<Long, Currency> {

	Currency getByCode(String code);

}
