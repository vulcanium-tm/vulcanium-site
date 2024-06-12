package dev.vulcanium.site.tech.model.customer.attribute;

import dev.vulcanium.business.model.customer.attribute.CustomerAttributeEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableCustomerAttribute extends CustomerAttributeEntity{

private static final long serialVersionUID = 1L;
private ReadableCustomerOption customerOption;
private ReadableCustomerOptionValue customerOptionValue;


}
