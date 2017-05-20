package org.razkevich.testapi.services;

import org.razkevich.testapi.valueobjects.RequestVO3;
import org.razkevich.testapi.valueobjects.ResponseVO3;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/jaxrs-service2")
@Produces(APPLICATION_JSON + "; charset=UTF-8")
@Consumes(APPLICATION_JSON)
public interface JaxRsService2 {

	@POST
	@Path("/method3")
	ResponseVO3 method3(RequestVO3 request);
}
