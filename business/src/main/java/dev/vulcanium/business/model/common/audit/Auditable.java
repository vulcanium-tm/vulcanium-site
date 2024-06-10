package dev.vulcanium.business.model.common.audit;

public interface Auditable {

AuditSection getAuditSection();

void setAuditSection(AuditSection audit);
}
