package org.razkevich.testapi.valueobjects;

import io.swagger.annotations.ApiModel;

/**
 * Demo class demonstrating support of inheritance and its proper documenting.
 * It should be noted that @ApiModel annotation is optional. In case it is absent
 * swagger.yaml file will not reflect inheritance with allOf keyword and rather will
 * show all fields (its own as well as inherited) flat
 */
@ApiModel(subTypes = {ResponseVO3.class, ResponseVO1.class})
public class ResponseVO2 {

	private Integer resultCode;
	private String resultDescription;

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultDescription() {
		return resultDescription;
	}

	public void setResultDescription(String resultDescription) {
		this.resultDescription = resultDescription;
	}
}
