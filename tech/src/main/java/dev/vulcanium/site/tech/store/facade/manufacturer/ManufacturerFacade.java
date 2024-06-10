package dev.vulcanium.site.tech.store.facade.manufacturer;

import java.util.List;
import dev.vulcanium.business.model.catalog.product.manufacturer.Manufacturer;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.catalog.manufacturer.PersistableManufacturer;
import dev.vulcanium.site.tech.model.catalog.manufacturer.ReadableManufacturer;
import dev.vulcanium.site.tech.model.catalog.manufacturer.ReadableManufacturerList;
import dev.vulcanium.site.tech.model.entity.ListCriteria;

/**
 * Manufacturer / brand / collection product grouping
 * @author carlsamson
 *
 */
public interface ManufacturerFacade {

List<ReadableManufacturer> getByProductInCategory(MerchantStore store, Language language, Long categoryId);

/**
 * Creates or saves a manufacturer
 *
 * @param manufacturer
 * @param store
 * @param language
 * @throws Exception
 */
void saveOrUpdateManufacturer(PersistableManufacturer manufacturer, MerchantStore store,
                              Language language) throws Exception;

/**
 * Deletes a manufacturer
 *
 * @param manufacturer
 * @param store
 * @param language
 * @throws Exception
 */
void deleteManufacturer(Manufacturer manufacturer, MerchantStore store, Language language)
		throws Exception;

/**
 * Get a Manufacturer by id
 *
 * @param id
 * @param store
 * @param language
 * @return
 * @throws Exception
 */
ReadableManufacturer getManufacturer(Long id, MerchantStore store, Language language)
		throws Exception;

/**
 * Get all Manufacturer
 *
 * @param store
 * @param language
 * @return
 * @throws Exception
 */
ReadableManufacturerList getAllManufacturers(MerchantStore store, Language language, ListCriteria criteria, int page, int count) ;

/**
 * List manufacturers by a specific store
 * @param store
 * @param language
 * @param criteria
 * @param page
 * @param count
 * @return
 */
ReadableManufacturerList listByStore(MerchantStore store, Language language, ListCriteria criteria, int page, int count) ;

/**
 * Determines if manufacturer code already exists
 * @param store
 * @param manufacturerCode
 * @return
 */
boolean manufacturerExist(MerchantStore store, String manufacturerCode);

}
