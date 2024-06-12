package dev.vulcanium.business.model.customer.attribute;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerAttributeEntity extends CustomerAttribute implements
		Serializable {


private static final long serialVersionUID = 1L;
private String textValue;


}
