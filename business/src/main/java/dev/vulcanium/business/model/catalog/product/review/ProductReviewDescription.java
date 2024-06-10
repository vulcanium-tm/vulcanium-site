package dev.vulcanium.business.model.catalog.product.review;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.UniqueConstraint;

import dev.vulcanium.business.constants.SchemaConstant;
import dev.vulcanium.business.model.common.description.Description;
import dev.vulcanium.business.model.reference.language.Language;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "PRODUCT_REVIEW_DESCRIPTION", uniqueConstraints={
		@UniqueConstraint(columnNames={
				"PRODUCT_REVIEW_ID",
				"LANGUAGE_ID"
		})
})

@TableGenerator(name = "description_gen", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "product_review_description_seq", allocationSize = SchemaConstant.DESCRIPTION_ID_ALLOCATION_SIZE, initialValue = SchemaConstant.DESCRIPTION_ID_START_VALUE)
public class ProductReviewDescription extends Description {
private static final long serialVersionUID = 1L;

@ManyToOne(targetEntity = ProductReview.class)
@JoinColumn(name="PRODUCT_REVIEW_ID")
private ProductReview productReview;

public ProductReviewDescription() {
}

public ProductReviewDescription(Language language, String name) {
	this.setLanguage(language);
	this.setName(name);
}

}
