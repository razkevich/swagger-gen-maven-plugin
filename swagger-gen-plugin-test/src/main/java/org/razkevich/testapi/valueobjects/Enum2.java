package org.razkevich.testapi.valueobjects;

/**
 * Enums are correctly reflected in swagger.yaml. However please note that
 * swagger.yaml will list all values of enum the number of times it is referred to
 */
public enum Enum2 {

	VALUE_1(1, "value 1"),
	VALUE_2(2, "value 2");

	private Integer integer;
	private String string;

	Enum2(Integer integer, String string) {
		this.integer = integer;
		this.string = string;
	}

	public Integer getInteger() {
		return integer;
	}

	public String getString() {
		return string;
	}
}
