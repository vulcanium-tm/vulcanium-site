package dev.vulcanium.site.tech.mapper.catalog;

import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Component;
import dev.vulcanium.business.model.catalog.product.manufacturer.Manufacturer;
import dev.vulcanium.business.model.catalog.product.manufacturer.ManufacturerDescription;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.mapper.Mapper;
import dev.vulcanium.site.tech.model.catalog.manufacturer.ReadableManufacturer;

@Component
public class ReadableManufacturerMapper implements Mapper<Manufacturer, ReadableManufacturer> {

@Override
public ReadableManufacturer convert(Manufacturer source, MerchantStore store, Language language) {
	
	if(language == null) {
		language = store.getDefaultLanguage();
	}
	ReadableManufacturer target = new ReadableManufacturer();
	
	Optional<dev.vulcanium.site.tech.model.catalog.manufacturer.ManufacturerDescription> description =
			getDescription(source, language, target);
	description.ifPresent(target::setDescription);
	
	target.setCode(source.getCode());
	target.setId(source.getId());
	target.setOrder(source.getOrder());
	Optional<dev.vulcanium.site.tech.model.catalog.manufacturer.ManufacturerDescription> desc = this.getDescription(source, language, target);
	if(description.isPresent()) {
		target.setDescription(desc.get());
	}
	
	
	return target;
}

private Optional<dev.vulcanium.site.tech.model.catalog.manufacturer.ManufacturerDescription> getDescription(
		Manufacturer source, Language language, ReadableManufacturer target) {
	
	Optional<ManufacturerDescription> description =
			getDescription(source.getDescriptions(), language);
	if (source.getDescriptions() != null && !source.getDescriptions().isEmpty()
			    && description.isPresent()) {
		return Optional.of(convertDescription(description.get(), source));
	} else {
		return Optional.empty();
	}
}

private Optional<ManufacturerDescription> getDescription(
		Set<ManufacturerDescription> descriptionsLang, Language language) {
	Optional<ManufacturerDescription> descriptionByLang = descriptionsLang.stream()
			                                                      .filter(desc -> desc.getLanguage().getCode().equals(language.getCode())).findAny();
	if (descriptionByLang.isPresent()) {
		return descriptionByLang;
	} else {
		return Optional.empty();
	}
}

private dev.vulcanium.site.tech.model.catalog.manufacturer.ManufacturerDescription convertDescription(
		ManufacturerDescription description, Manufacturer source) {
	final dev.vulcanium.site.tech.model.catalog.manufacturer.ManufacturerDescription desc =
			new dev.vulcanium.site.tech.model.catalog.manufacturer.ManufacturerDescription();
	
	desc.setFriendlyUrl(description.getUrl());
	desc.setId(description.getId());
	desc.setLanguage(description.getLanguage().getCode());
	desc.setName(description.getName());
	desc.setDescription(description.getDescription());
	return desc;
}

@Override
public ReadableManufacturer merge(Manufacturer source, ReadableManufacturer destination,
                                  MerchantStore store, Language language) {
	return destination;
}

}
