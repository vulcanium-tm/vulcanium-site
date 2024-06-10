package dev.vulcanium.site.tech.mapper;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;

public interface Mapper<S, T> {

T convert(S source, MerchantStore store, Language language);
T merge(S source, T destination, MerchantStore store, Language language);


}
