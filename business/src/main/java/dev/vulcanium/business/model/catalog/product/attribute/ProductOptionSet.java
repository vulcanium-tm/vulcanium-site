package dev.vulcanium.business.model.catalog.product.attribute;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import dev.vulcanium.business.model.catalog.product.type.ProductType;
import dev.vulcanium.business.model.generic.SalesManagerEntity;
import dev.vulcanium.business.model.merchant.MerchantStore;

/**
 * Create a list of option and option value in order to accelerate and
 * prepare product attribute creation
 */
@Entity
@Table(name="PRODUCT_OPTION_SET",
		uniqueConstraints={
				@UniqueConstraint(columnNames={
						"MERCHANT_ID",
						"PRODUCT_OPTION_SET_CODE"
				})
		}
)
public class ProductOptionSet extends SalesManagerEntity<Long, ProductOptionSet> {

/**
 *
 */
private static final long serialVersionUID = 1L;

@Id
@Column(name="PRODUCT_OPTION_SET_ID")
@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "PRODUCT_OPT_SET_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;

@NotEmpty
@Pattern(regexp="^[a-zA-Z0-9_]*$")
@Column(name="PRODUCT_OPTION_SET_CODE")
private String code;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="PRODUCT_OPTION_ID", nullable=false)
private ProductOption option;

@ManyToMany(fetch = FetchType.LAZY, targetEntity=ProductOptionValue.class)
@JoinTable(name = "PRODUCT_OPT_SET_OPT_VALUE")
private List<ProductOptionValue> values = new ArrayList<ProductOptionValue>();

@ManyToMany(fetch = FetchType.LAZY, targetEntity=ProductType.class)
@JoinTable(name = "PRODUCT_OPT_SET_PRD_TYPE")
private Set<ProductType> productTypes = new HashSet<ProductType>();

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="MERCHANT_ID", nullable=false)
private MerchantStore store;

@Column(name="PRODUCT_OPTION_SET_DISP")
private boolean optionDisplayOnly = false;


public ProductOption getOption() {
	return option;
}
public void setOption(ProductOption option) {
	this.option = option;
}
public List<ProductOptionValue> getValues() {
	return values;
}
public void setValues(List<ProductOptionValue> values) {
	this.values = values;
}
public MerchantStore getStore() {
	return store;
}
public void setStore(MerchantStore store) {
	this.store = store;
}
@Override
public Long getId() {
	return this.id;
}
@Override
public void setId(Long id) {
	this.id = id;
}
public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
}
public boolean isOptionDisplayOnly() {
	return optionDisplayOnly;
}
public void setOptionDisplayOnly(boolean optionDisplayOnly) {
	this.optionDisplayOnly = optionDisplayOnly;
}

public Set<ProductType> getProductTypes() {
	return productTypes;
}
public void setProductTypes(Set<ProductType> productTypes) {
	this.productTypes = productTypes;
}

}