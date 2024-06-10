package dev.vulcanium.business.services.system;

import dev.vulcanium.business.model.system.MerchantLog;
import dev.vulcanium.business.repositories.system.MerchantLogRepository;
import dev.vulcanium.business.services.common.generic.SalesManagerEntityServiceImpl;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("merchantLogService")
public class MerchantLogServiceImpl extends
		SalesManagerEntityServiceImpl<Long, MerchantLog> implements
		MerchantLogService {
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantLogServiceImpl.class);


	
	private MerchantLogRepository merchantLogRepository;
	
	@Inject
	public MerchantLogServiceImpl(
			MerchantLogRepository merchantLogRepository) {
			super(merchantLogRepository);
			this.merchantLogRepository = merchantLogRepository;
	}


	




}
