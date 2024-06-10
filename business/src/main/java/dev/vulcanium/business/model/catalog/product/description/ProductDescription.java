package dev.vulcanium.business.model.catalog.product.description;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
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

@Getter
@Setter
@Entity
@Table(name = "PRODUCT_DESCRIPTION",
		uniqueConstraints = {@UniqueConstraint(columnNames = { "PRODUCT_ID", "LANGUAGE_ID" })},
		indexes = {@Index(name = "PRODUCT_DESCRIPTION_SEF_URL", columnList = "SEF_URL")})

@TableGenerator(name = "description_gen", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "product_description_seq", allocationSize = SchemaConstant.DESCRIPTION_ID_ALLOCATION_SIZE, initialValue = SchemaConstant.DESCRIPTION_ID_START_VALUE)
public class ProductDescription extends Description {
private static final long serialVersionUID = 1L;

@JsonIgnore
@ManyToOne(targetEntity = Product.class)
@JoinColumn(name = "PRODUCT_ID", nullable = false)
private Product product;

@Column(name = "PRODUCT_HIGHLIGHT")
private String productHighlight;

@Column(name = "DOWNLOAD_LNK")
private String productExternalDl;

@Column(name = "SEF_URL")
private String seUrl;

@Column(name = "META_TITLE")
private String metatagTitle;

@Column(name = "META_KEYWORDS")
private String metatagKeywords;

@Column(name = "META_DESCRIPTION")
private String metatagDescription;

public ProductDescription() {
}

}
