package com.onebox.cart.common;

import com.onebox.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
@Slf4j
@Component
@RequiredArgsConstructor()
public class CartCleanupTask {

    public static final int REPETITION_TIME = 600000;  // 10 minutes
    public static final int CHECK_AFTER = 10;
    private final CartRepository cartRepository;

    @Scheduled(fixedRate = REPETITION_TIME)
    public void cleanupCarts() {
        var lastUpdate = LocalDateTime.now().minus(CHECK_AFTER, ChronoUnit.MINUTES);
        log.info("Cleaning carts not updated since " + lastUpdate);
        var carts = cartRepository.findByDateUpdateBefore(lastUpdate);

        if (!carts.isEmpty()) {
            cartRepository.deleteAll(carts);
            log.info("Deleted " + carts.size() + " carts.");
        }
    }
}
