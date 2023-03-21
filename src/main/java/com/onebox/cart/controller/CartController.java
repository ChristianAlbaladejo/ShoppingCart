package com.onebox.cart.controller;

import com.onebox.cart.dto.CartDto;
import com.onebox.cart.exception.CartNotFound;
import com.onebox.cart.exception.EmptyCartException;
import com.onebox.cart.exception.ProductNotFoundException;
import com.onebox.cart.model.Cart;
import com.onebox.cart.model.Product;
import com.onebox.cart.services.CartService;
import com.onebox.cart.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/cart")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ProductService productService;

    @Operation(summary = "Create a cart")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CartDto cartDto) {
        log.info("Cart creation request - Id: {} - Products: {}",
                cartDto.getId(), cartDto.getProducts().toString());
        try {
            var cart = cartService.create(cartDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(cart);
        } catch (EmptyCartException | ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Update a cart")
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody CartDto cartDto) {
        log.info("Update request - Id: {} - Products: {}",
                cartDto.getId(), cartDto.getProducts().toString());
        try {
            var cart = cartService.update(cartDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(cart);
        } catch (EmptyCartException | ProductNotFoundException | CartNotFound e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Get all products")
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        log.info("Request to retrieve all products");
        return productService.findAll();
    }

    @Operation(summary = "Get cart by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable String id) {
        log.info("Request to retrieve the product with id {}", id);
        var cart = cartService.findById(id);
        if (cart.isPresent()) {
            return ResponseEntity.ok(cart.get());
        } else {
            log.error("Could not find product with id {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete cart by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartById(@PathVariable String id) {
        log.info("Request to delete product with id {}", id);
        cartService.deleteCartById(id);
        return ResponseEntity.ok().build();
    }
}
