package dev.vulcanium.site.tech.model.order;

import java.io.Serializable;

import dev.vulcanium.site.tech.model.entity.Entity;
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
