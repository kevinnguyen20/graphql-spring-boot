package com.example.graphql_spring_boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.Collection;
import java.util.List;

@SpringBootApplication
public class GraphqlSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphqlSpringBootApplication.class, args);
    }
}

@Controller
class GreetingsController {

    private final List<Customer> customerList = List.of(
            new Customer(1, "A"),
            new Customer(2, "B")
    );

    @QueryMapping
    Customer customerById(@Argument Integer id) {
        return new Customer(id, Math.random() > .5 ? "A" : "B");
    }

    @QueryMapping
    String helloWithName(@Argument String name) {
        return "Hello " + name + "!";
    }

    @QueryMapping
    String hello() {
        return "Hello World!";
    }

    @QueryMapping
    Collection<Customer> customers() {
        return this.customerList;
    }

    @SchemaMapping(typeName = "Customer")
    Account account(Customer customer) {
        return new Account(customer.id());
    }
}

record Account(Integer id){}
record Customer(Integer id, String name) {}