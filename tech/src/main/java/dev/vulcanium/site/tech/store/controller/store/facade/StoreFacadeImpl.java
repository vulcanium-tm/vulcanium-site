package dev.vulcanium.site.tech.store.controller.store.facade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import dev.vulcanium.business.exception.ConversionException;
import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.services.content.ContentService;
import dev.vulcanium.business.services.merchant.MerchantStoreService;
import dev.vulcanium.business.services.reference.language.LanguageService;
import dev.vulcanium.business.services.system.MerchantConfigurationService;
import dev.vulcanium.business.constants.MeasureUnit;
import dev.vulcanium.business.model.common.GenericEntityList;
import dev.vulcanium.business.model.content.InputContentFile;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.merchant.MerchantStoreCriteria;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.system.MerchantConfiguration;
import dev.vulcanium.business.model.system.MerchantConfigurationType;
import dev.vulcanium.site.tech.model.content.ReadableImage;
import dev.vulcanium.site.tech.model.store.MerchantConfigEntity;
import dev.vulcanium.site.tech.model.store.PersistableBrand;
import dev.vulcanium.site.tech.model.store.PersistableMerchantStore;
import dev.vulcanium.site.tech.model.store.ReadableBrand;
import dev.vulcanium.site.tech.model.store.ReadableMerchantStore;
import dev.vulcanium.site.tech.model.store.ReadableMerchantStoreList;
import dev.vulcanium.site.tech.populator.store.PersistableMerchantStorePopulator;
import dev.vulcanium.site.tech.populator.store.ReadableMerchantStorePopulator;
import dev.vulcanium.site.tech.store.api.exception.ConversionRuntimeException;
import dev.vulcanium.site.tech.store.api.exception.ResourceNotFoundException;
import dev.vulcanium.site.tech.store.api.exception.ServiceRuntimeException;
import dev.vulcanium.site.tech.utils.ImageFilePath;
import dev.vulcanium.site.tech.utils.LanguageUtils;

@Service("storeFacade")
public class StoreFacadeImpl implements StoreFacade {

@Inject
private MerchantStoreService merchantStoreService;

@Inject
private MerchantConfigurationService merchantConfigurationService;

@Inject
private LanguageService languageService;

@Inject
private ContentService contentService;

@Inject
private PersistableMerchantStorePopulator persistableMerchantStorePopulator;

@Inject
@Qualifier("img")
private ImageFilePath imageUtils;

@Inject
private LanguageUtils languageUtils;

@Autowired
private ReadableMerchantStorePopulator readableMerchantStorePopulator;

private static final Logger LOG = LoggerFactory.getLogger(StoreFacadeImpl.class);

@Override
public MerchantStore getByCode(HttpServletRequest request) {
	String code = request.getParameter("store");
	if (StringUtils.isEmpty(code)) {
		code = dev.vulcanium.business.constants.Constants.DEFAULT_STORE;
	}
	return get(code);
}

@Override
public MerchantStore get(String code) {
	try {
		MerchantStore store = merchantStoreService.getByCode(code);
		return store;
	} catch (ServiceException e) {
		LOG.error("Error while getting MerchantStore", e);
		throw new ServiceRuntimeException(e);
	}
	
}

@Override
public ReadableMerchantStore getByCode(String code, String lang) {
	Language language = getLanguage(lang);
	return getByCode(code, language);
}

@Override
public ReadableMerchantStore getFullByCode(String code, String lang) {
	Language language = getLanguage(lang);
	return getFullByCode(code, language);
}

private Language getLanguage(String lang) {
	return languageUtils.getServiceLanguage(lang);
}

@Override
public ReadableMerchantStore getByCode(String code, Language language) {
	MerchantStore store = getMerchantStoreByCode(code);
	return convertMerchantStoreToReadableMerchantStore(language, store);
}

@Override
public ReadableMerchantStore getFullByCode(String code, Language language) {
	MerchantStore store = getMerchantStoreByCode(code);
	return convertMerchantStoreToReadableMerchantStoreWithFullDetails(language, store);
}

@Override
public boolean existByCode(String code) {
	try {
		return merchantStoreService.getByCode(code) != null;
	} catch (ServiceException e) {
		throw new ServiceRuntimeException(e);
	}
}

private ReadableMerchantStore convertMerchantStoreToReadableMerchantStore(Language language, MerchantStore store) {
	ReadableMerchantStore readable = new ReadableMerchantStore();
	
	/**
	 * Language is not important for this conversion using default language
	 */
	try {
		readableMerchantStorePopulator.populate(store, readable, store, language);
	} catch (Exception e) {
		throw new ConversionRuntimeException("Error while populating MerchantStore " + e.getMessage());
	}
	return readable;
}

private ReadableMerchantStore convertMerchantStoreToReadableMerchantStoreWithFullDetails(Language language, MerchantStore store) {
	ReadableMerchantStore readable = new ReadableMerchantStore();
	
	
	/**
	 * Language is not important for this conversion using default language
	 */
	try {
		readableMerchantStorePopulator.populate(store, readable, store, language);
	} catch (Exception e) {
		throw new ConversionRuntimeException("Error while populating MerchantStore " + e.getMessage());
	}
	return readable;
}

private MerchantStore getMerchantStoreByCode(String code) {
	return Optional.ofNullable(get(code))
			       .orElseThrow(() -> new ResourceNotFoundException("Merchant store code [" + code + "] not found"));
}

@Override
public void create(PersistableMerchantStore store) {
	
	Validate.notNull(store, "PersistableMerchantStore must not be null");
	Validate.notNull(store.getCode(), "PersistableMerchantStore.code must not be null");
	MerchantStore storeForCheck = get(store.getCode());
	if (storeForCheck != null) {
		throw new ServiceRuntimeException("MerhantStore " + store.getCode() + " already exists");
	}
	
	MerchantStore mStore = convertPersistableMerchantStoreToMerchantStore(store, languageService.defaultLanguage());
	createMerchantStore(mStore);
	
}

private void createMerchantStore(MerchantStore mStore) {
	try {
		merchantStoreService.saveOrUpdate(mStore);
	} catch (ServiceException e) {
		throw new ServiceRuntimeException(e);
	}
}

private MerchantStore convertPersistableMerchantStoreToMerchantStore(PersistableMerchantStore store,
                                                                     Language language) {
	MerchantStore mStore = new MerchantStore();
	mStore.setWeightunitcode(MeasureUnit.KG.name());
	mStore.setSeizeunitcode(MeasureUnit.IN.name());
	
	try {
		mStore = persistableMerchantStorePopulator.populate(store, mStore, language);
	} catch (ConversionException e) {
		throw new ConversionRuntimeException(e);
	}
	return mStore;
}

@Override
public void update(PersistableMerchantStore store) {
	
	Validate.notNull(store);
	
	MerchantStore mStore = mergePersistableMerchantStoreToMerchantStore(store, store.getCode(),
			languageService.defaultLanguage());
	
	updateMerchantStore(mStore);
	
}

private void updateMerchantStore(MerchantStore mStore) {
	try {
		merchantStoreService.update(mStore);
	} catch (ServiceException e) {
		throw new ServiceRuntimeException(e);
	}
	
}

private MerchantStore mergePersistableMerchantStoreToMerchantStore(PersistableMerchantStore store, String code,
                                                                   Language language) {
	
	MerchantStore mStore = getMerchantStoreByCode(code);
	
	store.setId(mStore.getId());
	
	try {
		mStore = persistableMerchantStorePopulator.populate(store, mStore, language);
	} catch (ConversionException e) {
		throw new ConversionRuntimeException(e);
	}
	return mStore;
}

@Override
public ReadableMerchantStoreList getByCriteria(MerchantStoreCriteria criteria, Language lang) {
	return  getMerchantStoresByCriteria(criteria, lang);
	
}



private ReadableMerchantStoreList getMerchantStoresByCriteria(MerchantStoreCriteria criteria, Language language) {
	try {
		GenericEntityList<MerchantStore> stores =  Optional.ofNullable(merchantStoreService.getByCriteria(criteria))
				                                           .orElseThrow(() -> new ResourceNotFoundException("Criteria did not match any store"));
		
		
		ReadableMerchantStoreList storeList = new ReadableMerchantStoreList();
		storeList.setData(
				(List<ReadableMerchantStore>) stores.getList().stream()
						                              .map(s -> convertMerchantStoreToReadableMerchantStore(language, s))
						                              .collect(Collectors.toList())
		);
		storeList.setTotalPages(stores.getTotalPages());
		storeList.setRecordsTotal(stores.getTotalCount());
		storeList.setNumber(stores.getList().size());
		
		return storeList;
		
	} catch (ServiceException e) {
		throw new ServiceRuntimeException(e);
	}
	
}

@Override
public void delete(String code) {
	
	if (MerchantStore.DEFAULT_STORE.equals(code.toUpperCase())) {
		throw new ServiceRuntimeException("Cannot remove default store");
	}
	
	MerchantStore mStore = getMerchantStoreByCode(code);
	
	try {
		merchantStoreService.delete(mStore);
	} catch (Exception e) {
		LOG.error("Error while deleting MerchantStore", e);
		throw new ServiceRuntimeException("Error while deleting MerchantStore " + e.getMessage());
	}
	
}

@Override
public ReadableBrand getBrand(String code) {
	MerchantStore mStore = getMerchantStoreByCode(code);
	
	ReadableBrand readableBrand = new ReadableBrand();
	if (!StringUtils.isEmpty(mStore.getStoreLogo())) {
		String imagePath = imageUtils.buildStoreLogoFilePath(mStore);
		ReadableImage image = createReadableImage(mStore.getStoreLogo(), imagePath);
		readableBrand.setLogo(image);
	}
	List<MerchantConfigEntity> merchantConfigTOs = getMerchantConfigEntities(mStore);
	readableBrand.getSocialNetworks().addAll(merchantConfigTOs);
	return readableBrand;
}

private List<MerchantConfigEntity> getMerchantConfigEntities(MerchantStore mStore) {
	List<MerchantConfiguration> configurations = getMergeConfigurationsByStore(MerchantConfigurationType.SOCIAL,
			mStore);
	
	return configurations.stream().map(config -> convertToMerchantConfigEntity(config))
			       .collect(Collectors.toList());
}

private List<MerchantConfiguration> getMergeConfigurationsByStore(MerchantConfigurationType configurationType,
                                                                  MerchantStore mStore) {
	try {
		return merchantConfigurationService.listByType(configurationType, mStore);
	} catch (ServiceException e) {
		throw new ServiceRuntimeException("Error wile getting merchantConfigurations " + e.getMessage());
	}
}

private MerchantConfigEntity convertToMerchantConfigEntity(MerchantConfiguration config) {
	MerchantConfigEntity configTO = new MerchantConfigEntity();
	configTO.setId(config.getId());
	configTO.setKey(config.getKey());
	configTO.setType(config.getMerchantConfigurationType());
	configTO.setValue(config.getValue());
	configTO.setActive(config.getActive() != null ? config.getActive().booleanValue() : false);
	return configTO;
}

private MerchantConfiguration convertToMerchantConfiguration(MerchantConfigEntity config,
                                                             MerchantConfigurationType configurationType) {
	MerchantConfiguration configTO = new MerchantConfiguration();
	configTO.setId(config.getId());
	configTO.setKey(config.getKey());
	configTO.setMerchantConfigurationType(configurationType);
	configTO.setValue(config.getValue());
	configTO.setActive(new Boolean(config.isActive()));
	return configTO;
}

private ReadableImage createReadableImage(String storeLogo, String imagePath) {
	ReadableImage image = new ReadableImage();
	image.setName(storeLogo);
	image.setPath(imagePath);
	return image;
}

@Override
public void deleteLogo(String code) {
	MerchantStore store = getByCode(code);
	String image = store.getStoreLogo();
	store.setStoreLogo(null);
	
	try {
		updateMerchantStore(store);
		if (!StringUtils.isEmpty(image)) {
			contentService.removeFile(store.getCode(), image);
		}
	} catch (ServiceException e) {
		throw new ServiceRuntimeException(e.getMessage());
	}
}

@Override
public MerchantStore getByCode(String code) {
	return getMerchantStoreByCode(code);
}

@Override
public void addStoreLogo(String code, InputContentFile cmsContentImage) {
	MerchantStore store = getByCode(code);
	store.setStoreLogo(cmsContentImage.getFileName());
	saveMerchantStore(store);
	addLogoToStore(code, cmsContentImage);
}

private void addLogoToStore(String code, InputContentFile cmsContentImage) {
	try {
		contentService.addLogo(code, cmsContentImage);
	} catch (ServiceException e) {
		throw new ServiceRuntimeException(e);
	}
}

private void saveMerchantStore(MerchantStore store) {
	try {
		merchantStoreService.save(store);
	} catch (ServiceException e) {
		throw new ServiceRuntimeException(e);
	}
	
}

@Override
public void createBrand(String merchantStoreCode, PersistableBrand brand) {
	MerchantStore mStore = getMerchantStoreByCode(merchantStoreCode);
	
	List<MerchantConfigEntity> createdConfigs = brand.getSocialNetworks();
	
	List<MerchantConfiguration> configurations = createdConfigs.stream()
			                                             .map(config -> convertToMerchantConfiguration(config, MerchantConfigurationType.SOCIAL))
			                                             .collect(Collectors.toList());
	try {
		for (MerchantConfiguration mConfigs : configurations) {
			mConfigs.setMerchantStore(mStore);
			if (!StringUtils.isEmpty(mConfigs.getValue())) {
				mConfigs.setMerchantConfigurationType(MerchantConfigurationType.SOCIAL);
				merchantConfigurationService.saveOrUpdate(mConfigs);
			} else {// remove if submited blank and exists
				MerchantConfiguration config = merchantConfigurationService
						                               .getMerchantConfiguration(mConfigs.getKey(), mStore);
				if (config != null) {
					merchantConfigurationService.delete(config);
				}
			}
		}
	} catch (ServiceException se) {
		throw new ServiceRuntimeException(se);
	}
	
}

@Override
public ReadableMerchantStoreList getChildStores(Language language, String code, int page, int count) {
	try {
		MerchantStore retailer = this.getByCode(code);
		if (retailer == null) {
			throw new ResourceNotFoundException("Merchant [" + code + "] not found");
		}
		
		if (retailer.isRetailer() == null || !retailer.isRetailer().booleanValue()) {
			throw new ResourceNotFoundException("Merchant [" + code + "] not a retailer");
		}
		
		
		Page<MerchantStore> children = merchantStoreService.listChildren(code, page, count);
		List<ReadableMerchantStore> readableStores = new ArrayList<ReadableMerchantStore>();
		ReadableMerchantStoreList readableList = new ReadableMerchantStoreList();
		if (!CollectionUtils.isEmpty(children.getContent())) {
			for (MerchantStore store : children)
				readableStores.add(convertMerchantStoreToReadableMerchantStore(language, store));
		}
		readableList.setData(readableStores);
		readableList.setRecordsFiltered(children.getSize());
		readableList.setTotalPages(children.getTotalPages());
		readableList.setRecordsTotal(children.getTotalElements());
		readableList.setNumber(children.getNumber());
		
		return readableList;
	} catch (ServiceException e) {
		throw new ServiceRuntimeException(e);
	}
	
}

@Override
public ReadableMerchantStoreList findAll(MerchantStoreCriteria criteria, Language language, int page, int count) {
	
	try {
		Page<MerchantStore> stores = null;
		List<ReadableMerchantStore> readableStores = new ArrayList<ReadableMerchantStore>();
		ReadableMerchantStoreList readableList = new ReadableMerchantStoreList();
		
		Optional<String> code = Optional.ofNullable(criteria.getStoreCode());
		Optional<String> name = Optional.ofNullable(criteria.getName());
		if(code.isPresent()) {
			
			stores = merchantStoreService.listByGroup(name, code.get(), page, count);
			
		} else {
			if(criteria.isRetailers()) {
				stores = merchantStoreService.listAllRetailers(name, page, count);
			} else {
				stores = merchantStoreService.listAll(name, page, count);
			}
		}
		
		
		if (!CollectionUtils.isEmpty(stores.getContent())) {
			for (MerchantStore store : stores)
				readableStores.add(convertMerchantStoreToReadableMerchantStore(language, store));
		}
		readableList.setData(readableStores);
		readableList.setRecordsTotal(stores.getTotalElements());
		readableList.setTotalPages(stores.getTotalPages());
		readableList.setNumber(stores.getSize());
		readableList.setRecordsFiltered(stores.getSize());
		return readableList;
		
	} catch (ServiceException e) {
		throw new ServiceRuntimeException("Error while finding all merchant", e);
	}
	
	
}

private ReadableMerchantStore convertStoreName(MerchantStore store) {
	ReadableMerchantStore convert = new ReadableMerchantStore();
	convert.setId(store.getId());
	convert.setCode(store.getCode());
	convert.setName(store.getStorename());
	return convert;
}

@Override
public List<ReadableMerchantStore> getMerchantStoreNames(MerchantStoreCriteria criteria) {
	Validate.notNull(criteria, "MerchantStoreCriteria must not be null");
	
	try {
		
		List<ReadableMerchantStore> stores = null;
		Optional<String> code = Optional.ofNullable(criteria.getStoreCode());
		
		if(code.isPresent()) {
			
			stores = merchantStoreService.findAllStoreNames(code.get()).stream()
					         .map(s -> convertStoreName(s))
					         .collect(Collectors.toList());
		} else {
			stores = merchantStoreService.findAllStoreNames().stream()
					         .map(s -> convertStoreName(s))
					         .collect(Collectors.toList());
		}
		
		
		return stores;
	} catch (ServiceException e) {
		throw new ServiceRuntimeException("Exception while getting store name",e);
	}
	
	
}

@Override
public List<Language> supportedLanguages(MerchantStore store) {
	
	Validate.notNull(store, "MerchantStore cannot be null");
	Validate.notNull(store.getClass(), "MerchantStore code cannot be null");
	
	if(!CollectionUtils.isEmpty(store.getLanguages())) {
		return store.getLanguages();
	}
	
	try {
		store = merchantStoreService.getByCode(store.getCode());
	} catch (ServiceException e) {
		throw new ServiceRuntimeException("An exception occured when getting store [" + store.getCode() + "]");
	}
	
	if(store!=null) {
		return store.getLanguages();
	}
	
	return Collections.emptyList();
}

}