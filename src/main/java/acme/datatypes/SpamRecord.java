/*
 * Money.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.datatypes;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import acme.framework.datatypes.AbstractDatatype;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class SpamRecord extends AbstractDatatype {

	// Serialisation identifier -----------------------------------------------

	protected static final long serialVersionUID = 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	protected String term;

	@NotNull
	@Range(min = 0, max = 1)
	protected Double weight;

	protected String boosterTerm;

	// Object interface -------------------------------------------------------

	@Override
	public String toString() {
		StringBuilder dataBuilder = new StringBuilder();
		appendFieldValue(dataBuilder, term);
		appendFieldValue(dataBuilder, Double.toString(weight));
		appendFieldValue(dataBuilder, boosterTerm);

		return dataBuilder.toString();
	}

	private void appendFieldValue(StringBuilder dataBuilder, String fieldValue) {
		if (fieldValue != null) {
			dataBuilder.append(fieldValue).append(",");
		} else {
			dataBuilder.append("").append(",");
		}
	}

}
