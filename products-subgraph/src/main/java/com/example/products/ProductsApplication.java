package com.example.products;

import graphql.execution.instrumentation.Instrumentation;
import graphql.execution.instrumentation.tracing.TracingInstrumentation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProductsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsApplication.class, args);
	}

  @Bean
  @ConditionalOnProperty( prefix = "graphql.tracing", name = "enabled", matchIfMissing = true)
  public Instrumentation tracingInstrumentation(){
    return new TracingInstrumentation();
  }

}
