package dev.vulcanium.site.tech.mapper.optin;

import org.springframework.stereotype.Component;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.system.optin.Optin;
import dev.vulcanium.business.model.system.optin.OptinType;
import dev.vulcanium.site.tech.mapper.Mapper;
import dev.vulcanium.site.tech.model.system.PersistableOptin;

@Component
public class PersistableOptinMapper implements Mapper<PersistableOptin, Optin> {


@Override
public Optin convert(PersistableOptin source, MerchantStore store, Language language) {
	Optin optinEntity = new Optin();
	optinEntity.setCode(source.getCode());
	optinEntity.setDescription(source.getDescription());
	optinEntity.setOptinType(OptinType.valueOf(source.getOptinType()));
	optinEntity.setMerchant(store);
	return optinEntity;
}

@Override
public Optin merge(PersistableOptin source, Optin destination, MerchantStore store,
                   Language language) {
	return destination;
}
}
