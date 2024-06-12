package dev.vulcanium.business.model.customer.attribute;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.generic.SalesManagerEntity;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="CUSTOMER_ATTRIBUTE",
		uniqueConstraints={
				@UniqueConstraint(columnNames={
						"OPTION_ID",
						"CUSTOMER_ID"
				})
		}
)
public class CustomerAttribute extends SalesManagerEntity<Long, CustomerAttribute> implements Serializable{
private static final long serialVersionUID = 1L;

@Id
@Column(name = "CUSTOMER_ATTRIBUTE_ID", unique=true, nullable=false)
@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "CUST_ATTR_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;


@Setter
@Getter
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="OPTION_ID", nullable=false)
private CustomerOption customerOption;


@Setter
@Getter
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="OPTION_VALUE_ID", nullable=false)
private CustomerOptionValue customerOptionValue;

@Setter
@Getter
@Column(name="CUSTOMER_ATTR_TXT_VAL")
private String textValue;

@Setter
@Getter
@JsonIgnore
@ManyToOne(targetEntity = Customer.class)
@JoinColumn(name = "CUSTOMER_ID", nullable = false)
private Customer customer;

public CustomerAttribute() {
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
