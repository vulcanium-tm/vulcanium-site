package dev.vulcanium.site.tech.populator.security;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.user.Permission;
import dev.vulcanium.site.tech.model.security.ReadablePermission;

public class ReadablePermissionPopulator extends AbstractDataPopulator<Permission, ReadablePermission> {

@Override
public ReadablePermission populate(Permission source, ReadablePermission target,
                                   MerchantStore store, Language language) throws ConversionException {
	return null;
}

@Override
protected ReadablePermission createTarget() {
	return null;
}

}
