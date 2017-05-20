package org.razkevich.swaggergen;

import com.google.common.reflect.ClassPath;
import io.swagger.converter.ModelConverters;
import io.swagger.models.*;
import io.swagger.models.properties.Property;
import io.swagger.models.properties.RefProperty;
import io.swagger.util.ParameterProcessor;

import javax.ws.rs.*;
import javax.ws.rs.Path;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

final class SwaggerGeneratorService {

	private SwaggerGeneratorService() {
	}

	private static final List<String> SUPPORTED_PRIMITIVE_TYPES = Arrays.asList("integer", "string", "number", "boolean", "array", "file");
	private static final List<Class<? extends Annotation>> SUPPORTED_HTTP_METHODS = Arrays.asList(GET.class, POST.class, PUT.class, DELETE.class, OPTIONS.class, HEAD.class);
	private static final String SUCCESSFUL_OPERATION = "successful operation";
	private static final int SUCCESS_CODE = 200;

	static Swagger generateSwagger(List<String> locations, String basePath, String host, Info info, List<Scheme> schemes) {
		try {
			Swagger swagger = new Swagger();
			Set<ClassPath.ClassInfo> topLevelClasses =
					ClassPath.from(Thread.currentThread().getContextClassLoader()).getTopLevelClasses();
			locations.stream()
					.flatMap(location -> topLevelClasses.stream()
							.filter(classInfo -> classInfo.getName().startsWith(location))
							.map(ClassPath.ClassInfo::load)
							.flatMap(clazz -> Stream.of(clazz.getMethods()))
							.filter(method -> method.isAnnotationPresent(Path.class)))
					.forEach(method -> SwaggerGeneratorService.appendMethodToSwagger(method, swagger));
			return swagger.schemes(schemes).host(host).info(info).basePath(basePath);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	static void appendMethodToSwagger(Method method, Swagger swagger) {
		Class declaringClass = method.getDeclaringClass(), returnClass = method.getReturnType();
		Operation operation = new Operation().operationId(method.getName());
		Response successfulResponse = new Response().description(SUCCESSFUL_OPERATION);
		if (Arrays.asList(null, Void.class, void.class, javax.ws.rs.core.Response.class).contains(returnClass)) {
			operation.defaultResponse(successfulResponse);
		} else {
			Property property = ModelConverters.getInstance().readAsProperty(returnClass);
			Map<String, Model> models = ModelConverters.getInstance().read(returnClass), allModels = ModelConverters.getInstance().readAll(returnClass);
			if (Optional.ofNullable(property).map(Property::getType).map(SUPPORTED_PRIMITIVE_TYPES::contains).orElse(models.isEmpty())) {
				operation.response(SUCCESS_CODE, successfulResponse.schema(property));
			} else {
				Stream.concat(models.entrySet().stream(), allModels.entrySet().stream())
						.forEach(entry -> {
							operation.response(SUCCESS_CODE, successfulResponse.schema(new RefProperty().asDefault(entry.getKey())));
							swagger.model(entry.getKey(), entry.getValue());
						});
			}
		}
		Stream.of(
				Optional.ofNullable(method.getAnnotation(Produces.class)),
				Optional.ofNullable((Produces) declaringClass.getAnnotation(Produces.class))
		).forEach(optional -> optional
				.map(produces -> Stream.of(produces.value()))
				.ifPresent(produces -> produces.forEach(operation::produces)));
		Stream.of(
				Optional.ofNullable(method.getAnnotation(Consumes.class)),
				Optional.ofNullable((Consumes) declaringClass.getAnnotation(Consumes.class))
		).forEach(optional -> optional
				.map(consumes -> Stream.of(consumes.value()))
				.ifPresent(consumes -> consumes.forEach(operation::consumes)));
		String httpMethod = SUPPORTED_HTTP_METHODS.stream()
				.filter(method::isAnnotationPresent)
				.map(m -> m.getSimpleName().toLowerCase())
				.findAny()
				.orElse(null);
		String sName = ((Path) declaringClass.getAnnotation(Path.class)).value();
		String mName = method.getAnnotation(Path.class).value();
		String path = Paths.get(sName, mName).toString().replace("\\", "/");
		swagger.path(path, new io.swagger.models.Path().set(httpMethod, operation));
		Stream.of(method.getGenericParameterTypes())
				.map(type -> ParameterProcessor.applyAnnotations(swagger, null, type, Collections.emptyList()))
				.filter(Objects::nonNull)
				.forEach(operation::parameter);
	}
}
