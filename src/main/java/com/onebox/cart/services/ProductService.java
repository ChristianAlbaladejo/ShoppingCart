package com.onebox.cart.services;

import com.onebox.cart.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductService extends CrudRepository<Product, String> {
    /**
     * Retrieve all products
     * @return
     */
    List<Product> findAll();

    /**
     * Get product by id
     * @param id
     * @return
     */
    Product getProductById(String id);
}
