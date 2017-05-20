package org.razkevich.swaggergen;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class SwaggerGeneratorIntegrationTest {

	@Test
	public void checkFileExistence() throws Exception {
		Path swaggerYaml = Paths.get("target/swagger.yaml");
		Assert.assertTrue(Files.exists(swaggerYaml));
		List<String> lines = Files.lines(swaggerYaml).map(StringUtils::trim).collect(Collectors.toList());
		Assert.assertTrue(lines.size() > 10);
		Assert.assertTrue(lines.contains("swagger: \"2.0\""));
		Assert.assertTrue(lines.contains("paths:"));
		Assert.assertTrue(lines.contains("/jaxrs-service1/method1:"));
		Assert.assertTrue(lines.contains("/jaxrs-service1/method2:"));
		Assert.assertTrue(lines.contains("/jaxrs-service2/method3:"));
		Assert.assertTrue(lines.contains("definitions:"));
		Assert.assertTrue(lines.contains("ResponseVO1:"));
		Assert.assertTrue(lines.contains("InnerStatic:"));
		Assert.assertTrue(lines.contains("ResponseVO2:"));
		Assert.assertTrue(lines.contains("ResponseVO3:"));
		Assert.assertTrue(lines.contains("RequestVO1:"));
		Assert.assertTrue(lines.contains("RequestVO2:"));
		Assert.assertTrue(lines.contains("RequestVO3:"));
		Assert.assertEquals(2, lines.stream().filter(s -> s.contains("allOf")).count());
	}
}
