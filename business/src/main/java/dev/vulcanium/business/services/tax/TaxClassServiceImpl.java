package dev.vulcanium.business.services.tax;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.tax.taxclass.TaxClass;
import dev.vulcanium.business.repositories.tax.TaxClassRepository;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityServiceImpl;
import java.util.List;
import jakarta.inject.Inject;
import org.jsoup.helper.Validate;
import org.springframework.stereotype.Service;

@Service("taxClassService")
public class TaxClassServiceImpl extends SalesManagerEntityServiceImpl<Long, TaxClass>
		implements TaxClassService {

	private TaxClassRepository taxClassRepository;
	
	@Inject
	public TaxClassServiceImpl(TaxClassRepository taxClassRepository) {
		super(taxClassRepository);
		
		this.taxClassRepository = taxClassRepository;
	}
	
	@Override
	public List<TaxClass> listByStore(MerchantStore store) throws ServiceException {	
		return taxClassRepository.findByStore(store.getId());
	}
	
	@Override
	public TaxClass getByCode(String code) throws ServiceException {
		return taxClassRepository.findByCode(code);
	}
	
	@Override
	public TaxClass getByCode(String code, MerchantStore store) throws ServiceException {
		return taxClassRepository.findByStoreAndCode(store.getId(), code);
	}
	
	@Override
	public void delete(TaxClass taxClass) throws ServiceException {
		
		TaxClass t = getById(taxClass.getId());
		super.delete(t);
		
	}
	
	@Override
	public TaxClass getById(Long id) {
		return taxClassRepository.getOne(id);
	}

	@Override
	public boolean exists(String code, MerchantStore store) throws ServiceException {
		Validate.notNull(code, "TaxClass code cannot be empty");
		Validate.notNull(store, "MerchantStore cannot be null");
		
		return taxClassRepository.findByStoreAndCode(store.getId(), code) != null;

	}
	
	@Override
	public TaxClass saveOrUpdate(TaxClass taxClass) throws ServiceException {
		if(taxClass.getId()!=null && taxClass.getId() > 0) {
			this.update(taxClass);
		} else {
			taxClass = super.saveAndFlush(taxClass);
		}
		return taxClass;
	}

	

}
