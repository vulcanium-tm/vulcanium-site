/**
 * 
 */
package dev.vulcanium.business.utils;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;

/**
 * @author Umesh A
 *
 */
public interface DataPopulator<Source,Target> {

    Target populate(Source source,Target target, MerchantStore store, Language language) throws ConversionException;
    Target populate(Source source, MerchantStore store, Language language) throws ConversionException;

}
