package dev.vulcanium.business.model.content;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.Valid;

import jakarta.validation.constraints.NotEmpty;

import dev.vulcanium.business.model.common.audit.AuditListener;
import dev.vulcanium.business.model.common.audit.AuditSection;
import dev.vulcanium.business.model.generic.SalesManagerEntity;
import dev.vulcanium.business.model.merchant.MerchantStore;
import lombok.Getter;
import lombok.Setter;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "CONTENT",
		indexes = { @Index(name="CODE_IDX", columnList = "CODE")},
		uniqueConstraints = @UniqueConstraint(columnNames = {"MERCHANT_ID", "CODE"}) )
public class Content extends SalesManagerEntity<Long, Content> implements Serializable {



private static final long serialVersionUID = 1772757159185494620L;

@Id
@Column(name = "CONTENT_ID", unique=true, nullable=false)
@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "CONTENT_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;

@Setter
@Getter
@Embedded
private AuditSection auditSection = new AuditSection();

@Setter
@Getter
@Valid
@OneToMany(mappedBy="content", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
private List<ContentDescription> descriptions = new ArrayList<>();

@Setter
@Getter
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="MERCHANT_ID", nullable=false)
private MerchantStore merchantStore;

@Setter
@Getter
@NotEmpty
@Column(name="CODE", length=100, nullable=false)
private String code;

@Setter
@Getter
@Column(name = "VISIBLE")
private boolean visible;

@Setter
@Getter
@Column(name = "LINK_TO_MENU")
private boolean linkToMenu;

@Setter
@Getter
@Column(name = "CONTENT_POSITION", length=10)
@Enumerated(value = EnumType.STRING)
private ContentPosition contentPosition;

@Setter
@Getter
@Column(name = "CONTENT_TYPE", length=10)
@Enumerated(value = EnumType.STRING)
private ContentType contentType;

@Setter
@Getter
@Column(name = "SORT_ORDER")
private Integer sortOrder = 0;

@Setter
@Getter
@Column(name = "PRODUCT_GROUP")
private String productGroup;

@Override
public Long getId() {
	return this.id;
}

@Override
public void setId(Long id) {
	this.id = id;
	
}

public ContentDescription getDescription() {
	
	if(this.getDescriptions()!=null && !this.getDescriptions().isEmpty()) {
		return this.getDescriptions().getFirst();
	}
	
	return null;
	
}

}