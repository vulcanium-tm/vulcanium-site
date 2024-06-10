package dev.vulcanium.business.model.catalog.product.attribute;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import dev.vulcanium.business.model.generic.SalesManagerEntity;
import dev.vulcanium.business.model.merchant.MerchantStore;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="PRODUCT_OPTION",
		
		indexes = { @Index(name="PRD_OPTION_CODE_IDX", columnList = "PRODUCT_OPTION_CODE")},
		uniqueConstraints=@UniqueConstraint(columnNames = {"MERCHANT_ID", "PRODUCT_OPTION_CODE"}))

public class ProductOption extends SalesManagerEntity<Long, ProductOption> {
private static final long serialVersionUID = 1L;

@Id
@Column(name="PRODUCT_OPTION_ID")
@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "PRODUCT_OPTION_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;

@Setter
@Getter
@Column(name="PRODUCT_OPTION_SORT_ORD")
private Integer productOptionSortOrder;

@Setter
@Getter
@Column(name="PRODUCT_OPTION_TYPE", length=10)
private String productOptionType;


@Setter
@Getter
@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productOption")
private Set<ProductOptionDescription> descriptions = new HashSet<>();

@Setter
@Getter
@Transient
private List<ProductOptionDescription> descriptionsList = new ArrayList<>();

@Setter
@Getter
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="MERCHANT_ID", nullable=false)
private MerchantStore merchantStore;

@Setter
@Getter
@Column(name="PRODUCT_OPTION_READ")
private boolean readOnly;

@Setter
@Getter
@NotEmpty
@Pattern(regexp="^[a-zA-Z0-9_]*$")
@Column(name="PRODUCT_OPTION_CODE")
private String code;

public ProductOption() {
}

@Override
public Long getId() {
	return id;
}

@Override
public void setId(Long id) {
	this.id = id;
}

public List<ProductOptionDescription> getDescriptionsSettoList() {
	if(descriptionsList==null || descriptionsList.isEmpty()) {
		descriptionsList = new ArrayList<>(this.getDescriptions());
	}
	return descriptionsList;
	
}

}
