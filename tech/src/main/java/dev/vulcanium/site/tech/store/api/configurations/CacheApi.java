package dev.vulcanium.site.tech.store.api.configurations;

import jakarta.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import dev.vulcanium.business.utils.CacheUtils;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.site.tech.store.api.exception.ServiceRuntimeException;
import dev.vulcanium.site.tech.store.controller.store.facade.StoreFacade;

@RestController
@RequestMapping(value = "/api")
public class CacheApi {

private static final Logger LOGGER = LoggerFactory.getLogger(CacheApi.class);

@Inject
private StoreFacade storeFacade;

@Inject
private CacheUtils cache;

@DeleteMapping(value = "/auth/cache/store/{storeId}/clear")
public @ResponseBody ResponseEntity<String> clearCache(@PathVariable("storeId") String storeCode,
                                                       @RequestParam(name = "cacheKey", required = false) String cacheKey) {
	
	try {
		MerchantStore merchantStore = storeFacade.get(storeCode);
		StringBuilder key =
				new StringBuilder().append(merchantStore.getId()).append("_").append(cacheKey);
		
		if (StringUtils.isNotEmpty(cacheKey)) {
			cache.removeFromCache(key.toString());
		} else {
			cache.removeAllFromCache(merchantStore);
		}
	} catch (Exception e) {
		LOGGER.error("Error while clearning cache {}", e.getCause());
		throw new ServiceRuntimeException("Error while clearing cache");
	}
	
	return new ResponseEntity<>(HttpStatus.OK);
}

}
