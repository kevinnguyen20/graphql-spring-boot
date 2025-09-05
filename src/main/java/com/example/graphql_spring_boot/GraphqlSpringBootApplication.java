package com.example.graphql_spring_boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@SpringBootApplication
public class GraphqlSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphqlSpringBootApplication.class, args);
	}

    @Bean
    RuntimeWiringConfigurer runtimeWiringConfigurer(CrmService crm) {
        return builder -> {
            builder.type("Customer", wiring -> wiring.dataFetcher("profile", env -> crm.getProfile(env.getSource())));
            builder.type("Query", wiring -> wiring.dataFetcher("customerById", env -> crm.getCustomerById(Integer.parseInt(env.getArgument("id")))));
            builder.type("Query", wiring -> wiring.dataFetcher("customers", env -> crm.getCustomers()));
        };
    }
}

record Customer(Integer id, String name) {}

record Profile(Integer id, Integer customerId) {}

@Service
class CrmService {
    Profile getProfile(Customer customer) {
        return new Profile(customer.id(), customer.id());
    }
    Customer getCustomerById(Integer id) {
        return new Customer(id, Math.random() > .5 ? "A" : "B");
    }

    Collection<Customer> getCustomers() {
        return List.of(new Customer(1, "A"),
                new Customer(2, "B"));
    }
}
