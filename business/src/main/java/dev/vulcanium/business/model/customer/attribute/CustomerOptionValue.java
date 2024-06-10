package dev.vulcanium.business.model.customer.attribute;

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
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.vulcanium.business.model.generic.SalesManagerEntity;
import dev.vulcanium.business.model.merchant.MerchantStore;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="CUSTOMER_OPTION_VALUE", indexes = { @Index(name="CUST_OPT_VAL_CODE_IDX",columnList = "CUSTOMER_OPT_VAL_CODE")}, uniqueConstraints=
@UniqueConstraint(columnNames = {"MERCHANT_ID", "CUSTOMER_OPT_VAL_CODE"}))
public class CustomerOptionValue extends SalesManagerEntity<Long, CustomerOptionValue> {
private static final long serialVersionUID = 3736085877929910891L;

@Id
@Column(name="CUSTOMER_OPTION_VALUE_ID")
@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "CUSTOMER_OPT_VAL_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;

@Setter
@Getter
@Column(name="SORT_ORDER")
private Integer sortOrder = 0;

@Setter
@Getter
@Column(name="CUSTOMER_OPT_VAL_IMAGE")
private String customerOptionValueImage;

@Setter
@Getter
@NotEmpty
@Pattern(regexp="^[a-zA-Z0-9_]*$")
@Column(name="CUSTOMER_OPT_VAL_CODE")
private String code;


@Setter
@Getter
@Valid
@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "customerOptionValue")
private Set<CustomerOptionValueDescription> descriptions = new HashSet<>();

@Setter
@Getter
@Transient
private List<CustomerOptionValueDescription> descriptionsList = new ArrayList<>();

@Setter
@Getter
@JsonIgnore
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="MERCHANT_ID", nullable=false)
private MerchantStore merchantStore;

public CustomerOptionValue() {
}

@Override
public Long getId() {
	return id;
}

@Override
public void setId(Long id) {
	this.id = id;
}

public List<CustomerOptionValueDescription> getDescriptionsSettoList() {
	if(descriptionsList==null || descriptionsList.isEmpty()) {
		descriptionsList = new ArrayList<>(this.getDescriptions());
	}
	return descriptionsList;
}


}
