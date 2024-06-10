package dev.vulcanium.site.tech.model.customer.attribute;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerOptionEntity extends CustomerOption implements
		Serializable {

private static final long serialVersionUID = 1L;
private int order;
private String code;
private String type;

}
