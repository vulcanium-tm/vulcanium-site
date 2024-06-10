package dev.vulcanium.site.tech.store.controller.country.facade;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.references.ReadableCountry;
import java.util.List;

public interface CountryFacade {
List<ReadableCountry> getListCountryZones(Language language, MerchantStore merchantStore);
}
