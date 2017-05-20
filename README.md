Swagger gen maven plugin
-----
This is Swagger gen maven plugin. The project has two modules:

1. swagger-gen-plugin - maven plugin itself
2. swagger-gen-plugin-test - unit and integration tests to check plugin correctness and provide example of usage

In order to use the plugin you should follow the steps:

1. Make sure it's available from repositories
2. If it's not available from repositories you can manually install it locally.
 To do that simple run "mvn clean install" from root directory (where parent .pom file is placed)
2. Make your project use swagger-gen-plugin. pom.xml file of swagger-gen-plugin-test module is
 an example of how to achieve that goal (see "Swagger gen plugin usage example" section)