package dev.vulcanium.site.tech.store.security.services;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;

public interface CredentialsService {

/**
 * Password validation with specific rules
 * @param password
 * @param repeatPassword
 * @param store
 * @param language
 * @throws CredentialsException
 */
void validateCredentials(String password, String repeatPassword, MerchantStore store, Language language) throws CredentialsException;

/**
 * Generates password based on specific rules
 * @param store
 * @param language
 * @return
 * @throws CredentialsException
 */
String generatePassword(MerchantStore store, Language language) throws CredentialsException;

}
