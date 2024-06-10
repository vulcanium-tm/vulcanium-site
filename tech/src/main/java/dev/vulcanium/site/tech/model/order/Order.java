package dev.vulcanium.site.tech.model.order;

import java.util.ArrayList;
import java.util.List;

import dev.vulcanium.site.tech.model.entity.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Order extends Entity {

private static final long serialVersionUID = 1L;

private boolean customerAgreement;
private String comments;
private String currency;
private List<OrderAttribute> attributes = new ArrayList<>();


}
