package dev.vulcanium.site.tech.populator.references;

import org.apache.commons.collections.CollectionUtils;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.reference.zone.Zone;
import dev.vulcanium.business.model.reference.zone.ZoneDescription;
import dev.vulcanium.site.tech.model.references.ReadableZone;

public class ReadableZonePopulator extends AbstractDataPopulator<Zone, ReadableZone> {

@Override
public ReadableZone populate(Zone source, ReadableZone target, MerchantStore store, Language language)
		throws ConversionException {
	if(target==null) {
		target = new ReadableZone();
	}
	
	target.setId(source.getId());
	target.setCode(source.getCode());
	target.setCountryCode(source.getCountry().getIsoCode());
	
	if(!CollectionUtils.isEmpty(source.getDescriptions())) {
		for(ZoneDescription d : source.getDescriptions()) {
			if(d.getLanguage().getId() == language.getId()) {
				target.setName(d.getName());
				continue;
			}
		}
	}
	
	return target;
	
}

@Override
protected ReadableZone createTarget() {
	return null;
}

}
