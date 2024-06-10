package dev.vulcanium.site.tech.store.facade.shipping;

import java.util.List;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.shipping.PackageDetails;
import dev.vulcanium.site.tech.model.references.PersistableAddress;
import dev.vulcanium.site.tech.model.references.ReadableAddress;
import dev.vulcanium.site.tech.model.references.ReadableCountry;
import dev.vulcanium.site.tech.model.shipping.ExpeditionConfiguration;

public interface ShippingFacade {

ExpeditionConfiguration getExpeditionConfiguration(MerchantStore store, Language language);
void saveExpeditionConfiguration(ExpeditionConfiguration expedition, MerchantStore store);


ReadableAddress getShippingOrigin(MerchantStore store);
void saveShippingOrigin(PersistableAddress address, MerchantStore store);


void createPackage(PackageDetails packaging, MerchantStore store);

PackageDetails getPackage(String code, MerchantStore store);

/**
 * List of configured ShippingCountry for a given store
 * @param store
 * @param lanuage
 * @return
 */
List<ReadableCountry> shipToCountry(MerchantStore store, Language lanuage);

List<PackageDetails> listPackages(MerchantStore store);

void updatePackage(String code, PackageDetails packaging, MerchantStore store);

void deletePackage(String code, MerchantStore store);




}
