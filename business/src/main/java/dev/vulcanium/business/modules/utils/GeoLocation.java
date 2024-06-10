package dev.vulcanium.business.modules.utils;

import dev.vulcanium.business.model.common.Address;

public interface GeoLocation {

Address getAddress(String ipAddress) throws Exception;

}