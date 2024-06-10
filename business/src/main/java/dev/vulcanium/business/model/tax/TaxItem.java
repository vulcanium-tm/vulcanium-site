package dev.vulcanium.business.model.tax;

import dev.vulcanium.business.model.order.OrderTotalItem;
import dev.vulcanium.business.model.tax.taxrate.TaxRate;

public class TaxItem extends OrderTotalItem {

private static final long serialVersionUID = 1L;
private String label;
private TaxRate taxRate=null;

public void setLabel(String label) {
	this.label = label;
}

public String getLabel() {
	return label;
}

public void setTaxRate(TaxRate taxRate) {
	this.taxRate = taxRate;
}

public TaxRate getTaxRate() {
	return taxRate;
}


}