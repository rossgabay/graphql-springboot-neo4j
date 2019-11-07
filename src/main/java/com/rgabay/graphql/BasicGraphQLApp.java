package com.rgabay.graphql;

import com.coxautodev.graphql.tools.SchemaParser;
import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLHttpServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import com.rgabay.graphql.resolver.Query;

@SpringBootApplication
public class BasicGraphQLApp {

	public static void main(String[] args) {
		SpringApplication.run(BasicGraphQLApp.class, args);
	}

	@Bean
	public ServletRegistrationBean graphQLServlet() {
		return new ServletRegistrationBean(SimpleGraphQLHttpServlet.
				newBuilder(buildSchema())
				.build(),"/graphql");
	}

	private static GraphQLSchema buildSchema() {
		return SchemaParser
				.newParser()
				.file("graphql/schema.graphqls")
//                .dictionary()
				.resolvers( new Query())
				.build()
				.makeExecutableSchema();
	}
}
