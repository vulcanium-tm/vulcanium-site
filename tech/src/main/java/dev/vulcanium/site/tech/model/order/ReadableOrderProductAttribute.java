package dev.vulcanium.site.tech.model.order;

import dev.vulcanium.business.model.entity.Entity;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadableOrderProductAttribute extends Entity implements Serializable {


/**
 *
 */
private static final long serialVersionUID = 1L;
private String attributeName;
private String attributePrice;
private String attributeValue;

}
