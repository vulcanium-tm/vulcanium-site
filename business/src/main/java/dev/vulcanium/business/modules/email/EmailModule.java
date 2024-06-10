package dev.vulcanium.business.modules.email;

public interface EmailModule {
  
  void send(final Email email) throws Exception;

  void setEmailConfig(EmailConfig emailConfig);

}
