package dev.vulcanium.business.modules.integration.shipping.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dev.vulcanium.business.model.system.CustomIntegrationConfiguration;
import dev.vulcanium.business.model.system.IntegrationConfiguration;

public class CustomShippingQuotesConfiguration extends IntegrationConfiguration implements CustomIntegrationConfiguration, Serializable {

private String moduleCode;

private List<CustomShippingQuotesRegion> regions = new ArrayList<CustomShippingQuotesRegion>();


private static final long serialVersionUID = 1L;


@SuppressWarnings("unchecked")
public String toJSONString() {
	StringBuilder returnString = new StringBuilder();
	returnString.append("{");
	returnString.append("\"moduleCode\"").append(":\"").append(this.getModuleCode()).append("\"");
	returnString.append(",");
	returnString.append("\"active\"").append(":").append(this.isActive());
	
	
	
	if(regions!=null && regions.size()>0) {
		
		returnString.append(",");
		StringBuilder regionsList = new StringBuilder();
		int countRegion = 0;
		regionsList.append("[");
		for(CustomShippingQuotesRegion region : regions) {
			regionsList.append(region.toJSONString());
			countRegion ++;
			if(countRegion<regions.size()) {
				regionsList.append(",");
			}
		}
		regionsList.append("]");
		returnString.append("\"regions\"").append(":").append(regionsList.toString());
	}
	
	returnString.append("}");
	return returnString.toString();
}

@Override
public String getModuleCode() {
	return moduleCode;
}

@Override
public void setModuleCode(String moduleCode) {
	this.moduleCode = moduleCode;
	
}

public void setRegions(List<CustomShippingQuotesRegion> regions) {
	this.regions = regions;
}

public List<CustomShippingQuotesRegion> getRegions() {
	return regions;
}

}
