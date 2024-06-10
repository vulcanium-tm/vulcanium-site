package dev.vulcanium.site.tech.store.controller.optin.facade;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.services.system.optin.OptinService;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.system.optin.Optin;
import dev.vulcanium.site.tech.mapper.optin.PersistableOptinMapper;
import dev.vulcanium.site.tech.mapper.optin.ReadableOptinMapper;
import dev.vulcanium.site.tech.model.system.PersistableOptin;
import dev.vulcanium.site.tech.model.system.ReadableOptin;
import dev.vulcanium.site.tech.store.api.exception.ServiceRuntimeException;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

@Service("optinFacade")
public class OptinFacadeImpl implements OptinFacade {

@Inject
private OptinService optinService;

@Inject
private ReadableOptinMapper readableOptinConverter;
@Inject
private PersistableOptinMapper persistableOptinConverter;

@Override
public ReadableOptin create(
		PersistableOptin persistableOptin, MerchantStore merchantStore, Language language) {
	Optin optinEntity = persistableOptinConverter.convert(persistableOptin, merchantStore, language);
	Optin savedOptinEntity = createOptin(optinEntity);
	return readableOptinConverter.convert(savedOptinEntity, merchantStore, language);
}

private Optin createOptin(Optin optinEntity) {
	try{
		optinService.create(optinEntity);
		return optinEntity;
	} catch (ServiceException e){
		throw new ServiceRuntimeException(e);
	}
	
}
}
