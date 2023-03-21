package com.onebox.cart.common;

import com.onebox.cart.model.Cart;
import com.onebox.cart.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartCleanupTaskTest {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartCleanupTask cartCleanupTask;

    @Test
    void shouldCleanupCarts() {
        var carts = new ArrayList<Cart>();
        carts.add(new Cart());
        when(cartRepository.findByDateUpdateBefore(any())).thenReturn(carts);
        cartCleanupTask.cleanupCarts();
        verify(cartRepository, times(1)).deleteAll(carts);
    }
}
