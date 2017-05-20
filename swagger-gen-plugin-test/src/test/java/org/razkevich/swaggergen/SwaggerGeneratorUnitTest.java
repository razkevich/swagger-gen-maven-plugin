package org.razkevich.swaggergen;

import io.swagger.models.*;
import org.junit.Assert;
import org.junit.Test;
import org.razkevich.testapi.services.JaxRsService1;
import org.razkevich.testapi.valueobjects.RequestVO1;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SwaggerGeneratorUnitTest {

	private static final List<Scheme> SCHEMES = Arrays.asList(Scheme.HTTP, Scheme.HTTPS);

	@Test
	public void testAppendMethodToSwagger() throws Exception {
		Method method = JaxRsService1.class.getMethod("method1", RequestVO1.class);
		Swagger swagger = new Swagger();
		SwaggerGeneratorService.appendMethodToSwagger(method, swagger);
		Assert.assertNotNull(swagger.getPaths());
		Assert.assertTrue(swagger.getPaths().containsKey("/jaxrs-service1/method1"));
		Operation postOperation = swagger.getPaths().get("/jaxrs-service1/method1").getPost();
		Assert.assertEquals(postOperation.getOperationId(), "method1");
		Assert.assertTrue(postOperation.getConsumes().contains("application/json"));
		Assert.assertTrue(postOperation.getProduces().contains("application/json; charset=UTF-8"));
		Assert.assertFalse(postOperation.getParameters().isEmpty());
		Assert.assertFalse(postOperation.getResponses().isEmpty());
		Assert.assertNotNull(swagger.getDefinitions());
		Assert.assertTrue(swagger.getDefinitions().containsKey("ResponseVO1"));
		Assert.assertTrue(swagger.getDefinitions().containsKey("RequestVO1"));
	}

	@Test
	public void testGenerateSwagger() {
		Swagger swagger = SwaggerGeneratorService.generateSwagger(Collections.singletonList("org.razkevich.testapi.services"), "/jaxrs-service1", "localhost",
				new Info().title("title").version("version").description("description").termsOfService("termsOfService")
						.contact(new Contact().email("contactEmail").name("contactName").url("contactUrl"))
						.license(new License().url("licenseUrl").name("licenseName")), SCHEMES);
		Assert.assertEquals(3, swagger.getPaths().size());
		Assert.assertEquals(7, swagger.getDefinitions().size());
		Assert.assertEquals(swagger.getBasePath(), "/jaxrs-service1");
		Assert.assertEquals(swagger.getHost(), "localhost");
		Assert.assertNotNull(swagger.getInfo());
		Assert.assertEquals(swagger.getInfo().getTitle(), "title");
		Assert.assertEquals(swagger.getInfo().getVersion(), "version");
		Assert.assertEquals(swagger.getInfo().getDescription(), "description");
		Assert.assertEquals(swagger.getInfo().getTermsOfService(), "termsOfService");
		Assert.assertNotNull(swagger.getInfo().getContact());
		Assert.assertEquals(swagger.getInfo().getContact().getEmail(), "contactEmail");
		Assert.assertEquals(swagger.getInfo().getContact().getName(), "contactName");
		Assert.assertEquals(swagger.getInfo().getContact().getUrl(), "contactUrl");
		Assert.assertNotNull(swagger.getInfo().getLicense());
		Assert.assertEquals(swagger.getInfo().getLicense().getUrl(), "licenseUrl");
		Assert.assertEquals(swagger.getInfo().getLicense().getName(), "licenseName");
		Assert.assertTrue(swagger.getSchemes().containsAll(SCHEMES));
	}
}
