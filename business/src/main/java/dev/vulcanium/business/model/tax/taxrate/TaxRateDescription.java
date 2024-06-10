package dev.vulcanium.business.model.tax.taxrate;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.UniqueConstraint;

import dev.vulcanium.business.constants.SchemaConstant;
import dev.vulcanium.business.model.common.description.Description;

@Entity
@Table(name = "TAX_RATE_DESCRIPTION"  ,uniqueConstraints={
		@UniqueConstraint(columnNames={
				"TAX_RATE_ID",
				"LANGUAGE_ID"
		})
}
)

@TableGenerator(name = "description_gen", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "taxrate_description_seq", allocationSize = SchemaConstant.DESCRIPTION_ID_ALLOCATION_SIZE, initialValue = SchemaConstant.DESCRIPTION_ID_START_VALUE)
public class TaxRateDescription extends Description {
private static final long serialVersionUID = 1L;

@ManyToOne(targetEntity = TaxRate.class)
@JoinColumn(name = "TAX_RATE_ID")
private TaxRate taxRate;

public TaxRateDescription() {
}

public TaxRate getTaxRate() {
	return taxRate;
}

public void setTaxRate(TaxRate taxRate) {
	this.taxRate = taxRate;
}
}