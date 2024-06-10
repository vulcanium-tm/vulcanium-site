package dev.vulcanium.business.model.catalog.product.type;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.UniqueConstraint;

import dev.vulcanium.business.constants.SchemaConstant;
import dev.vulcanium.business.model.common.description.Description;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PRODUCT_TYPE_DESCRIPTION",
		uniqueConstraints = {@UniqueConstraint(columnNames = {"PRODUCT_TYPE_ID", "LANGUAGE_ID"})})

@TableGenerator(name = "description_gen", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "product_type_description_seq", allocationSize = SchemaConstant.DESCRIPTION_ID_ALLOCATION_SIZE, initialValue = SchemaConstant.DESCRIPTION_ID_START_VALUE)
public class ProductTypeDescription extends Description {

private static final long serialVersionUID = 1L;

@ManyToOne(targetEntity = ProductType.class)
@JoinColumn(name = "PRODUCT_TYPE_ID", nullable = false)
private ProductType productType;

}