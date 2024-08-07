package dev.vulcanium.site.tech.store.facade.manufacturer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.inject.Inject;
import org.jsoup.helper.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.services.catalog.category.CategoryService;
import dev.vulcanium.business.services.catalog.product.manufacturer.ManufacturerService;
import dev.vulcanium.business.services.reference.language.LanguageService;
import dev.vulcanium.business.model.catalog.category.Category;
import dev.vulcanium.business.model.catalog.product.manufacturer.Manufacturer;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.mapper.Mapper;
import dev.vulcanium.site.tech.model.catalog.manufacturer.PersistableManufacturer;
import dev.vulcanium.site.tech.model.catalog.manufacturer.ReadableManufacturer;
import dev.vulcanium.site.tech.model.catalog.manufacturer.ReadableManufacturerList;
import dev.vulcanium.site.tech.model.entity.ListCriteria;
import dev.vulcanium.site.tech.populator.manufacturer.PersistableManufacturerPopulator;
import dev.vulcanium.site.tech.populator.manufacturer.ReadableManufacturerPopulator;
import dev.vulcanium.business.store.api.exception.ResourceNotFoundException;
import dev.vulcanium.business.store.api.exception.ServiceRuntimeException;
import dev.vulcanium.business.store.api.exception.UnauthorizedException;

@Service("manufacturerFacade")
public class ManufacturerFacadeImpl implements ManufacturerFacade {

@Inject
private Mapper<Manufacturer, ReadableManufacturer> readableManufacturerConverter;


@Autowired
private ManufacturerService manufacturerService;

@Autowired
private CategoryService categoryService;

@Inject
private LanguageService languageService;

@Override
public List<ReadableManufacturer> getByProductInCategory(MerchantStore store, Language language,
                                                         Long categoryId) {
	Validate.notNull(store,"MerchantStore cannot be null");
	Validate.notNull(language, "Language cannot be null");
	Validate.notNull(categoryId,"Category id cannot be null");
	
	Category category = categoryService.getById(categoryId, store.getId());
	
	if(category == null) {
		throw new ResourceNotFoundException("Category with id [" + categoryId + "] not found");
	}
	
	if(category.getMerchantStore().getId().longValue() != store.getId().longValue()) {
		throw new UnauthorizedException("Merchant [" + store.getCode() + "] not authorized");
	}
	
	try {
		List<Manufacturer> manufacturers = manufacturerService.listByProductsInCategory(store, category, language);
		
		List<ReadableManufacturer> manufacturersList = manufacturers.stream()
				                                               .sorted(new Comparator<Manufacturer>() {
					                                               @Override
					                                               public int compare(final Manufacturer object1, final Manufacturer object2) {
						                                               return object1.getCode().compareTo(object2.getCode());
					                                               }
				                                               })
				                                               .map(manuf -> readableManufacturerConverter.convert(manuf, store, language))
				                                               .collect(Collectors.toList());
		
		return manufacturersList;
	} catch (ServiceException e) {
		throw new ServiceRuntimeException(e);
	}
	
}

@Override
public void saveOrUpdateManufacturer(PersistableManufacturer manufacturer, MerchantStore store,
                                     Language language) throws Exception {
	
	PersistableManufacturerPopulator populator = new PersistableManufacturerPopulator();
	populator.setLanguageService(languageService);
	
	
	Manufacturer manuf = new Manufacturer();
	
	if(manufacturer.getId() != null && manufacturer.getId().longValue() > 0) {
		manuf = manufacturerService.getById(manufacturer.getId());
		if(manuf == null) {
			throw new ResourceNotFoundException("Manufacturer with id [" + manufacturer.getId() + "] not found");
		}
		
		if(manuf.getMerchantStore().getId().intValue() != store.getId().intValue()) {
			throw new ResourceNotFoundException("Manufacturer with id [" + manufacturer.getId() + "] not found for store [" + store.getId() + "]");
		}
	}
	
	populator.populate(manufacturer, manuf, store, language);
	
	manufacturerService.saveOrUpdate(manuf);
	
	manufacturer.setId(manuf.getId());
	
}

@Override
public void deleteManufacturer(Manufacturer manufacturer, MerchantStore store, Language language)
		throws Exception {
	manufacturerService.delete(manufacturer);
	
}

@Override
public ReadableManufacturer getManufacturer(Long id, MerchantStore store, Language language)
		throws Exception {
	Manufacturer manufacturer = manufacturerService.getById(id);
	
	
	
	if (manufacturer == null) {
		throw new ResourceNotFoundException("Manufacturer [" + id + "] not found");
	}
	
	if(manufacturer.getMerchantStore().getId() != store.getId()) {
		throw new ResourceNotFoundException("Manufacturer [" + id + "] not found for store [" + store.getId() + "]");
	}
	
	ReadableManufacturer readableManufacturer = new ReadableManufacturer();
	
	ReadableManufacturerPopulator populator = new ReadableManufacturerPopulator();
	readableManufacturer = populator.populate(manufacturer, readableManufacturer, store, language);
	
	
	return readableManufacturer;
}

@Override
public ReadableManufacturerList getAllManufacturers(MerchantStore store, Language language, ListCriteria criteria, int page, int count) {
	
	ReadableManufacturerList readableList = new ReadableManufacturerList();
	try {
		/**
		 * Is this a pageable request
		 */
		
		List<Manufacturer> manufacturers = null;
		if(page == 0 && count == 0) {
			//need total count
			int total = manufacturerService.count(store);
			
			if(language != null) {
				manufacturers = manufacturerService.listByStore(store, language);
			} else {
				manufacturers = manufacturerService.listByStore(store);
			}
			readableList.setRecordsTotal(total);
			readableList.setNumber(manufacturers.size());
		} else {
			
			Page<Manufacturer> m = null;
			if(language != null) {
				m = manufacturerService.listByStore(store, language, criteria.getName(), page, count);
			} else {
				m = manufacturerService.listByStore(store, criteria.getName(), page, count);
			}
			manufacturers = m.getContent();
			readableList.setTotalPages(m.getTotalPages());
			readableList.setRecordsTotal(m.getTotalElements());
			readableList.setNumber(m.getNumber());
		}
		
		
		ReadableManufacturerPopulator populator = new ReadableManufacturerPopulator();
		List<ReadableManufacturer> returnList = new ArrayList<ReadableManufacturer>();
		
		for (Manufacturer m : manufacturers) {
			ReadableManufacturer readableManufacturer = new ReadableManufacturer();
			populator.populate(m, readableManufacturer, store, language);
			returnList.add(readableManufacturer);
		}
		
		readableList.setManufacturers(returnList);
		return readableList;
		
	} catch (Exception e) {
		throw new ServiceRuntimeException("Error while get manufacturers",e);
	}
}


@Override
public boolean manufacturerExist(MerchantStore store, String manufacturerCode) {
	Validate.notNull(store,"Store must not be null");
	Validate.notNull(manufacturerCode,"Manufacturer code must not be null");
	boolean exists = false;
	Manufacturer manufacturer = manufacturerService.getByCode(store, manufacturerCode);
	if(manufacturer!=null) {
		exists = true;
	}
	return exists;
}

@Override
public ReadableManufacturerList listByStore(MerchantStore store, Language language, ListCriteria criteria, int page,
                                            int count) {
	
	ReadableManufacturerList readableList = new ReadableManufacturerList();
	
	try {
		/**
		 * Is this a pageable request
		 */
		
		List<Manufacturer> manufacturers = null;
		
		Page<Manufacturer> m = null;
		if(language != null) {
			m = manufacturerService.listByStore(store, language, criteria.getName(), page, count);
		} else {
			m = manufacturerService.listByStore(store, criteria.getName(), page, count);
		}
		
		manufacturers = m.getContent();
		readableList.setTotalPages(m.getTotalPages());
		readableList.setRecordsTotal(m.getTotalElements());
		readableList.setNumber(m.getContent().size());
		
		
		
		ReadableManufacturerPopulator populator = new ReadableManufacturerPopulator();
		List<ReadableManufacturer> returnList = new ArrayList<ReadableManufacturer>();
		
		for (Manufacturer mf : manufacturers) {
			ReadableManufacturer readableManufacturer = new ReadableManufacturer();
			populator.populate(mf, readableManufacturer, store, language);
			returnList.add(readableManufacturer);
		}
		
		readableList.setManufacturers(returnList);
		return readableList;
		
	} catch (Exception e) {
		throw new ServiceRuntimeException("Error while get manufacturers",e);
	}
	
}


}
