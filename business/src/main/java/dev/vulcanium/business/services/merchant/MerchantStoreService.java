package dev.vulcanium.business.services.merchant;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.common.GenericEntityList;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.merchant.MerchantStoreCriteria;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityService;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface MerchantStoreService extends SalesManagerEntityService<Integer, MerchantStore>{
	

	MerchantStore getByCode(String code) throws ServiceException;
	
	MerchantStore getParent(String code) throws ServiceException;
	
	List<MerchantStore> findAllStoreNames() throws ServiceException;
	
	List<MerchantStore> findAllStoreNames(String code) throws ServiceException;

	List<MerchantStore> findAllStoreCodeNameEmail() throws ServiceException;

	Page<MerchantStore> listAll(Optional<String> storeName, int page, int count) throws ServiceException;
	
	Page<MerchantStore> listByGroup(Optional<String> storeName, String code, int page, int count) throws ServiceException;

	Page<MerchantStore> listAllRetailers(Optional<String> storeName, int page, int count) throws ServiceException;
	
	Page<MerchantStore> listChildren(String code, int page, int count) throws ServiceException;

	boolean existByCode(String code);
	
	/**
	 * Is parent or child and part of a specific group
	 * @param code
	 * @return
	 */
	boolean isStoreInGroup(String code) throws ServiceException;

	void saveOrUpdate(MerchantStore store) throws ServiceException;
	
	GenericEntityList<MerchantStore> getByCriteria(MerchantStoreCriteria criteria) throws ServiceException;

}
