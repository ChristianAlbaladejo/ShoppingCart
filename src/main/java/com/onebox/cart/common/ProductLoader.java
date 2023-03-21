package com.onebox.cart.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onebox.cart.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
@Slf4j
@Component
public class ProductLoader implements CommandLineRunner {

    private final MongoTemplate mongoTemplate;

    @Value("${products.folder}")
    private String valueFromFile;
    @Autowired
    public ProductLoader(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Initial product load");
        InputStream inputStream = getClass().getResourceAsStream(valueFromFile);
        List<Product> products = new ObjectMapper().readValue(inputStream, new TypeReference<>() {
        });
        mongoTemplate.insertAll(products);
    }
}
