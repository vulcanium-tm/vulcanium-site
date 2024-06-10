package dev.vulcanium.business.model.catalog.marketplace;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Embedded;

import dev.vulcanium.business.model.common.audit.AuditSection;
import dev.vulcanium.business.model.common.audit.Auditable;
import dev.vulcanium.business.model.generic.SalesManagerEntity;
import dev.vulcanium.business.model.merchant.MerchantStore;
import lombok.Getter;
import lombok.Setter;

/**
 * A catalog is used to classify products of a given merchant
 * to be displayed in a specific marketplace
 */
public class Catalog extends SalesManagerEntity<Long, Catalog> implements Auditable {

private static final long serialVersionUID = 1L;

private Long id;

@Setter
@Getter
private MerchantStore store;

@Setter
@Getter
private String code;

@Setter
@Getter
private List<CatalogDescription> descriptions = new ArrayList<>();

@Embedded
private AuditSection auditSection = new AuditSection();

@Override
public AuditSection getAuditSection() {
	return auditSection;
}

@Override
public void setAuditSection(AuditSection audit) {
	this.auditSection = auditSection;
}


@Override
public Long getId() {
	return id;
}

@Override
public void setId(Long id) {
	this.id = id;
}

}
