package dev.vulcanium.site.tech.model.content;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * System configuration settings for content management
 */
@Setter
@Getter
public class ContentSettings implements Serializable {

private static final long serialVersionUID = 1L;

private String httpBasePath;

}
