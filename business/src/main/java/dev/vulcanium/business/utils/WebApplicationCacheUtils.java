package dev.vulcanium.business.utils;

import dev.vulcanium.business.utils.CacheUtils;
import org.springframework.stereotype.Component;

import jakarta.inject.Inject;

@Component
public class WebApplicationCacheUtils {

@Inject
private CacheUtils cache;

public Object getFromCache(String key) throws Exception {
	return cache.getFromCache(key);
}

public void putInCache(String key, Object object) throws Exception {
	cache.putInCache(object, key);
}

}
