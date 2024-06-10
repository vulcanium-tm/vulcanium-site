package dev.vulcanium.business.model.common.description;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.vulcanium.business.model.common.audit.AuditListener;
import dev.vulcanium.business.model.common.audit.AuditSection;
import dev.vulcanium.business.model.common.audit.Auditable;
import dev.vulcanium.business.model.reference.language.Language;

@MappedSuperclass
@EntityListeners(value = AuditListener.class)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Description implements Auditable, Serializable {
private static final long serialVersionUID = 1L;

@Setter
@Getter
@Id
@Column(name = "DESCRIPTION_ID")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "description_gen")
private Long id;

@JsonIgnore
@Embedded
private AuditSection auditSection = new AuditSection();

@Setter
@Getter
@ManyToOne(optional = false)
@JoinColumn(name = "LANGUAGE_ID")
private Language language;

@Setter
@Getter
@NotEmpty
@Column(name="NAME", nullable = false, length=120)
private String name;

@Setter
@Getter
@Column(name="TITLE", length=100)
private String title;

@Setter
@Getter
@Column(name="DESCRIPTION")
@Type(type = "org.hibernate.type.TextType")
private String description;

public Description() {
}

public Description(Language language, String name) {
	this.setLanguage(language);
	this.setName(name);
}

@Override
public AuditSection getAuditSection() {
	return auditSection;
}

@Override
public void setAuditSection(AuditSection auditSection) {
	this.auditSection = auditSection;
}

}
