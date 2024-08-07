package dev.vulcanium.business.configuration;

import dev.vulcanium.business.modules.integration.payment.model.PaymentModule;
import dev.vulcanium.business.modules.integration.shipping.model.ShippingQuoteModule;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Contains injection of external shopizer starter modules
 * @author carlsamson
 * New Way - out of xml config and using spring boot starters
 *
 */
@Configuration
public class ModulesConfiguration {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ModulesConfiguration.class);
	
	
	/**
	 * Goes along with
	 * shipping-canadapost-spring-boot-starter
	 */
    @Autowired
    private ShippingQuoteModule canadapost;
    
    
    /**
     * All living modules exposed here
     */
    @Autowired
    private List<PaymentModule> liveModules;

    
    
    


}
