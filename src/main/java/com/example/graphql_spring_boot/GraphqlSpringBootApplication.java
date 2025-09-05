package com.example.graphql_spring_boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class GraphqlSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphqlSpringBootApplication.class, args);
    }
}

@Controller
class MutationController {

    private final Map<Integer, Customer> db = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    @MutationMapping
    // @SchemaMapping(typeName = "Mutation", field = "addCustomer")
    Customer addCustomer(@Argument String name) {
        var id = this.id.incrementAndGet();
        var value = new Customer(id, name);
        this.db.put(id, value);
        return value;
    }

    @QueryMapping
    Customer customerById(@Argument Integer id) {
        return this.db.get(id);
    }
}

record Customer(Integer id, String name) {}