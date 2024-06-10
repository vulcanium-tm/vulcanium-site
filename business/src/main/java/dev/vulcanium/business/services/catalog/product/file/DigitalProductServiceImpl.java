package dev.vulcanium.business.services.catalog.product.file;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.catalog.product.file.DigitalProduct;
import dev.vulcanium.business.model.content.FileContentType;
import dev.vulcanium.business.model.content.InputContentFile;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.modules.cms.content.StaticContentFileManager;
import dev.vulcanium.business.repositories.catalog.product.file.DigitalProductRepository;
import dev.vulcanium.business.services.catalog.product.ProductService;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityServiceImpl;
import java.util.Optional;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("digitalProductService")
public class DigitalProductServiceImpl extends SalesManagerEntityServiceImpl<Long, DigitalProduct> 
	implements DigitalProductService {
	

	private DigitalProductRepository digitalProductRepository;
	
    @Inject
    StaticContentFileManager productDownloadsFileManager;
    
    @Inject
    ProductService productService;

	@Inject
	public DigitalProductServiceImpl(DigitalProductRepository digitalProductRepository) {
		super(digitalProductRepository);
		this.digitalProductRepository = digitalProductRepository;
	}
	
	@Override
	public void addProductFile(Product product, DigitalProduct digitalProduct, InputContentFile inputFile) throws ServiceException {
	
		Assert.notNull(digitalProduct,"DigitalProduct cannot be null");
		Assert.notNull(product,"Product cannot be null");
		digitalProduct.setProduct(product);

		try {
			
			Assert.notNull(inputFile.getFile(),"InputContentFile.file cannot be null");
			
			Assert.notNull(product.getMerchantStore(),"Product.merchantStore cannot be null");
			this.saveOrUpdate(digitalProduct);
			
		    String path = null;
		    
			
			productDownloadsFileManager.addFile(product.getMerchantStore().getCode(), Optional.of(path), inputFile);
			
			product.setProductVirtual(true);
			productService.update(product);
		
		} catch (Exception e) {
			throw new ServiceException(e);
		} finally {
			try {

				if(inputFile.getFile()!=null) {
					inputFile.getFile().close();
				}

			} catch(Exception ignore) {}
		}
		
		
	}
	
	@Override
	public DigitalProduct getByProduct(MerchantStore store, Product product) throws ServiceException {
		return digitalProductRepository.findByProduct(store.getId(), product.getId());
	}
	
	@Override
	public void delete(DigitalProduct digitalProduct) throws ServiceException {
		
		Assert.notNull(digitalProduct,"DigitalProduct cannot be null");
		Assert.notNull(digitalProduct.getProduct(),"DigitalProduct.product cannot be null");
		//refresh file
		digitalProduct = this.getById(digitalProduct.getId());
		super.delete(digitalProduct);
		
		String path = null;

		productDownloadsFileManager.removeFile(digitalProduct.getProduct().getMerchantStore().getCode(), FileContentType.PRODUCT, digitalProduct.getProductFileName(), Optional.of(path));
		digitalProduct.getProduct().setProductVirtual(false);
		productService.update(digitalProduct.getProduct());
	}
	
	
	@Override
	public void saveOrUpdate(DigitalProduct digitalProduct) throws ServiceException {
		
		Assert.notNull(digitalProduct,"DigitalProduct cannot be null");
		Assert.notNull(digitalProduct.getProduct(),"DigitalProduct.product cannot be null");
		if(digitalProduct.getId()==null || digitalProduct.getId() ==0) {
			super.save(digitalProduct);
		} else {
			super.create(digitalProduct);
		}
		
		digitalProduct.getProduct().setProductVirtual(true);
		productService.update(digitalProduct.getProduct());
		
		
	}
	

	

}
