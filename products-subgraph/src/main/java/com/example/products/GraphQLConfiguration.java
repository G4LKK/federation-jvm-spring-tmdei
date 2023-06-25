package com.example.products;

import com.apollographql.federation.graphqljava.Federation;
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * GraphQL Configuration class
 */
@Configuration
public class GraphQLConfiguration {

  /**
   * Method to handle the GraphQL Federation transformations
   * @return Source Builder Customizer
   */
  @Bean
  public GraphQlSourceBuilderCustomizer federationTransform() {
    return builder -> {
      builder.schemaFactory((registry, wiring)->
        Federation.transform(registry, wiring)
          .fetchEntities(env -> null)
          .resolveEntityType(env -> null)
          .build()
      );
    };
  }
}
