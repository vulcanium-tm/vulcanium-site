package dev.vulcanium.site.tech.store.controller.currency.facade;

import dev.vulcanium.business.services.reference.currency.CurrencyService;
import dev.vulcanium.business.model.reference.currency.Currency;
import dev.vulcanium.site.tech.store.api.exception.ResourceNotFoundException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

@Service("currencyFacade")
public class CurrencyFacadeImpl implements CurrencyFacade {

@Inject
private CurrencyService currencyService;

@Override
public List<Currency> getList() {
	List<Currency> currencyList = currencyService.list();
	if (currencyList.isEmpty()){
		throw new ResourceNotFoundException("No languages found");
	}
	Collections.sort(currencyList, new Comparator<Currency>(){
		
		public int compare(Currency o1, Currency o2)
		{
			return o1.getCode().compareTo(o2.getCode());
		}
	});
	return currencyList;
}
}
