package dev.vulcanium.business.model.catalog.product;

import java.util.List;

import dev.vulcanium.business.model.catalog.product.attribute.AttributeCriteria;
import dev.vulcanium.business.model.common.Criteria;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductCriteria extends Criteria {

public static final String ORIGIN_SHOP = "shop";
public static final String ORIGIN_ADMIN = "admin";

private String productName;
private List<AttributeCriteria> attributeCriteria;
private String origin = ORIGIN_SHOP;


private Boolean available = null;

private List<Long> categoryIds;
private List<String> availabilities;
private List<Long> productIds;
private List<Long> optionValueIds;
private String sku;

//V2
private List<String> optionValueCodes;
private String option;

private String status;

private Long manufacturerId = null;

private Long ownerId = null;


}
