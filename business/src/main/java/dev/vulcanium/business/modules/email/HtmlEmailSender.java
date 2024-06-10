package dev.vulcanium.business.modules.email;


public interface HtmlEmailSender {
	
	void send(final Email email) throws Exception;

	void setEmailConfig(EmailConfig emailConfig);

}
