package org.razkevich.testapi.valueobjects;

/**
 * Simple demo class with a number of fields
 */
public class RequestVO2 {

	private String stringValue;
	private Integer intValue;
	private Boolean boolValue;

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public Integer getIntValue() {
		return intValue;
	}

	public void setIntValue(Integer intValue) {
		this.intValue = intValue;
	}

	public Boolean getBoolValue() {
		return boolValue;
	}

	public void setBoolValue(Boolean boolValue) {
		this.boolValue = boolValue;
	}
}
