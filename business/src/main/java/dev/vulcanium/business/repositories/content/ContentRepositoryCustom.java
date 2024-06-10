package dev.vulcanium.business.repositories.content;

import dev.vulcanium.business.model.content.ContentDescription;
import dev.vulcanium.business.model.content.ContentType;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import java.util.List;


public interface ContentRepositoryCustom {

	List<ContentDescription> listNameByType(List<ContentType> contentType,
			MerchantStore store, Language language);

	ContentDescription getBySeUrl(MerchantStore store, String seUrl);
	

}
