package dev.vulcanium.site.tech.model.order.total;

import java.io.Serializable;
import java.math.BigDecimal;

import dev.vulcanium.site.tech.model.entity.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderTotal extends Entity implements Serializable {

private static final long serialVersionUID = 1L;
private String title;
private String text;
private String code;
private int order;
private String module;
private BigDecimal value;


}