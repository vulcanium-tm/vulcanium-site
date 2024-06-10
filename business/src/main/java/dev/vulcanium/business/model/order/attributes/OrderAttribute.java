package dev.vulcanium.business.model.order.attributes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;

import dev.vulcanium.business.constants.SchemaConstant;
import dev.vulcanium.business.model.generic.SalesManagerEntity;
import dev.vulcanium.business.model.order.Order;

/**
 * Entity used for storing various attributes related to an Order
 */
@Entity
@Table (name="ORDER_ATTRIBUTE" )
public class OrderAttribute extends SalesManagerEntity<Long, OrderAttribute> {

private static final long serialVersionUID = 1L;

@Id
@Column(name = "ORDER_ATTRIBUTE_ID", unique=true, nullable=false)
@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "ORDER_ATTR_ID_NEXT_VALUE")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;

@Column (name ="IDENTIFIER", nullable=false)
private String key;

@Column (name ="VALUE", nullable=false)
private String value;

@ManyToOne(targetEntity = Order.class)
@JoinColumn(name = "ORDER_ID", nullable=false)
private Order order;

public Order getOrder() {
	return order;
}

public void setOrder(Order order) {
	this.order = order;
}

@Override
public Long getId() {
	return id;
}

@Override
public void setId(Long id) {
	this.id = id;
}

public String getValue() {
	return value;
}

public void setValue(String value) {
	this.value = value;
}

public String getKey() {
	return key;
}

public void setKey(String key) {
	this.key = key;
}

}
