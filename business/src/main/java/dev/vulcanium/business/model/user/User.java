package dev.vulcanium.business.model.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import org.hibernate.annotations.Cascade;

import dev.vulcanium.business.model.common.CredentialsReset;
import dev.vulcanium.business.model.common.audit.AuditListener;
import dev.vulcanium.business.model.common.audit.AuditSection;
import dev.vulcanium.business.model.common.audit.Auditable;
import dev.vulcanium.business.model.generic.SalesManagerEntity;
import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;

/**
 * User management
 */
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "USERS",
		indexes = { @Index(name="USR_NAME_IDX", columnList = "ADMIN_NAME")},
		uniqueConstraints=
		@UniqueConstraint(columnNames = {"MERCHANT_ID", "ADMIN_NAME"}))
public class User extends SalesManagerEntity<Long, User> implements Auditable {


private static final long serialVersionUID = 1L;

@Id
@Column(name = "USER_ID", unique=true, nullable=false)
@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "USER_SEQ_NEXT_VAL")
@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
private Long id;

public User() {

}

public User(String userName,String password, String email) {
	
	this.adminName = userName;
	this.adminPassword = password;
	this.adminEmail = email;
}

@NotEmpty
@Column(name="ADMIN_NAME", length=100)
private String adminName;

@ManyToMany(fetch=FetchType.LAZY, cascade = {CascadeType.REFRESH})
@JoinTable(name = "USER_GROUP", joinColumns = {
		@JoinColumn(name = "USER_ID", nullable = false, updatable = false) }
		,
		inverseJoinColumns = { @JoinColumn(name = "GROUP_ID",
				nullable = false, updatable = false) }
)
@Cascade({
		org.hibernate.annotations.CascadeType.DETACH,
		org.hibernate.annotations.CascadeType.LOCK,
		org.hibernate.annotations.CascadeType.REFRESH,
		org.hibernate.annotations.CascadeType.REPLICATE
	
})
private List<Group> groups = new ArrayList<Group>();

@NotEmpty
@Email
@Column(name="ADMIN_EMAIL")
private String adminEmail;

@NotEmpty
@Column(name="ADMIN_PASSWORD", length=60)
private String adminPassword;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="MERCHANT_ID", nullable=false)
private MerchantStore merchantStore;


@Column(name="ADMIN_FIRST_NAME")
private String firstName;

@Column(name="ACTIVE")
private boolean active = true;


@Column(name="ADMIN_LAST_NAME")
private String lastName;

@ManyToOne(fetch = FetchType.LAZY, targetEntity = Language.class)
@JoinColumn(name = "LANGUAGE_ID")
private Language defaultLanguage;


@Column(name="ADMIN_Q1")
private String question1;

@Column(name="ADMIN_Q2")
private String question2;

@Column(name="ADMIN_Q3")
private String question3;

@Column(name="ADMIN_A1")
private String answer1;

@Column(name="ADMIN_A2")
private String answer2;

@Column(name="ADMIN_A3")
private String answer3;

@Embedded
private AuditSection auditSection = new AuditSection();

@Temporal(TemporalType.TIMESTAMP)
@Column(name = "LAST_ACCESS")
private Date lastAccess;

@Temporal(TemporalType.TIMESTAMP)
@Column(name = "LOGIN_ACCESS")
private Date loginTime;

@Embedded
private CredentialsReset credentialsResetRequest = null;


public CredentialsReset getCredentialsResetRequest() {
	return credentialsResetRequest;
}

public void setCredentialsResetRequest(CredentialsReset credentialsResetRequest) {
	this.credentialsResetRequest = credentialsResetRequest;
}

@Override
public Long getId() {
	return this.id;
}

@Override
public void setId(Long id) {
	this.id = id;
}

@Override
public AuditSection getAuditSection() {
	return auditSection;
}

@Override
public void setAuditSection(AuditSection audit) {
	auditSection = audit;
	
}

public String getAdminName() {
	return adminName;
}

public void setAdminName(String adminName) {
	this.adminName = adminName;
}

public String getAdminEmail() {
	return adminEmail;
}

public void setAdminEmail(String adminEmail) {
	this.adminEmail = adminEmail;
}

public String getAdminPassword() {
	return adminPassword;
}

public void setAdminPassword(String adminPassword) {
	this.adminPassword = adminPassword;
}

public String getFirstName() {
	return firstName;
}

public void setFirstName(String firstName) {
	this.firstName = firstName;
}

public String getLastName() {
	return lastName;
}

public void setLastName(String lastName) {
	this.lastName = lastName;
}

public Language getDefaultLanguage() {
	return defaultLanguage;
}

public void setDefaultLanguage(Language defaultLanguage) {
	this.defaultLanguage = defaultLanguage;
}

public String getQuestion1() {
	return question1;
}

public void setQuestion1(String question1) {
	this.question1 = question1;
}

public String getQuestion2() {
	return question2;
}

public void setQuestion2(String question2) {
	this.question2 = question2;
}

public String getQuestion3() {
	return question3;
}

public void setQuestion3(String question3) {
	this.question3 = question3;
}

public String getAnswer1() {
	return answer1;
}

public void setAnswer1(String answer1) {
	this.answer1 = answer1;
}

public String getAnswer2() {
	return answer2;
}

public void setAnswer2(String answer2) {
	this.answer2 = answer2;
}

public String getAnswer3() {
	return answer3;
}

public void setAnswer3(String answer3) {
	this.answer3 = answer3;
}

public void setGroups(List<Group> groups) {
	this.groups = groups;
}

public List<Group> getGroups() {
	return groups;
}

public MerchantStore getMerchantStore() {
	return merchantStore;
}

public void setMerchantStore(MerchantStore merchantStore) {
	this.merchantStore = merchantStore;
}

public void setActive(boolean active) {
	this.active = active;
}

public boolean isActive() {
	return active;
}

public void setLastAccess(Date lastAccess) {
	this.lastAccess = lastAccess;
}

public Date getLastAccess() {
	return lastAccess;
}

public void setLoginTime(Date loginTime) {
	this.loginTime = loginTime;
}

public Date getLoginTime() {
	return loginTime;
}

}
