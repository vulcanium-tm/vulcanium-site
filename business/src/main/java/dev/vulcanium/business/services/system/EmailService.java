package dev.vulcanium.business.services.system;

import dev.vulcanium.business.exception.ServiceException;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.modules.email.Email;
import dev.vulcanium.business.modules.email.EmailConfig;


public interface EmailService {

	void sendHtmlEmail(MerchantStore store, Email email) throws ServiceException, Exception;
	
	EmailConfig getEmailConfiguration(MerchantStore store) throws ServiceException;
	
	void saveEmailConfiguration(EmailConfig emailConfig, MerchantStore store) throws ServiceException;
	
}
