package dev.vulcanium.site.tech.mapper.optin;

import org.springframework.stereotype.Component;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.system.optin.Optin;
import dev.vulcanium.site.tech.mapper.Mapper;
import dev.vulcanium.site.tech.model.system.ReadableOptin;

@Component
public class ReadableOptinMapper implements Mapper<Optin, ReadableOptin> {


@Override
public ReadableOptin convert(Optin source, MerchantStore store, Language language) {
	ReadableOptin optinEntity = new ReadableOptin();
	optinEntity.setCode(source.getCode());
	optinEntity.setDescription(source.getDescription());
	optinEntity.setOptinType(source.getOptinType().name());
	return optinEntity;
}

@Override
public ReadableOptin merge(Optin source, ReadableOptin destination, MerchantStore store,
                           Language language) {
	return destination;
}
}
