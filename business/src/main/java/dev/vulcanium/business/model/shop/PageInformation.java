package dev.vulcanium.business.model.shop;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PageInformation implements Serializable {

private static final long serialVersionUID = 1L;
private String pageTitle;
private String pageDescription;
private String pageKeywords;
private String pageUrl;
private String pageImageUrl;

}
