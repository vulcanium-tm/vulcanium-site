package dev.vulcanium.business.services.reference.init;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.constants.SchemaConstant;
import dev.vulcanium.business.model.catalog.product.manufacturer.Manufacturer;
import dev.vulcanium.business.model.catalog.product.manufacturer.ManufacturerDescription;
import dev.vulcanium.business.model.catalog.product.type.ProductType;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.country.Country;
import dev.vulcanium.business.model.reference.country.CountryDescription;
import dev.vulcanium.business.model.reference.currency.Currency;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.model.reference.zone.Zone;
import dev.vulcanium.business.model.reference.zone.ZoneDescription;
import dev.vulcanium.business.model.system.IntegrationModule;
import dev.vulcanium.business.model.system.optin.Optin;
import dev.vulcanium.business.model.system.optin.OptinType;
import dev.vulcanium.business.model.tax.taxclass.TaxClass;
import dev.vulcanium.business.model.user.Group;
import dev.vulcanium.business.model.user.GroupType;
import dev.vulcanium.business.model.user.Permission;
import dev.vulcanium.business.services.catalog.product.manufacturer.ManufacturerService;
import dev.vulcanium.business.services.catalog.product.type.ProductTypeService;
import dev.vulcanium.business.services.merchant.MerchantStoreService;
import dev.vulcanium.business.services.reference.country.CountryService;
import dev.vulcanium.business.services.reference.currency.CurrencyService;
import dev.vulcanium.business.services.reference.language.LanguageService;
import dev.vulcanium.business.services.reference.loader.IntegrationModulesLoader;
import dev.vulcanium.business.services.reference.loader.ZonesLoader;
import dev.vulcanium.business.services.reference.zone.ZoneService;
import dev.vulcanium.business.services.system.ModuleConfigurationService;
import dev.vulcanium.business.services.system.optin.OptinService;
import dev.vulcanium.business.services.tax.TaxClassService;
import dev.vulcanium.business.services.user.GroupService;
import dev.vulcanium.business.services.user.PermissionService;
import dev.vulcanium.business.utils.SecurityGroupsBuilder;
import java.sql.Date;
import java.util.*;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("initializationDatabase")
public class InitializationDatabaseImpl implements InitializationDatabase {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InitializationDatabaseImpl.class);
	

	@Inject
	private ZoneService zoneService;
	
	@Inject
	private LanguageService languageService;
	
	@Inject
	private CountryService countryService;
	
	@Inject
	private CurrencyService currencyService;
	
	@Inject
	protected MerchantStoreService merchantService;
		
	@Inject
	protected ProductTypeService productTypeService;
	
	@Inject
	private TaxClassService taxClassService;
	
	@Inject
	private ZonesLoader zonesLoader;
	
	@Inject
	private IntegrationModulesLoader modulesLoader;
	
	@Inject
	private ManufacturerService manufacturerService;
	
	@Inject
	private ModuleConfigurationService moduleConfigurationService;
	
	@Inject
	private OptinService optinService;
	
	@Inject
	protected GroupService   groupService;
	
	@Inject
	protected PermissionService   permissionService;

	private String name;
	
	public boolean isEmpty() {
		return languageService.count() == 0;
	}
	
	@Transactional
	public void populate(String contextName) throws ServiceException {
		this.name =  contextName;
		
		createSecurityGroups();
		createLanguages();
		createCountries();
		createZones();
		createCurrencies();
		createSubReferences();
		createModules();
		createMerchant();


	}
	
	private void createSecurityGroups() throws ServiceException {
		
		  //create permissions
		  //Map name object
		  Map<String, Permission> permissionKeys = new HashMap<String, Permission>();
		  Permission AUTH = new Permission("AUTH");
		  permissionService.create(AUTH);
		  permissionKeys.put(AUTH.getPermissionName(), AUTH);
		  
		  Permission SUPERADMIN = new Permission("SUPERADMIN");
		  permissionService.create(SUPERADMIN);
		  permissionKeys.put(SUPERADMIN.getPermissionName(), SUPERADMIN);
		  
		  Permission ADMIN = new Permission("ADMIN");
		  permissionService.create(ADMIN);
		  permissionKeys.put(ADMIN.getPermissionName(), ADMIN);
		  
		  Permission PRODUCTS = new Permission("PRODUCTS");
		  permissionService.create(PRODUCTS);
		  permissionKeys.put(PRODUCTS.getPermissionName(), PRODUCTS);
		  
		  Permission ORDER = new Permission("ORDER");
		  permissionService.create(ORDER);
		  permissionKeys.put(ORDER.getPermissionName(), ORDER);
		  
		  Permission CONTENT = new Permission("CONTENT");
		  permissionService.create(CONTENT);
		  permissionKeys.put(CONTENT.getPermissionName(), CONTENT);
		  
		  Permission STORE = new Permission("STORE");
		  permissionService.create(STORE);
		  permissionKeys.put(STORE.getPermissionName(), STORE);
		  
		  Permission TAX = new Permission("TAX");
		  permissionService.create(TAX);
		  permissionKeys.put(TAX.getPermissionName(), TAX);
		  
		  Permission PAYMENT = new Permission("PAYMENT");
		  permissionService.create(PAYMENT);
		  permissionKeys.put(PAYMENT.getPermissionName(), PAYMENT);
		  
		  Permission CUSTOMER = new Permission("CUSTOMER");
		  permissionService.create(CUSTOMER);
		  permissionKeys.put(CUSTOMER.getPermissionName(), CUSTOMER);
		  
		  Permission SHIPPING = new Permission("SHIPPING");
		  permissionService.create(SHIPPING);
		  permissionKeys.put(SHIPPING.getPermissionName(), SHIPPING);
		  
		  Permission AUTH_CUSTOMER = new Permission("AUTH_CUSTOMER");
		  permissionService.create(AUTH_CUSTOMER);
		  permissionKeys.put(AUTH_CUSTOMER.getPermissionName(), AUTH_CUSTOMER);
		
		  SecurityGroupsBuilder groupBuilder = new SecurityGroupsBuilder();
		  groupBuilder
		  .addGroup("SUPERADMIN", GroupType.ADMIN)
		  .addPermission(permissionKeys.get("AUTH"))
		  .addPermission(permissionKeys.get("SUPERADMIN"))
		  .addPermission(permissionKeys.get("ADMIN"))
		  .addPermission(permissionKeys.get("PRODUCTS"))
		  .addPermission(permissionKeys.get("ORDER"))
		  .addPermission(permissionKeys.get("CONTENT"))
		  .addPermission(permissionKeys.get("STORE"))
		  .addPermission(permissionKeys.get("TAX"))
		  .addPermission(permissionKeys.get("PAYMENT"))
		  .addPermission(permissionKeys.get("CUSTOMER"))
		  .addPermission(permissionKeys.get("SHIPPING"))
		  
		  .addGroup("ADMIN", GroupType.ADMIN)
		  .addPermission(permissionKeys.get("AUTH"))
		  .addPermission(permissionKeys.get("ADMIN"))
		  .addPermission(permissionKeys.get("PRODUCTS"))
		  .addPermission(permissionKeys.get("ORDER"))
		  .addPermission(permissionKeys.get("CONTENT"))
		  .addPermission(permissionKeys.get("STORE"))
		  .addPermission(permissionKeys.get("TAX"))
		  .addPermission(permissionKeys.get("PAYMENT"))
		  .addPermission(permissionKeys.get("CUSTOMER"))
		  .addPermission(permissionKeys.get("SHIPPING"))
		  
		  .addGroup("ADMIN_RETAILER", GroupType.ADMIN)
		  .addPermission(permissionKeys.get("AUTH"))
		  .addPermission(permissionKeys.get("ADMIN"))
		  .addPermission(permissionKeys.get("PRODUCTS"))
		  .addPermission(permissionKeys.get("ORDER"))
		  .addPermission(permissionKeys.get("CONTENT"))
		  .addPermission(permissionKeys.get("STORE"))
		  .addPermission(permissionKeys.get("TAX"))
		  .addPermission(permissionKeys.get("PAYMENT"))
		  .addPermission(permissionKeys.get("CUSTOMER"))
		  .addPermission(permissionKeys.get("SHIPPING"))
		  
		  .addGroup("ADMIN_STORE", GroupType.ADMIN)
		  .addPermission(permissionKeys.get("AUTH"))
		  .addPermission(permissionKeys.get("CONTENT"))
		  .addPermission(permissionKeys.get("STORE"))
		  .addPermission(permissionKeys.get("TAX"))
		  .addPermission(permissionKeys.get("PAYMENT"))
		  .addPermission(permissionKeys.get("CUSTOMER"))
		  .addPermission(permissionKeys.get("SHIPPING"))
		  
		  .addGroup("ADMIN_CATALOGUE", GroupType.ADMIN)
		  .addPermission(permissionKeys.get("AUTH"))
		  .addPermission(permissionKeys.get("PRODUCTS"))
		  
		  .addGroup("ADMIN_ORDER", GroupType.ADMIN)
		  .addPermission(permissionKeys.get("AUTH"))
		  .addPermission(permissionKeys.get("ORDER"))
		  
		  .addGroup("ADMIN_CONTENT", GroupType.ADMIN)
		  .addPermission(permissionKeys.get("AUTH"))
		  .addPermission(permissionKeys.get("CONTENT"))
		  
		  .addGroup("CUSTOMER", GroupType.CUSTOMER)
		  .addPermission(permissionKeys.get("AUTH"))
		  .addPermission(permissionKeys.get("AUTH_CUSTOMER"));
		  
		  for(Group g : groupBuilder.build()) {
			  groupService.create(g);
		  }

		
	}
	


	private void createCurrencies() throws ServiceException {
		LOGGER.info(String.format("%s : Populating Currencies ", name));

		for (String code : SchemaConstant.CURRENCY_MAP.keySet()) {
  
            try {
            	java.util.Currency c = java.util.Currency.getInstance(code);
            	
            	if(c==null) {
            		LOGGER.info(String.format("%s : Populating Currencies : no currency for code : %s", name, code));
            	}
            	
            		//check if it exist
            		
	            	Currency currency = new Currency();
	            	currency.setName(c.getCurrencyCode());
	            	currency.setCurrency(c);
	            	currencyService.create(currency);

            //System.out.println(l.getCountry() + "   " + c.getSymbol() + "  " + c.getSymbol(l));
            } catch (IllegalArgumentException e) {
            	LOGGER.info(String.format("%s : Populating Currencies : no currency for code : %s", name, code));
            }
        }  
	}

	private void createCountries() throws ServiceException {
		LOGGER.info(String.format("%s : Populating Countries ", name));
		List<Language> languages = languageService.list();
		for(String code : SchemaConstant.COUNTRY_ISO_CODE) {
			Locale locale = SchemaConstant.LOCALES.get(code);
			if (locale != null) {
				Country country = new Country(code);
				countryService.create(country);
				
				for (Language language : languages) {
					String name = locale.getDisplayCountry(new Locale(language.getCode()));
					//byte[] ptext = value.getBytes(Constants.ISO_8859_1); 
					//String name = new String(ptext, Constants.UTF_8); 
					CountryDescription description = new CountryDescription(language, name);
					countryService.addCountryDescription(country, description);
				}
			}
		}
	}
	
	private void createZones() throws ServiceException {
		LOGGER.info(String.format("%s : Populating Zones ", name));
        try {

    		  Map<String,Zone> zonesMap = new HashMap<String,Zone>();
    		  zonesMap = zonesLoader.loadZones("reference/zoneconfig.json");
    		  
    		  this.addZonesToDb(zonesMap);
/*              
              for (Map.Entry<String, Zone> entry : zonesMap.entrySet()) {
            	    String key = entry.getKey();
            	    Zone value = entry.getValue();
            	    if(value.getDescriptions()==null) {
            	    	LOGGER.warn("This zone " + key + " has no descriptions");
            	    	continue;
            	    }
            	    
            	    List<ZoneDescription> zoneDescriptions = value.getDescriptions();
            	    value.setDescriptons(null);

            	    zoneService.create(value);
            	    
            	    for(ZoneDescription description : zoneDescriptions) {
            	    	description.setZone(value);
            	    	zoneService.addDescription(value, description);
            	    }
              }*/
              
              //lookup additional zones
              //iterate configured languages
      		  LOGGER.info("Populating additional zones");

              //load reference/zones/* (zone config for additional country)
              //example in.json and in-fr.son
              //will load es zones and use a specific file for french es zones
      		  List<Map<String, Zone>> loadIndividualZones = zonesLoader.loadIndividualZones();
      		  
      		loadIndividualZones.forEach(this::addZonesToDb);

  		} catch (Exception e) {
  		    
  			throw new ServiceException(e);
  		}

	}

	
	private void addZonesToDb(Map<String,Zone> zonesMap) throws RuntimeException {
		
		try {
		
	        for (Map.Entry<String, Zone> entry : zonesMap.entrySet()) {
	    	    String key = entry.getKey();
	    	    Zone value = entry.getValue();

	    	    if(value.getDescriptions()==null) {
	    	    	LOGGER.warn("This zone " + key + " has no descriptions");
	    	    	continue;
	    	    }
	    	    
	    	    List<ZoneDescription> zoneDescriptions = value.getDescriptions();
	    	    value.setDescriptons(null);
	
	    	    zoneService.create(value);
	    	    
	    	    for(ZoneDescription description : zoneDescriptions) {
	    	    	description.setZone(value);
	    	    	zoneService.addDescription(value, description);
	    	    }
	        }
        
		}catch(Exception e) {
			LOGGER.error("An error occured while loading zones",e);
			
		}
		
	}
	
	private void createLanguages() throws ServiceException {
		LOGGER.info(String.format("%s : Populating Languages ", name));
		for(String code : SchemaConstant.LANGUAGE_ISO_CODE) {
			Language language = new Language(code);
			languageService.create(language);
		}
	}
	
	private void createMerchant() throws ServiceException {
		LOGGER.info(String.format("%s : Creating merchant ", name));
		
		Date date = new Date(System.currentTimeMillis());
		
		Language en = languageService.getByCode("en");
		Country ca = countryService.getByCode("CA");
		Currency currency = currencyService.getByCode("CAD");
		Zone qc = zoneService.getByCode("QC");
		
		List<Language> supportedLanguages = new ArrayList<Language>();
		supportedLanguages.add(en);
		
		//create a merchant
		MerchantStore store = new MerchantStore();
		store.setCountry(ca);
		store.setCurrency(currency);
		store.setDefaultLanguage(en);
		store.setInBusinessSince(date);
		store.setZone(qc);
		store.setStorename("Default store");
		store.setStorephone("888-888-8888");
		store.setCode(MerchantStore.DEFAULT_STORE);
		store.setStorecity("My city");
		store.setStoreaddress("1234 Street address");
		store.setStorepostalcode("H2H-2H2");
		store.setStoreEmailAddress("john@test.com");
		store.setDomainName("localhost:8080");
		store.setStoreTemplate("december");
		store.setRetailer(true);
		store.setLanguages(supportedLanguages);
		
		merchantService.create(store);
		
		
		TaxClass taxclass = new TaxClass(TaxClass.DEFAULT_TAX_CLASS);
		taxclass.setMerchantStore(store);
		
		taxClassService.create(taxclass);
		
		//create default manufacturer
		Manufacturer defaultManufacturer = new Manufacturer();
		defaultManufacturer.setCode("DEFAULT");
		defaultManufacturer.setMerchantStore(store);
		
		ManufacturerDescription manufacturerDescription = new ManufacturerDescription();
		manufacturerDescription.setLanguage(en);
		manufacturerDescription.setName("DEFAULT");
		manufacturerDescription.setManufacturer(defaultManufacturer);
		manufacturerDescription.setDescription("DEFAULT");
		defaultManufacturer.getDescriptions().add(manufacturerDescription);
		
		manufacturerService.create(defaultManufacturer);
		
	   Optin newsletter = new Optin();
	   newsletter.setCode(OptinType.NEWSLETTER.name());
	   newsletter.setMerchant(store);
	   newsletter.setOptinType(OptinType.NEWSLETTER);
	   optinService.create(newsletter);
		
		
	}

	private void createModules() throws ServiceException {
		
		try {
			
			List<IntegrationModule> modules = modulesLoader.loadIntegrationModules("reference/integrationmodules.json");
            for (IntegrationModule entry : modules) {
        	    moduleConfigurationService.create(entry);
          }
			
			
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
		
	}
	
	private void createSubReferences() throws ServiceException {
		
		LOGGER.info(String.format("%s : Loading catalog sub references ", name));
		
		
		ProductType productType = new ProductType();
		productType.setCode(ProductType.GENERAL_TYPE);
		productTypeService.create(productType);


		
		
	}
	

	



}
