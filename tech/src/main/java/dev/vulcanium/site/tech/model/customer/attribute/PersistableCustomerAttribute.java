package dev.vulcanium.site.tech.model.customer.attribute;

import dev.vulcanium.business.model.customer.attribute.CustomerAttributeEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersistableCustomerAttribute extends CustomerAttributeEntity{

private static final long serialVersionUID = 1L;
private CustomerOption customerOption;
private CustomerOptionValue customerOptionValue;


}
