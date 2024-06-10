package dev.vulcanium.business.services.reference.currency;

import dev.vulcanium.business.model.reference.currency.Currency;
import dev.vulcanium.business.repositories.reference.currency.CurrencyRepository;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityServiceImpl;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

@Service("currencyService")
public class CurrencyServiceImpl extends SalesManagerEntityServiceImpl<Long, Currency>
	implements CurrencyService {
	
	private CurrencyRepository currencyRepository;
	
	@Inject
	public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
		super(currencyRepository);
		this.currencyRepository = currencyRepository;
	}

	@Override
	public Currency getByCode(String code) {
		return currencyRepository.getByCode(code);
	}

}
