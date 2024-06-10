package dev.vulcanium.business.model.catalog.product.attribute;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.vulcanium.business.constants.SchemaConstant;
import dev.vulcanium.business.model.common.description.Description;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity

@Table(name = "PRODUCT_OPTION_DESC",
		uniqueConstraints = {@UniqueConstraint(columnNames = { "PRODUCT_OPTION_ID", "LANGUAGE_ID" })}

)

@TableGenerator(name = "description_gen", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "product_option_description_seq", allocationSize = SchemaConstant.DESCRIPTION_ID_ALLOCATION_SIZE, initialValue = SchemaConstant.DESCRIPTION_ID_START_VALUE)
public class ProductOptionDescription extends Description {
private static final long serialVersionUID = 1L;

@JsonIgnore
@ManyToOne(targetEntity = ProductOption.class)
@JoinColumn(name = "PRODUCT_OPTION_ID", nullable = false)
private ProductOption productOption;

@Column(name="PRODUCT_OPTION_COMMENT", length=4000)
private String productOptionComment;

public ProductOptionDescription() {
}

}
