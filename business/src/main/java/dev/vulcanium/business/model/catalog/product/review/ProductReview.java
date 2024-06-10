package dev.vulcanium.business.model.catalog.product.review;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.vulcanium.business.model.catalog.product.Product;
import dev.vulcanium.business.model.common.audit.AuditListener;
import dev.vulcanium.business.model.common.audit.AuditSection;
import dev.vulcanium.business.model.common.audit.Auditable;
import dev.vulcanium.business.model.customer.Customer;
import dev.vulcanium.business.model.generic.SalesManagerEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "PRODUCT_REVIEW", uniqueConstraints={
		@UniqueConstraint(columnNames={
				"CUSTOMERS_ID",
				"PRODUCT_ID"
		})
}
)
public class ProductReview extends SalesManagerEntity<Long, ProductReview> implements Auditable {
private static final long serialVersionUID = 1L;

@Setter
@Getter
@Id
@Column(name = "PRODUCT_REVIEW_ID", unique=true, nullable=false)
@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT",
		pkColumnValue = "PRODUCT_REVIEW_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;

@Embedded
private AuditSection audit = new AuditSection();

@Getter
@Setter
@Column(name = "REVIEWS_RATING")
private Double reviewRating;

@Getter
@Setter
@Column(name = "REVIEWS_READ")
private Long reviewRead;

@Getter
@Setter
@Temporal(TemporalType.TIMESTAMP)
@Column(name = "REVIEW_DATE")
private Date reviewDate;

@Getter
@Setter
@Column(name = "STATUS")
private Integer status;

@Getter
@Setter
@JsonIgnore
@ManyToOne
@JoinColumn(name="CUSTOMERS_ID")
private Customer customer;

@Getter
@Setter
@OneToOne
@JoinColumn(name="PRODUCT_ID")
private Product product;

@Getter
@Setter
@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productReview")
private Set<ProductReviewDescription> descriptions = new HashSet<>();

public ProductReview() {
}

@Override
public AuditSection getAuditSection() {
	return audit;
}

@Override
public void setAuditSection(AuditSection audit) {
	this.audit = audit;
}

}
