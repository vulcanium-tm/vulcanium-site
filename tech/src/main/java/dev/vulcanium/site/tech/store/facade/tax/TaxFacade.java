package dev.vulcanium.site.tech.store.facade.tax;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.entity.Entity;
import dev.vulcanium.site.tech.model.entity.ReadableEntityList;
import dev.vulcanium.site.tech.model.tax.PersistableTaxClass;
import dev.vulcanium.site.tech.model.tax.PersistableTaxRate;
import dev.vulcanium.site.tech.model.tax.ReadableTaxClass;
import dev.vulcanium.site.tech.model.tax.ReadableTaxRate;

public interface TaxFacade {

Entity createTaxClass(PersistableTaxClass taxClass, MerchantStore store, Language language);
void updateTaxClass(Long id, PersistableTaxClass taxClass, MerchantStore store, Language language);
void deleteTaxClass(Long id, MerchantStore store, Language language);
boolean existsTaxClass(String code, MerchantStore store, Language language);

ReadableEntityList<ReadableTaxClass> taxClasses(MerchantStore store, Language language);
ReadableTaxClass taxClass(String code, MerchantStore store, Language language);

Entity createTaxRate(PersistableTaxRate taxRate, MerchantStore store, Language language);
void updateTaxRate(Long id, PersistableTaxRate taxRate, MerchantStore store, Language language);
void deleteTaxRate(Long id, MerchantStore store, Language language);
boolean existsTaxRate(String code, MerchantStore store, Language language);

ReadableEntityList<ReadableTaxRate> taxRates(MerchantStore store, Language language);
ReadableTaxRate taxRate(Long id, MerchantStore store, Language language);




}
