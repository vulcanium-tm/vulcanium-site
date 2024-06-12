package dev.vulcanium.site.tech.populator.user;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.security.PersistableGroup;
import dev.vulcanium.business.model.user.Group;
import dev.vulcanium.business.model.user.User;
import dev.vulcanium.business.services.merchant.MerchantStoreService;
import dev.vulcanium.business.services.reference.language.LanguageService;
import dev.vulcanium.business.services.user.GroupService;
import dev.vulcanium.business.utils.AbstractDataPopulator;
import dev.vulcanium.site.tech.model.user.PersistableUser;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class PersistableUserPopulator extends AbstractDataPopulator<PersistableUser, User> {

@Inject
private LanguageService languageService;

@Inject
private GroupService groupService;

@Inject
private MerchantStoreService merchantStoreService;

@Inject
@Named("passwordEncoder")
private PasswordEncoder passwordEncoder;

@Override
public User populate(PersistableUser source, User target, MerchantStore store, Language language)
		throws ConversionException {
	Validate.notNull(source, "PersistableUser cannot be null");
	Validate.notNull(store, "MerchantStore cannot be null");
	
	if (target == null) {
		target = new User();
	}
	
	target.setFirstName(source.getFirstName());
	target.setLastName(source.getLastName());
	target.setAdminEmail(source.getEmailAddress());
	target.setAdminName(source.getUserName());
	if(!StringUtils.isBlank(source.getPassword())) {
		target.setAdminPassword(passwordEncoder.encode(source.getPassword()));
	}
	
	if(!StringUtils.isBlank(source.getStore())) {
		try {
			MerchantStore userStore = merchantStoreService.getByCode(source.getStore());
			target.setMerchantStore(userStore);
		} catch (ServiceException e) {
			throw new ConversionException("Error while reading MerchantStore store [" + source.getStore() + "]",e);
		}
	} else {
		target.setMerchantStore(store);
	}
	
	
	target.setActive(source.isActive());
	
	Language lang = null;
	try {
		lang = languageService.getByCode(source.getDefaultLanguage());
	} catch(Exception e) {
		throw new ConversionException("Cannot get language [" + source.getDefaultLanguage() + "]",e);
	}
	
	target.setDefaultLanguage(lang);
	
	List<Group> userGroups = new ArrayList<Group>();
	List<String> names = new ArrayList<String>();
	for (PersistableGroup group : source.getGroups()) {
		names.add(group.getName());
	}
	try {
		List<Group> groups = groupService.listGroupByNames(names);
		for(Group g: groups) {
			userGroups.add(g);
		}
	} catch (Exception e1) {
		throw new ConversionException("Error while getting user groups",e1);
	}
	
	target.setGroups(userGroups);
	
	return target;
}

@Override
protected User createTarget() {
	return null;
}

}
