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

import dev.vulcanium.business.model.generic.SalesManagerEntity;
import dev.vulcanium.business.model.merchant.MerchantStore;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="CUSTOMER_OPTION", indexes = { @Index(name="CUST_OPT_CODE_IDX", columnList = "CUSTOMER_OPT_CODE")}, uniqueConstraints=
@UniqueConstraint(columnNames = {"MERCHANT_ID", "CUSTOMER_OPT_CODE"}))
public class CustomerOption extends SalesManagerEntity<Long, CustomerOption> {
private static final long serialVersionUID = -2019269055342226086L;

@Id
@Column(name="CUSTOMER_OPTION_ID")
@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "CUSTOMER_OPTION_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;

@Setter
@Getter
@Column(name="SORT_ORDER")
private Integer sortOrder = 0;

@Setter
@Getter
@Column(name="CUSTOMER_OPTION_TYPE", length=10)
private String customerOptionType;

@Setter
@Getter
@NotEmpty
@Pattern(regexp="^[a-zA-Z0-9_]*$")
@Column(name="CUSTOMER_OPT_CODE")
private String code;

@Setter
@Getter
@Column(name="CUSTOMER_OPT_ACTIVE")
private boolean active;

@Setter
@Getter
@Column(name="CUSTOMER_OPT_PUBLIC")
private boolean publicOption;

@Setter
@Getter
@Valid
@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "customerOption")
private Set<CustomerOptionDescription> descriptions = new HashSet<>();

@Setter
@Getter
@Transient
private List<CustomerOptionDescription> descriptionsList = new ArrayList<>();


@Setter
@Getter
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="MERCHANT_ID", nullable=false)
private MerchantStore merchantStore;

public CustomerOption() {
}

@Override
public Long getId() {
	return id;
}

@Override
public void setId(Long id) {
	this.id = id;
}

public List<CustomerOptionDescription> getDescriptionsSettoList() {
	if(descriptionsList==null || descriptionsList.isEmpty()) {
		descriptionsList = new ArrayList<>(this.getDescriptions());
	}
	return descriptionsList;
	
}


}
