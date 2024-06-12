package dev.vulcanium.site.tech.model.catalog.product;

import dev.vulcanium.business.model.entity.Entity;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableImage extends Entity implements Serializable {

private static final long serialVersionUID = 1L;
private String imageName;
private String imageUrl;
private String externalUrl;
private String videoUrl;
private int imageType;
private int order;
private boolean defaultImage;

}
