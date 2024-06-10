package dev.vulcanium.business.model.catalog.product.variant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.vulcanium.business.constants.SchemaConstant;
import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.common.description.Description;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="PRODUCT_VAR_IMAGE_DESCRIPTION", uniqueConstraints={
		@UniqueConstraint(columnNames={
				"PRODUCT_VAR_IMAGE_ID",
				"LANGUAGE_ID"
		})
}
)
@TableGenerator(name = "description_gen", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "product_var_image_desc_seq", allocationSize = SchemaConstant.DESCRIPTION_ID_ALLOCATION_SIZE, initialValue = SchemaConstant.DESCRIPTION_ID_START_VALUE)
public class ProductVariantImageDescription extends Description {
private static final long serialVersionUID = 1L;

@Setter
@Getter
@ManyToOne(targetEntity = ProductVariantImage.class)
@JoinColumn(name = "PRODUCT_VAR_IMAGE_ID", nullable = false)
private ProductVariantImage productVariantImage;

@JsonIgnore
@ManyToOne(targetEntity = Product.class)
@JoinColumn(name = "PRODUCT_ID", nullable = false)
private Product product;


@Setter
@Getter
@Column(name="ALT_TAG", length=100)
private String altTag;


}
