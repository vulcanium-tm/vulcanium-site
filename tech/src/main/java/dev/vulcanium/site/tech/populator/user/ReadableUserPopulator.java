package dev.vulcanium.site.tech.populator.user;

import org.apache.commons.lang3.Validate;

import dev.vulcanium.business.constants.Constants;
import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.user.Group;
import dev.vulcanium.business.model.user.User;
import dev.vulcanium.site.tech.model.security.ReadableGroup;
import dev.vulcanium.site.tech.model.user.ReadableUser;
import dev.vulcanium.site.tech.utils.DateUtil;

/**
 * Converts user model to readable user
 */
public class ReadableUserPopulator extends AbstractDataPopulator<User, ReadableUser> {

@Override
public ReadableUser populate(User source, ReadableUser target, MerchantStore store,
                             Language language) throws ConversionException {
	Validate.notNull(source, "User cannot be null");
	
	if (target == null) {
		target = new ReadableUser();
	}
	
	target.setFirstName(source.getFirstName());
	target.setLastName(source.getLastName());
	target.setEmailAddress(source.getAdminEmail());
	target.setUserName(source.getAdminName());
	target.setActive(source.isActive());
	
	if (source.getLastAccess() != null) {
		target.setLastAccess(DateUtil.formatLongDate(source.getLastAccess()));
	}
	
	target.setDefaultLanguage(Constants.DEFAULT_LANGUAGE);
	
	if (source.getDefaultLanguage() != null)
		target.setDefaultLanguage(source.getDefaultLanguage().getCode());
	target.setMerchant(store.getCode());
	target.setId(source.getId());
	
	
	for (Group group : source.getGroups()) {
		
		ReadableGroup g = new ReadableGroup();
		g.setName(group.getGroupName());
		g.setId(group.getId().longValue());
		target.getGroups().add(g);
	}
	
	/**
	 * dates DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
	 * myObjectMapper.setDateFormat(df);
	 */
	
	
	return target;
}

@Override
protected ReadableUser createTarget() {
	return null;
}

}
