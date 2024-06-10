package dev.vulcanium.business.model.customer.review;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.UniqueConstraint;

import dev.vulcanium.business.constants.SchemaConstant;
import dev.vulcanium.business.model.common.description.Description;
import dev.vulcanium.business.model.reference.language.Language;

@Entity
@Table(name = "CUSTOMER_REVIEW_DESCRIPTION", uniqueConstraints={
		@UniqueConstraint(columnNames={
				"CUSTOMER_REVIEW_ID",
				"LANGUAGE_ID"
		})
})

@TableGenerator(name = "description_gen", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "custome_review_description_seq", allocationSize = SchemaConstant.DESCRIPTION_ID_ALLOCATION_SIZE, initialValue = SchemaConstant.DESCRIPTION_ID_START_VALUE)
public class CustomerReviewDescription extends Description {
private static final long serialVersionUID = 1L;

@ManyToOne(targetEntity = CustomerReview.class)
@JoinColumn(name="CUSTOMER_REVIEW_ID")
private CustomerReview customerReview;

public CustomerReview getCustomerReview() {
	return customerReview;
}

public void setCustomerReview(CustomerReview customerReview) {
	this.customerReview = customerReview;
}

public CustomerReviewDescription() {
}

public CustomerReviewDescription(Language language, String name) {
	this.setLanguage(language);
	this.setName(name);
}


}
