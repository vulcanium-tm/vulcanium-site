package dev.vulcanium.site.tech.model.catalog;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * Search product request
 */
@Setter
@Getter
public class SearchProductRequest implements Serializable {

private static final long serialVersionUID = 1L;
private static final int DEFAULT_COUNT = 100;
private static final int START_COUNT = 0;
@NotEmpty
private String query;
private int count = DEFAULT_COUNT;
private int start = START_COUNT;

}
