package dev.vulcanium.business.model.catalog.product.manufacturer;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.vulcanium.business.constants.SchemaConstant;
import dev.vulcanium.business.model.common.description.Description;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "MANUFACTURER_DESCRIPTION", uniqueConstraints={
		@UniqueConstraint(columnNames={
				"MANUFACTURER_ID",
				"LANGUAGE_ID"
		})
}
)

@TableGenerator(name = "description_gen", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "manufacturer_description_seq", allocationSize = SchemaConstant.DESCRIPTION_ID_ALLOCATION_SIZE, initialValue = SchemaConstant.DESCRIPTION_ID_START_VALUE)
public class ManufacturerDescription extends Description {
private static final long serialVersionUID = 1L;

@JsonIgnore
@ManyToOne(targetEntity = Manufacturer.class)
@JoinColumn(name = "MANUFACTURER_ID", nullable = false)
private Manufacturer manufacturer;

@Column(name = "MANUFACTURERS_URL")
private String url;

@Column(name = "URL_CLICKED")
private Integer urlClicked;

@Column(name = "DATE_LAST_CLICK")
private Date dateLastClick;

public ManufacturerDescription() {
}

}
