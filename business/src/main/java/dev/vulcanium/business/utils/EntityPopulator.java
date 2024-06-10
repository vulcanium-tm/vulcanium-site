/**
 * 
 */
package dev.vulcanium.business.utils;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.model.merchant.MerchantStore;

/**
 * @author Umesh A
 *
 */
public interface EntityPopulator<Source,Target>
{

    Target populateToEntity(Source source, Target target, MerchantStore store)  throws ConversionException;
    Target populateToEntity(Source source) throws ConversionException;
}
