package dev.vulcanium.business.model.catalog.product.file;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.UniqueConstraint;

import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.generic.SalesManagerEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * Representation of a digital product
 */
@Entity
@Table(name = "PRODUCT_DIGITAL", uniqueConstraints=
@UniqueConstraint(columnNames = {"PRODUCT_ID", "FILE_NAME"}))
public class DigitalProduct extends SalesManagerEntity<Long, DigitalProduct> {


private static final long serialVersionUID = 1L;


@Id
@Column(name = "PRODUCT_DIGITAL_ID")
@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "PRODUCT_DGT_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;


@Setter
@Getter
@ManyToOne(targetEntity = Product.class)
@JoinColumn(name = "PRODUCT_ID", nullable = false)
private Product product;


@Setter
@Getter
@Column(name="FILE_NAME",nullable=false)
private String productFileName;


@Override
public Long getId() {
	return id;
}

@Override
public void setId(Long id) {
	this.id = id;
}


}
