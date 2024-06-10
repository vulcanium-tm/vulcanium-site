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

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import dev.vulcanium.business.model.generic.SalesManagerEntity;
import dev.vulcanium.business.model.merchant.MerchantStore;


@Entity
@Table(name="PRODUCT_OPTION_VALUE",
		indexes = { @Index(name="PRD_OPTION_VAL_CODE_IDX", columnList = "PRODUCT_OPTION_VAL_CODE")},
		uniqueConstraints=
		@UniqueConstraint(columnNames = {"MERCHANT_ID", "PRODUCT_OPTION_VAL_CODE"}))
public class ProductOptionValue extends SalesManagerEntity<Long, ProductOptionValue> {
private static final long serialVersionUID = 1L;

@Id
@Column(name="PRODUCT_OPTION_VALUE_ID")
@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "PRODUCT_OPT_VAL_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;

@Setter
@Getter
@Column(name="PRODUCT_OPT_VAL_SORT_ORD")
private Integer productOptionValueSortOrder;

@Setter
@Getter
@Column(name="PRODUCT_OPT_VAL_IMAGE")
private String productOptionValueImage;

@Setter
@Getter
@Column(name="PRODUCT_OPT_FOR_DISP")
private boolean productOptionDisplayOnly=false;

@Setter
@Getter
@NotEmpty
@Pattern(regexp="^[a-zA-Z0-9_]*$")
@Column(name="PRODUCT_OPTION_VAL_CODE")
private String code;

@Setter
@Getter
@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productOptionValue")
private Set<ProductOptionValueDescription> descriptions = new HashSet<>();

@Setter
@Getter
@Transient
private MultipartFile image = null;

@Setter
@Getter
@Transient
private List<ProductOptionValueDescription> descriptionsList = new ArrayList<>();

@Setter
@Getter
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="MERCHANT_ID", nullable=false)
private MerchantStore merchantStore;

public ProductOptionValue() {
}

@Override
public Long getId() {
	return id;
}

@Override
public void setId(Long id) {
	this.id = id;
}

public List<ProductOptionValueDescription> getDescriptionsSettoList() {
	if(descriptionsList==null || descriptionsList.isEmpty()) {
		descriptionsList = new ArrayList<>(this.getDescriptions());
	}
	return descriptionsList;
}


}
