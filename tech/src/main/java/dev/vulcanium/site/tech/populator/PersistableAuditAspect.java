package dev.vulcanium.site.tech.populator;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import dev.vulcanium.business.model.common.audit.AuditSection;
import dev.vulcanium.business.model.common.audit.Auditable;

/**
 * Create audit section
 */
@Aspect
@Configuration
public class PersistableAuditAspect {


private static final Logger LOGGER = LoggerFactory.getLogger(PersistableAuditAspect.class);

@AfterReturning(value = "execution(* populate(..))",
		returning = "result")
public void afterReturning(JoinPoint joinPoint, Object result) {
	
	try {
		if(result instanceof Auditable) {
			Auditable entity = (Auditable)result;
			AuditSection audit = entity.getAuditSection();
			if(entity.getAuditSection()==null) {
				audit = new AuditSection();
			}
			audit.setDateModified(new Date());
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if(auth!=null) {
				if(auth instanceof UsernamePasswordAuthenticationToken) {//api only is captured
					dev.vulcanium.business.store.security.user.JWTUser user = (dev.vulcanium.business.store.security.user.JWTUser)auth.getPrincipal();
					audit.setModifiedBy(user.getUsername());
				}
			}
			entity.setAuditSection(audit);
		}
	} catch (Throwable e) {
		LOGGER.error("Error while setting audit values" + e.getMessage());
	}
	
}


}