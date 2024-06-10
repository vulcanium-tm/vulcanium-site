package dev.vulcanium.business.modules.cms.impl;

import lombok.Getter;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VendorCacheManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(VendorCacheManager.class);
  @Getter
  private static EmbeddedCacheManager manager = null;
  private static VendorCacheManager vendorCacheManager = null;


  private VendorCacheManager() {

    try {
      manager = new DefaultCacheManager();
    } catch (Exception e) {
      LOGGER.error("Cannot start manager " + e.toString());
    }

  }


  public static VendorCacheManager getInstance() {
    if (vendorCacheManager == null) {
      vendorCacheManager = new VendorCacheManager();

    }
    return vendorCacheManager;
  }
	
	
}
