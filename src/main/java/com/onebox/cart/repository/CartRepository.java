package com.onebox.cart.repository;

import com.onebox.cart.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CartRepository extends MongoRepository<Cart, String> {
    @Query("{ 'dateUpdate' : { $lt: ?0 } }")
    List<Cart> findByDateUpdateBefore(LocalDateTime dateTime);
    Optional<Cart> findById(String id);
}
