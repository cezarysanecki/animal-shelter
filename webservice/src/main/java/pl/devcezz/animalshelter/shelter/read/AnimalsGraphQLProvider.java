package pl.devcezz.animalshelter.shelter.read;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.idl.TypeRuntimeWiring;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;

@Component
class AnimalsGraphQLProvider {

    private final AnimalsGraphQLDataFetchers animalsGraphQLDataFetchers;
    private GraphQL graphQL;

    AnimalsGraphQLProvider(AnimalsGraphQLDataFetchers animalsGraphQLDataFetchers) {
        this.animalsGraphQLDataFetchers = animalsGraphQLDataFetchers;
    }

    @Bean
    GraphQL graphQL() {
        return graphQL;
    }

    @PostConstruct
    public void init() throws IOException {
        URL url = Resources.getResource("animal-schema.graphqls");
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(TypeRuntimeWiring.newTypeWiring("Query")
                        .dataFetcher("animalById", animalsGraphQLDataFetchers.getAnimalByIdDataFetcher()))
                .type(TypeRuntimeWiring.newTypeWiring("Query")
                        .dataFetcher("allAnimals", animalsGraphQLDataFetchers.getAnimalsDataFetcher()))
                .build();
    }
}
