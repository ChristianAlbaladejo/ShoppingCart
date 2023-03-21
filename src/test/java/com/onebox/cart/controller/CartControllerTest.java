package com.onebox.cart.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.onebox.cart.dto.CartDto;
import com.onebox.cart.exception.CartNotFound;
import com.onebox.cart.exception.EmptyCartException;
import com.onebox.cart.exception.ProductNotFoundException;
import com.onebox.cart.model.Cart;
import com.onebox.cart.services.CartService;
import com.onebox.cart.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @MockBean
    private ProductService productService;

    @Test
    void testCreateCart001() throws Exception {
        var cart = new Cart();
        cart.setId("1");
        cart.setProducts(new ArrayList<>());
        cart.setDateUpdate(LocalDateTime.now());
        cart.setTotalAmount(BigDecimal.ZERO);

        var requestJson = mapperJson(cart);

        when(cartService.create(any(CartDto.class))).thenReturn(cart);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cart/create").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                        .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateCart002() throws Exception {
        var cart = new Cart();

        var requestJson = mapperJson(cart);

        when(cartService.create(any(CartDto.class))).thenThrow(EmptyCartException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cart/create").contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testCreateCart003() throws Exception {
        var cart = new Cart();
        cart.setId("1");
        cart.setProducts(new ArrayList<>());
        cart.setDateUpdate(LocalDateTime.now());
        cart.setTotalAmount(BigDecimal.ZERO);

        var requestJson = mapperJson(cart);

        when(cartService.create(any(CartDto.class))).thenThrow(ProductNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cart/create").contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testUpdateCart001() throws Exception {
        var cart = new Cart();
        cart.setId("1");
        cart.setProducts(new ArrayList<>());
        cart.setDateUpdate(LocalDateTime.now());
        cart.setTotalAmount(BigDecimal.ZERO);

        var requestJson = mapperJson(cart);

        when(cartService.update(any(CartDto.class))).thenReturn(cart);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/cart/update").contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateCart002() throws Exception {
        var cart = new Cart();
        cart.setId("1");
        cart.setProducts(new ArrayList<>());
        cart.setDateUpdate(LocalDateTime.now());
        cart.setTotalAmount(BigDecimal.ZERO);

        var requestJson = mapperJson(cart);

        when(cartService.update(any(CartDto.class))).thenThrow(EmptyCartException.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/cart/update").contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testUpdateCart003() throws Exception {
        var cart = new Cart();
        cart.setId("1");
        cart.setProducts(new ArrayList<>());
        cart.setDateUpdate(LocalDateTime.now());
        cart.setTotalAmount(BigDecimal.ZERO);

        var requestJson = mapperJson(cart);

        when(cartService.update(any(CartDto.class))).thenThrow(ProductNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/cart/update").contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testUpdateCart004() throws Exception {
        var cart = new Cart();
        cart.setId("1");
        cart.setProducts(new ArrayList<>());
        cart.setDateUpdate(LocalDateTime.now());
        cart.setTotalAmount(BigDecimal.ZERO);

        var requestJson = mapperJson(cart);

        when(cartService.update(any(CartDto.class))).thenThrow(CartNotFound.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/cart/update").contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testGetAllProducts001() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cart/products"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void testGetCartById001() throws Exception {
        var cart = new Cart();
        cart.setId("1234");
        cart.setProducts(new ArrayList<>());
        cart.setDateUpdate(LocalDateTime.now());
        cart.setTotalAmount(BigDecimal.ZERO);

        when(cartService.findById(anyString())).thenReturn(Optional.of(cart));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cart/1234"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void testGetCartById002() throws Exception {
        var cart = new Cart();
        cart.setId("1234");
        cart.setProducts(new ArrayList<>());
        cart.setDateUpdate(LocalDateTime.now());
        cart.setTotalAmount(BigDecimal.ZERO);

        when(cartService.findById(anyString())).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cart/1234"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(404));
    }

    @Test
    void testDeleteById001() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/cart/1234"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(200));
    }

    private static String mapperJson(Cart cart) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(cart);
        return requestJson;
    }
}
