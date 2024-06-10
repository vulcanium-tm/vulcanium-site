package dev.vulcanium.site.tech.store.controller.marketplace.facade;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.site.tech.store.api.exception.ConversionRuntimeException;
import dev.vulcanium.site.tech.store.api.exception.ResourceNotFoundException;
import dev.vulcanium.site.tech.store.api.exception.ServiceRuntimeException;
import java.util.Optional;
import jakarta.inject.Inject;

import org.springframework.stereotype.Component;

import dev.vulcanium.business.services.system.optin.OptinService;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.system.optin.Optin;
import dev.vulcanium.business.model.system.optin.OptinType;
import dev.vulcanium.site.tech.model.marketplace.ReadableMarketPlace;
import dev.vulcanium.site.tech.model.store.ReadableMerchantStore;
import dev.vulcanium.site.tech.model.system.ReadableOptin;
import dev.vulcanium.site.tech.populator.system.ReadableOptinPopulator;
import dev.vulcanium.site.tech.store.controller.store.facade.StoreFacade;

@Component
public class MarketPlaceFacadeImpl implements MarketPlaceFacade {

@Inject
private StoreFacade storeFacade;

@Inject
private OptinService optinService;

@Override
public ReadableMarketPlace get(String store, Language lang) {
	ReadableMerchantStore readableStore = storeFacade.getByCode(store, lang);
	return createReadableMarketPlace(readableStore);
}

private ReadableMarketPlace createReadableMarketPlace(ReadableMerchantStore readableStore) {
	ReadableMarketPlace marketPlace = new ReadableMarketPlace();
	marketPlace.setStore(readableStore);
	return marketPlace;
}

@Override
public ReadableOptin findByMerchantAndType(MerchantStore store, OptinType type) {
	Optin optin = getOptinByMerchantAndType(store, type);
	return convertOptinToReadableOptin(store, optin);
}

private Optin getOptinByMerchantAndType(MerchantStore store, OptinType type) {
	try{
		return Optional.ofNullable(optinService.getOptinByMerchantAndType(store, type))
				       .orElseThrow(() -> new ResourceNotFoundException("Option not found"));
	} catch (ServiceException e) {
		throw new ServiceRuntimeException(e);
	}
	
}

private ReadableOptin convertOptinToReadableOptin(MerchantStore store, Optin optin) {
	try{
		ReadableOptinPopulator populator = new ReadableOptinPopulator();
		return populator.populate(optin, null, store, null);
	} catch (ConversionException e) {
		throw new ConversionRuntimeException(e);
	}
	
}

}
