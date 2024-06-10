package dev.vulcanium.site.tech.model.catalog.product.attribute;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableProductVariantValue implements Serializable {

private static final long serialVersionUID = 1L;
private String name;
private String code;
private int order;
private String description;
private Long option;
private Long value;

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((code == null) ? 0 : code.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((option == null) ? 0 : option.hashCode());
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	ReadableProductVariantValue other = (ReadableProductVariantValue) obj;
	if (code == null) {
		if (other.code != null)
			return false;
	} else if (!code.equals(other.code))
		return false;
	if (name == null) {
		if (other.name != null)
			return false;
	} else if (!name.equals(other.name))
		return false;
	if (option == null) {
		return other.option==null;
	} else
		return option.equals(other.option);
}

}
