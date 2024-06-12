package dev.vulcanium.site.tech.store.facade.payment;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.payments.PaymentMethod;
import dev.vulcanium.business.model.system.IntegrationConfiguration;
import dev.vulcanium.business.services.payments.PaymentService;
import dev.vulcanium.business.store.api.exception.ServiceRuntimeException;
import dev.vulcanium.site.tech.model.configuration.PersistableConfiguration;
import dev.vulcanium.site.tech.model.configuration.ReadableConfiguration;
import dev.vulcanium.site.tech.store.facade.configuration.ConfigurationsFacade;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("paymentConfigurationFacade")
public class PaymentConfigurationFacadeImpl implements ConfigurationsFacade {


@Autowired
private PaymentService paymentService;

@Override
public List<ReadableConfiguration> configurations(MerchantStore store) {
	
	try {
		
		List<PaymentMethod> methods = paymentService.getAcceptedPaymentMethods(store);
		List<ReadableConfiguration> configurations =
				methods.stream()
						.map(m -> configuration(m.getInformations(), store)).collect(Collectors.toList());
		return configurations;
		
	} catch (ServiceException e) {
		throw new ServiceRuntimeException("Error while getting payment configurations",e);
	}
	
}

@Override
public ReadableConfiguration configuration(String module, MerchantStore store) {
	
	try {
		
		ReadableConfiguration config = null;
		List<PaymentMethod> methods = paymentService.getAcceptedPaymentMethods(store);
		Optional<ReadableConfiguration> configuration =
				methods.stream()
						.filter(m -> module.equals(m.getModule().getCode()))
						.map(m -> this.configuration(m.getInformations(), store))
						.findFirst();
		
		if(configuration.isPresent()) {
			config = configuration.get();
		}
		
		return config;
		
	} catch (ServiceException e) {
		throw new ServiceRuntimeException("Error while getting payment configuration [" + module + "]",e);
	}
	
}

@Override
public void saveConfiguration(PersistableConfiguration configuration, MerchantStore store) {

}

@Override
public void deleteConfiguration(String module, MerchantStore store) {

}


private ReadableConfiguration configuration(IntegrationConfiguration source, MerchantStore store) {
	
	ReadableConfiguration config = new ReadableConfiguration();
	config.setActive(source.isActive());
	config.setCode(source.getModuleCode());
	config.setKeys(source.getIntegrationKeys());
	config.setIntegrationOptions(source.getIntegrationOptions());
	
	return config;
}


}
