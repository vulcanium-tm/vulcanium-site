package dev.vulcanium.business.model.customer.attribute;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.UniqueConstraint;

import dev.vulcanium.business.model.generic.SalesManagerEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="CUSTOMER_OPTION_SET",
		uniqueConstraints={
				@UniqueConstraint(columnNames={
						"CUSTOMER_OPTION_ID",
						"CUSTOMER_OPTION_VALUE_ID"
				})
		}
)
public class CustomerOptionSet extends SalesManagerEntity<Long, CustomerOptionSet> {

private static final long serialVersionUID = 1L;

@Id
@Column(name = "CUSTOMER_OPTIONSET_ID", unique=true, nullable=false)
@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "CUST_OPTSET_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;


@Getter
@Setter
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="CUSTOMER_OPTION_ID", nullable=false)
private CustomerOption customerOption = null;

@Getter
@Setter
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="CUSTOMER_OPTION_VALUE_ID", nullable=false)
private CustomerOptionValue customerOptionValue = null;



@Column(name="SORT_ORDER")
private Integer sortOrder = new Integer(0);



public int getSortOrder() {
	return sortOrder;
}

public void setSortOrder(int sortOrder) {
	this.sortOrder = sortOrder;
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
