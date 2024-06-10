package dev.vulcanium.business.model.catalog.category;

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
import dev.vulcanium.business.model.reference.language.Language;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="CATEGORY_DESCRIPTION",uniqueConstraints={
		@UniqueConstraint(columnNames={
				"CATEGORY_ID",
				"LANGUAGE_ID"
		})
}
)
@TableGenerator(name = "description_gen", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "category_description_seq", allocationSize = SchemaConstant.DESCRIPTION_ID_ALLOCATION_SIZE, initialValue = SchemaConstant.DESCRIPTION_ID_START_VALUE)
public class CategoryDescription extends Description {
private static final long serialVersionUID = 1L;

@JsonIgnore
@ManyToOne(targetEntity = Category.class)
@JoinColumn(name = "CATEGORY_ID", nullable = false)
private Category category;

@Column(name="SEF_URL", length=120)
private String seUrl;

@Column(name = "CATEGORY_HIGHLIGHT")
private String categoryHighlight;

@Column(name="META_TITLE", length=120)
private String metatagTitle;

@Column(name="META_KEYWORDS")
private String metatagKeywords;

@Column(name="META_DESCRIPTION")
private String metatagDescription;

public CategoryDescription() {
}

public CategoryDescription(String name, Language language) {
	this.setName(name);
	this.setLanguage(language);
	super.setId(0L);
}

}
