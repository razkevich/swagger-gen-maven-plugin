package org.razkevich.testapi.valueobjects;

import io.swagger.annotations.ApiModel;

/**
 * Demo class demonstrating support of inheritance and its proper documenting.
 * It should be noted that @ApiModel annotation is optional. In case it is absent
 * swagger.yaml file will not reflect inheritance with allOf keyword and rather will
 * show all fields (its own as well as inherited) flat
 */
@ApiModel(parent = ResponseVO2.class)
public class ResponseVO1 extends ResponseVO2 {

	private String smsSessionId;

	public String getSmsSessionId() {
		return smsSessionId;
	}

	public void setSmsSessionId(String smsSessionId) {
		this.smsSessionId = smsSessionId;
	}
}
