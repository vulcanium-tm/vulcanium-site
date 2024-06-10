package dev.vulcanium.site.tech.model.content;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * Used for defining an image name and its path
 */
@Setter
@Getter
public class ReadableImage implements Serializable {

private static final long serialVersionUID = 1L;

private String name;
private String path;

}
