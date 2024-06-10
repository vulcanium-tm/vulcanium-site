package dev.vulcanium.site.tech.model.store;

import  dev.vulcanium.site.tech.model.content.ReadableImage;

public class ReadableBrand extends MerchantStoreBrand {

private ReadableImage logo;

public ReadableImage getLogo() {
	return logo;
}

public void setLogo(ReadableImage logo) {
	this.logo = logo;
}

}
