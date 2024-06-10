package dev.vulcanium.business.model.catalog.product.price;

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
@Table(name="PRODUCT_PRICE_DESCRIPTION",
		uniqueConstraints={
				@UniqueConstraint(columnNames={
						"PRODUCT_PRICE_ID",
						"LANGUAGE_ID"
				})
		}
)

@TableGenerator(name = "description_gen", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "product_price_description_seq", allocationSize = SchemaConstant.DESCRIPTION_ID_ALLOCATION_SIZE, initialValue = SchemaConstant.DESCRIPTION_ID_START_VALUE)
public class ProductPriceDescription extends Description {

private static final long serialVersionUID = 1L;

public final static String DEFAULT_PRICE_DESCRIPTION = "DEFAULT";

@JsonIgnore
@ManyToOne(targetEntity = ProductPrice.class)
@JoinColumn(name = "PRODUCT_PRICE_ID", nullable = false)
private ProductPrice productPrice;


@Column(name = "PRICE_APPENDER")
private String priceAppender;

public ProductPriceDescription() {
}


}
