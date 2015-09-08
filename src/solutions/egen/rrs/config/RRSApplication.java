package solutions.egen.rrs.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import io.swagger.jaxrs.config.BeanConfig;

@ApplicationPath("/api")
public class RRSApplication extends ResourceConfig {

	public RRSApplication() {

		packages("solutions.egen.rrs");

		register(io.swagger.jaxrs.listing.ApiListingResource.class);
		register(io.swagger.jaxrs.listing.SwaggerSerializers.class);

		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setVersion("1.0.0");
		beanConfig.setSchemes(new String[] { "http" });
		beanConfig.setBasePath("/RRS/api");
		beanConfig.setResourcePackage("solutions.egen.rrs");
		beanConfig.setTitle("RRSApi Documentation");
		beanConfig.setDescription("RRS API is used for making Restaurant Reservation!!");
		beanConfig.setScan(true);

	}

}
