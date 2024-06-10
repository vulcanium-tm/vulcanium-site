package modules.commons.search.request;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 * Fields to be inserted
 */
@Setter
@Getter
public class IndexItem implements Serializable {

private static final long serialVersionUID = 1L;

private String store;
private String language;

private String name;
private String description;
private String image;
private boolean addToCart = true;
private String brand;
private String category;
private List<Map<String,String>> variants;
private Map<String, String> attributes;
private String price;
private Long id;

private String reviews;

public static long getSerialversionuid() {
	return serialVersionUID;
}
}