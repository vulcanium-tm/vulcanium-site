package dev.vulcanium.site.tech.model.store;

import java.io.Serializable;
import java.util.List;

import dev.vulcanium.site.tech.model.content.ReadableImage;
import dev.vulcanium.site.tech.model.entity.ReadableAudit;
import dev.vulcanium.site.tech.model.entity.ReadableAuditable;
import dev.vulcanium.site.tech.model.references.ReadableAddress;
import dev.vulcanium.site.tech.model.references.ReadableLanguage;

public class ReadableMerchantStore extends MerchantStoreEntity implements ReadableAuditable, Serializable {

/**
 *
 */
private static final long serialVersionUID = 1L;

private String currentUserLanguage;
private ReadableAddress address;
private ReadableImage logo;
private ReadableAudit audit;
private ReadableMerchantStore parent;

private List<ReadableLanguage> supportedLanguages;

public String getCurrentUserLanguage() {
	return currentUserLanguage;
}

public void setCurrentUserLanguage(String currentUserLanguage) {
	this.currentUserLanguage = currentUserLanguage;
}

public ReadableAddress getAddress() {
	return address;
}

public void setAddress(ReadableAddress address) {
	this.address = address;
}

public ReadableImage getLogo() {
	return logo;
}

public void setLogo(ReadableImage logo) {
	this.logo = logo;
}

public void setReadableAudit(ReadableAudit audit) {
	this.audit = audit;
	
}

public ReadableAudit getReadableAudit() {
	return this.audit;
}

public ReadableMerchantStore getParent() {
	return parent;
}

public void setParent(ReadableMerchantStore parent) {
	this.parent = parent;
}

public List<ReadableLanguage> getSupportedLanguages() {
	return supportedLanguages;
}

public void setSupportedLanguages(List<ReadableLanguage> supportedLanguages) {
	this.supportedLanguages = supportedLanguages;
}


}
