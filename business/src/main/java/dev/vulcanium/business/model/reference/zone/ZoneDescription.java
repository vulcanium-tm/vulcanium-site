package dev.vulcanium.business.model.reference.zone;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.vulcanium.business.constants.SchemaConstant;
import dev.vulcanium.business.model.common.description.Description;
import dev.vulcanium.business.model.reference.language.Language;

@Entity
@Table(name="ZONE_DESCRIPTION", uniqueConstraints={
		@UniqueConstraint(columnNames={
				"ZONE_ID",
				"LANGUAGE_ID"
		})
}
)
@TableGenerator(name = "description_gen", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "zone_description_seq", allocationSize = SchemaConstant.DESCRIPTION_ID_ALLOCATION_SIZE, initialValue = SchemaConstant.DESCRIPTION_ID_START_VALUE)
public class ZoneDescription extends Description {
private static final long serialVersionUID = 1L;

@JsonIgnore
@ManyToOne(targetEntity = Zone.class)
@JoinColumn(name = "ZONE_ID", nullable = false)
private Zone zone;

public ZoneDescription() {
}

public ZoneDescription(Zone zone, Language language, String name) {
	setZone(zone);
	setLanguage(language);
	setName(name);
}

public Zone getZone() {
	return zone;
}

public void setZone(Zone zone) {
	this.zone = zone;
}
}
