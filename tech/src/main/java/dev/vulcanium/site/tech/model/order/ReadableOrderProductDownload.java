package dev.vulcanium.site.tech.model.order;

import java.io.Serializable;

import dev.vulcanium.site.tech.model.entity.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableOrderProductDownload extends Entity implements Serializable {

/**
 *
 */
private static final long serialVersionUID = 1L;

private long orderId;

private String productName;
private String downloadUrl;

private String fileName;

private int downloadExpiryDays = 0;
private int downloadCount = 0;


}
