package org.razkevich.testapi.services;

import org.razkevich.testapi.valueobjects.RequestVO1;
import org.razkevich.testapi.valueobjects.RequestVO2;
import org.razkevich.testapi.valueobjects.ResponseVO1;
import org.razkevich.testapi.valueobjects.ResponseVO2;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/jaxrs-service1")
@Produces(APPLICATION_JSON + "; charset=UTF-8")
@Consumes(APPLICATION_JSON)
public interface JaxRsService1 {

	@POST
	@Path("/method1")
	ResponseVO1 method1(RequestVO1 request);

	@POST
	@Path("/method2")
	ResponseVO2 method2(RequestVO2 request);
}
