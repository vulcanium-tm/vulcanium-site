package dev.vulcanium.business.model.order.payment;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import dev.vulcanium.business.model.payments.CreditCardType;

@Embeddable
public class CreditCard {

@Column (name ="CARD_TYPE")
@Enumerated(value = EnumType.STRING)
private CreditCardType cardType;

@Column (name ="CC_OWNER")
private String ccOwner;

@Column (name ="CC_NUMBER")
private String ccNumber;

@Column (name ="CC_EXPIRES")
private String ccExpires;

@Column (name ="CC_CVV")
private String ccCvv;

public String getCcOwner() {
	return ccOwner;
}

public void setCcOwner(String ccOwner) {
	this.ccOwner = ccOwner;
}

public String getCcNumber() {
	return ccNumber;
}

public void setCcNumber(String ccNumber) {
	this.ccNumber = ccNumber;
}

public String getCcExpires() {
	return ccExpires;
}

public void setCcExpires(String ccExpires) {
	this.ccExpires = ccExpires;
}

public String getCcCvv() {
	return ccCvv;
}

public void setCcCvv(String ccCvv) {
	this.ccCvv = ccCvv;
}

public void setCardType(CreditCardType cardType) {
	this.cardType = cardType;
}

public CreditCardType getCardType() {
	return cardType;
}

}
