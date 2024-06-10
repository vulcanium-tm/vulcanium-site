package dev.vulcanium.site.tech.model.tax;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersistableTaxRate extends TaxRateEntity {

private static final long serialVersionUID = 1L;

private BigDecimal rate;
private String store;
private String zone;
private String country;
private String taxClass;
private List<TaxRateDescription> descriptions = new ArrayList<>();

}
