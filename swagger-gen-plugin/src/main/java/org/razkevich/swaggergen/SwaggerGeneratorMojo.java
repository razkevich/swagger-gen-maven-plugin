package org.razkevich.swaggergen;

import io.swagger.models.Contact;
import io.swagger.models.Info;
import io.swagger.models.License;
import io.swagger.models.Scheme;
import io.swagger.util.Yaml;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mojo(name = "generateSwagger", requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME)
public class SwaggerGeneratorMojo extends AbstractMojo {

	private static final List<Scheme> SCHEMES = Arrays.asList(Scheme.HTTP, Scheme.HTTPS);
	private static final String YAML_EXTENTION = ".yaml";

	@Parameter(required = true)
	private String title;
	@Parameter(required = true)
	private String version;
	@Parameter(required = true)
	private String description;
	@Parameter(required = true)
	private List<String> packagesToScan;
	@Parameter(required = true)
	private String termsOfService;
	@Parameter(required = true)
	private String contactEmail;
	@Parameter(required = true)
	private String contactName;
	@Parameter(required = true)
	private String contactUrl;
	@Parameter(required = true)
	private String licenseUrl;
	@Parameter(required = true)
	private String licenseName;
	@Parameter(required = true)
	private String basePath;
	@Parameter(required = true)
	private String host;
	@Parameter(required = true)
	private String swaggerDirectory;
	@Parameter(required = true)
	private String swaggerFileName;
	@Parameter(defaultValue = "${project}", required = true)
	private MavenProject project;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		try {
			List<URL> urlsList = project.getCompileClasspathElements().stream()
					.map(File::new)
					.map(File::toURI)
					.map(uri -> {
						try {
							return uri.toURL();
						} catch (MalformedURLException e) {
							throw new RuntimeException(e);
						}
					})
					.collect(Collectors.toList());
			URL[] urlsArray = urlsList.toArray(new URL[urlsList.size()]);
			Thread.currentThread().setContextClassLoader(URLClassLoader.newInstance(urlsArray, Thread.currentThread().getContextClassLoader()));
			java.nio.file.Path path = Paths.get(project.getBasedir().toURI()).resolve(swaggerDirectory);
			if (Files.notExists(path) || !Files.isDirectory(path)) {
				Files.createDirectory(path);
			}
			Files.write(path.resolve(swaggerFileName + YAML_EXTENTION), Yaml.pretty().writeValueAsBytes(SwaggerGeneratorService.generateSwagger(packagesToScan, basePath, host,
					new Info().title(title).version(version).description(description).termsOfService(termsOfService)
							.contact(new Contact().email(contactEmail).name(contactName).url(contactUrl))
							.license(new License().url(licenseUrl).name(licenseName)), SCHEMES)));
		} catch (Exception e) {
			throw new MojoFailureException("Error generating swagger.yaml", e);
		}
	}
}
