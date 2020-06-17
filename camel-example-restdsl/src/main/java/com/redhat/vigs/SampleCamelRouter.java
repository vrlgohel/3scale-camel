package com.redhat.vigs;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class SampleCamelRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

		//  enable swagger docs
		restConfiguration().component("servlet").bindingMode(RestBindingMode.json).apiContextPath("api")
				.apiProperty("api.title", "Hello World").apiProperty("api.version", "1.0.0");

		// Camel Route
		rest().get("/hello").produces("text/plain").responseMessage().code(200).message("OK").endResponseMessage()
				.route().routeId("say-hello").to("direct:hello");

		from("direct:hello").routeId("log-hello").log(LoggingLevel.INFO, "Hello World").transform()
				.simple("Hello World");

	}

}
