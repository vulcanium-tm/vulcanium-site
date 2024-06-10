package dev.vulcanium.site.tech.store.facade.zone;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.references.ReadableZone;
import java.util.List;

public interface ZoneFacade {

List<ReadableZone> getZones(String countryCode, Language language, MerchantStore merchantStore);

}
