package com.example.users;

import com.apollographql.federation.graphqljava.Federation;

import com.apollographql.federation.graphqljava._Entity;
import com.example.users.model.Review;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import graphql.execution.AsyncSerialExecutionStrategy;
import graphql.execution.preparsed.PreparsedDocumentEntry;
import graphql.execution.preparsed.PreparsedDocumentProvider;
import graphql.schema.DataFetcher;
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.ClassNameTypeResolver;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.example.users.model.Review.REVIEW_TYPE;

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
  public GraphQlSourceBuilderCustomizer sourceBuilderCustomizer() {
    //Data fetchers
    DataFetcher<?> entityDataFetcher = env -> {
      List<Map<String, Object>> representations = env.getArgument(_Entity.argumentName);
      return representations.stream()
        .map(representation -> {
          if (REVIEW_TYPE.equals(representation.get("__typename"))) {
            return new Review((String)representation.get("id"),"");
          }
          return null;
        })
        .collect(Collectors.toList());
    };

    //Caching - Server-side
    Cache<String, PreparsedDocumentEntry> cache = Caffeine.newBuilder().maximumSize(10_000).build();
    PreparsedDocumentProvider preparsedCache = (executionInput, computeFunction) -> {
      Function<String, PreparsedDocumentEntry> mapCompute = key -> computeFunction.apply(executionInput);
      return cache.get(executionInput.getQuery(), mapCompute);
    };

    return builder -> {
      builder.schemaFactory((registry, wiring)->
        Federation.transform(registry, wiring)
          .fetchEntities(entityDataFetcher)
          .resolveEntityType(new ClassNameTypeResolver())
          .build()
      );
      builder.configureGraphQl(graphQlBuilder -> {
          graphQlBuilder.queryExecutionStrategy(new AsyncSerialExecutionStrategy());
          graphQlBuilder.preparsedDocumentProvider(preparsedCache);
        }
      );

    };

  }


}
