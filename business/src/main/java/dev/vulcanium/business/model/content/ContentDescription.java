package dev.vulcanium.business.model.content;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.UniqueConstraint;

import dev.vulcanium.business.constants.SchemaConstant;
import dev.vulcanium.business.model.common.description.Description;
import dev.vulcanium.business.model.reference.language.Language;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="CONTENT_DESCRIPTION",uniqueConstraints={
		@UniqueConstraint(columnNames={
				"CONTENT_ID",
				"LANGUAGE_ID"
		})
}
)

@TableGenerator(name = "description_gen", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "content_description_seq", allocationSize = SchemaConstant.DESCRIPTION_ID_ALLOCATION_SIZE, initialValue = SchemaConstant.DESCRIPTION_ID_START_VALUE)
public class ContentDescription extends Description implements Serializable {

private static final long serialVersionUID = 1L;

@ManyToOne(targetEntity = Content.class)
@JoinColumn(name = "CONTENT_ID", nullable = false)
private Content content;

@Column(name="SEF_URL", length=120)
private String seUrl;


@Column(name="META_KEYWORDS")
private String metatagKeywords;

@Column(name="META_TITLE")
private String metatagTitle;

@Column(name="META_DESCRIPTION")
private String metatagDescription;

public ContentDescription() {
}

public ContentDescription(String name, Language language) {
	this.setName(name);
	this.setLanguage(language);
	super.setId(0L);
}

}
