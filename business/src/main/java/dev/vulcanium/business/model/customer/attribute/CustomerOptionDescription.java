package dev.vulcanium.business.model.customer.attribute;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

import dev.vulcanium.business.constants.SchemaConstant;
import dev.vulcanium.business.model.common.description.Description;

@Getter
@Setter
@Entity
@Table(name="CUSTOMER_OPTION_DESC", uniqueConstraints={
		@UniqueConstraint(columnNames={
				"CUSTOMER_OPTION_ID",
				"LANGUAGE_ID"
		})
}
)

@TableGenerator(name = "description_gen", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "customer_option_description_seq", allocationSize = SchemaConstant.DESCRIPTION_ID_ALLOCATION_SIZE, initialValue = SchemaConstant.DESCRIPTION_ID_START_VALUE)
public class CustomerOptionDescription extends Description {
private static final long serialVersionUID = 1L;

@ManyToOne(targetEntity = CustomerOption.class)
@JoinColumn(name = "CUSTOMER_OPTION_ID", nullable = false)
private CustomerOption customerOption;

@Column(name = "CUSTOMER_OPTION_COMMENT", length=4000)
private String customerOptionComment;

public CustomerOptionDescription() {
}


}
