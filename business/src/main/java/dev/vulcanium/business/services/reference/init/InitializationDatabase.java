package dev.vulcanium.business.services.reference.init;

import dev.vulcanium.business.exception.ServiceException;

public interface InitializationDatabase {
	
	boolean isEmpty();
	
	void populate(String name) throws ServiceException;

}
