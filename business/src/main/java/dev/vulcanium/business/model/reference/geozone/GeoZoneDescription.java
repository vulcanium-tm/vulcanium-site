package dev.vulcanium.business.model.reference.geozone;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.UniqueConstraint;

import dev.vulcanium.business.constants.SchemaConstant;
import dev.vulcanium.business.model.common.description.Description;

@Entity
@Table(name="GEOZONE_DESCRIPTION", uniqueConstraints={
		@UniqueConstraint(columnNames={
				"GEOZONE_ID",
				"LANGUAGE_ID"
		})
}
)

@TableGenerator(name = "description_gen", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "geozone_description_seq", allocationSize = SchemaConstant.DESCRIPTION_ID_ALLOCATION_SIZE, initialValue = SchemaConstant.DESCRIPTION_ID_START_VALUE)
public class GeoZoneDescription extends Description {
private static final long serialVersionUID = 1L;

@ManyToOne(targetEntity = GeoZone.class)
@JoinColumn(name = "GEOZONE_ID")
private GeoZone geoZone;

public GeoZoneDescription() {
}

public GeoZone getGeoZone() {
	return geoZone;
}

public void setGeoZone(GeoZone geoZone) {
	this.geoZone = geoZone;
}
}
