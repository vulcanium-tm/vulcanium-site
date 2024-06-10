package dev.vulcanium.business.services.catalog.product.instance;

import jakarta.persistence.Column;
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

@Setter
@Getter
@Entity
@Table(name="PRODUCT_INST_IMAGE_DESCRIPTION", uniqueConstraints={
		@UniqueConstraint(columnNames={
				"PRODUCT_INST_IMAGE_ID",
				"LANGUAGE_ID"
		})
}
)
@TableGenerator(name = "description_gen", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "product_image_description_seq", allocationSize = SchemaConstant.DESCRIPTION_ID_ALLOCATION_SIZE, initialValue = SchemaConstant.DESCRIPTION_ID_START_VALUE)
public class ProductInstanceImageDescription extends Description {
private static final long serialVersionUID = 1L;

@ManyToOne(targetEntity = ProductInstanceImage.class)
@JoinColumn(name = "PRODUCT_INST_IMAGE_ID", nullable = false)
private ProductInstanceImage productInstanceImage;

@Column(name="ALT_TAG", length=100)
private String altTag;


}