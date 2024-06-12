package dev.vulcanium.site.tech.store.facade.product;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.product.type.ProductType;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.catalog.product.type.ProductTypeService;
import dev.vulcanium.business.store.api.exception.OperationNotAllowedException;
import dev.vulcanium.business.store.api.exception.ResourceNotFoundException;
import dev.vulcanium.business.store.api.exception.ServiceRuntimeException;
import dev.vulcanium.site.tech.mapper.catalog.PersistableProductTypeMapper;
import dev.vulcanium.site.tech.mapper.catalog.ReadableProductTypeMapper;
import dev.vulcanium.site.tech.model.catalog.product.type.PersistableProductType;
import dev.vulcanium.site.tech.model.catalog.product.type.ReadableProductType;
import dev.vulcanium.site.tech.model.catalog.product.type.ReadableProductTypeList;
import java.util.stream.Collectors;
import org.jsoup.helper.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service("productTypeFacade")
public class ProductTypeFacadeImpl implements ProductTypeFacade {

@Autowired
private ProductTypeService productTypeService;

@Autowired
private ReadableProductTypeMapper readableProductTypeMapper;

@Autowired
private PersistableProductTypeMapper persistableProductTypeMapper;

@Override
public ReadableProductTypeList getByMerchant(MerchantStore store, Language language,  int count, int page) {
	
	Validate.notNull(store, "MerchantStore cannot be null");
	ReadableProductTypeList returnList = new ReadableProductTypeList();
	
	try {
		
		Page<ProductType> types = productTypeService.getByMerchant(store, language, page, count);
		
		if(types != null) {
			returnList.setList(types.getContent().stream().map(t -> readableProductTypeMapper.convert(t, store, language)).collect(Collectors.toList()));
			returnList.setTotalPages(types.getTotalPages());
			returnList.setRecordsTotal(types.getTotalElements());
			returnList.setRecordsFiltered(types.getSize());
		}
		
		return returnList;
	} catch (Exception e) {
		throw new ServiceRuntimeException(
				"An exception occured while getting product types for merchant[ " + store.getCode() + "]", e);
	}
	
}

@Override
public ReadableProductType get(MerchantStore store, Long id, Language language) {
	
	Validate.notNull(store, "MerchantStore cannot be null");
	Validate.notNull(id, "ProductType code cannot be empty");
	try {
		
		ProductType type = null;
		if(language == null) {
			type = productTypeService.getById(id, store);
		} else {
			type = productTypeService.getById(id, store, language);
		}
		
		if(type == null) {
			throw new ResourceNotFoundException("Product type [" + id + "] not found for store [" + store.getCode() + "]");
		}
		
		ReadableProductType readableType = readableProductTypeMapper.convert(type, store, language);
		
		
		return readableType;
		
	} catch(Exception e) {
		throw new ServiceRuntimeException(
				"An exception occured while getting product type [" + id + "] not found for store [" + store.getCode() + "]", e);
	}
	
}

@Override
public Long save(PersistableProductType type, MerchantStore store, Language language) {
	
	Validate.notNull(type,"ProductType cannot be null");
	Validate.notNull(store,"MerchantStore cannot be null");
	Validate.notNull(type.getCode(),"ProductType code cannot be empty");
	
	try {
		
		if(this.exists(type.getCode(), store, language)) {
			throw new OperationNotAllowedException(
					"Product type [" + type.getCode() + "] already exist for store [" + store.getCode() + "]");
		}
		
		ProductType model = persistableProductTypeMapper.convert(type, store, language);
		model.setMerchantStore(store);
		ProductType saved = productTypeService.saveOrUpdate(model);
		return saved.getId();
		
	} catch(Exception e) {
		throw new ServiceRuntimeException(
				"An exception occured while saving product type",e);
	}
	
}

@Override
public void update(PersistableProductType type, Long id, MerchantStore store, Language language) {
	Validate.notNull(type,"ProductType cannot be null");
	Validate.notNull(store,"MerchantStore cannot be null");
	Validate.notNull(id,"id cannot be empty");
	
	try {
		
		ProductType t = productTypeService.getById(id, store, language);
		if(t == null) {
			throw new ResourceNotFoundException(
					"Product type [" + type.getCode() + "] does not exist for store [" + store.getCode() + "]");
		}
		
		type.setId(t.getId());
		type.setCode(t.getCode());
		
		ProductType model = persistableProductTypeMapper.merge(type, t, store, language);
		model.setMerchantStore(store);
		productTypeService.saveOrUpdate(model);
		
	} catch(Exception e) {
		throw new ServiceRuntimeException(
				"An exception occured while saving product type",e);
	}
	
}

@Override
public void delete(Long id, MerchantStore store, Language language) {
	Validate.notNull(store,"MerchantStore cannot be null");
	Validate.notNull(id,"id cannot be empty");
	
	try {
		
		ProductType t = productTypeService.getById(id, store, language);
		if(t == null) {
			throw new ResourceNotFoundException(
					"Product type [" + id + "] does not exist for store [" + store.getCode() + "]");
		}
		
		productTypeService.delete(t);
		
		
	} catch(Exception e) {
		throw new ServiceRuntimeException(
				"An exception occured while saving product type",e);
	}
	
}

@Override
public boolean exists(String code, MerchantStore store, Language language) {
	ProductType t;
	try {
		t = productTypeService.getByCode(code, store, language);
	} catch (ServiceException e) {
		throw new RuntimeException("An exception occured while getting product type [" + code + "] for merchant store [" + store.getCode() +"]",e);
	}
	if(t != null) {
		return true;
	}
	return false;
}

@Override
public ReadableProductType get(MerchantStore store, String code, Language language) {
	ProductType t;
	try {
		t = productTypeService.getByCode(code, store, language);
	} catch (ServiceException e) {
		throw new RuntimeException("An exception occured while getting product type [" + code + "] for merchant store [" + store.getCode() +"]",e);
	}
	
	if(t == null) {
		throw new ResourceNotFoundException("Product type [" + code + "] not found for merchant [" + store.getCode() + "]");
	}
	
	ReadableProductType readableType = readableProductTypeMapper.convert(t, store, language);
	return readableType;
	
}


}
