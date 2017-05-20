package org.razkevich.testapi.valueobjects;

import io.swagger.annotations.ApiModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Demo class demonstrating support of inheritance and its proper documenting.
 * It should be noted that @ApiModel annotation is optional. In case it is absent
 * swagger.yaml file will not reflect inheritance with allOf keyword and rather will
 * show all fields (its own as well as inherited) flat
 */
@ApiModel(parent = ResponseVO2.class)
public class ResponseVO3 extends ResponseVO2 {

	private List<InnerStatic> innerStatics = new ArrayList<>();

	public List<InnerStatic> getInnerStatics() {
		return innerStatics;
	}

	public void setInnerStatics(List<InnerStatic> innerStatics) {
		this.innerStatics = innerStatics;
	}

	/**
	 * Some advanced class structuring is also possible
	 */
	public static class InnerStatic {

		private String innerStaticValue;

		public InnerStatic(String innerStaticValue) {
			this.innerStaticValue = innerStaticValue;
		}

		public InnerStatic() {
		}

		public String getInnerStaticValue() {
			return innerStaticValue;
		}

		public void setInnerStaticValue(String innerStaticValue) {
			this.innerStaticValue = innerStaticValue;
		}
	}
}
