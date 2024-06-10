package dev.vulcanium.site.tech.model.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dev.vulcanium.site.tech.model.order.total.ReadableOrderTotal;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadableOrderTotalSummary implements Serializable {

private static final long serialVersionUID = 1L;
private String subTotal;
private String total;
private String taxTotal;

private List<ReadableOrderTotal> totals = new ArrayList<>();//all other fees (tax, shipping ....)

}
