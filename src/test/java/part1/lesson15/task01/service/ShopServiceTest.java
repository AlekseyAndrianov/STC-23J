package part1.lesson15.task01.service;

import org.junit.jupiter.api.Test;
import part1.lesson15.task01.domain.Customer;
import part1.lesson15.task01.domain.Product;
import part1.lesson15.task01.repository.DealRepository;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ShopServiceTest {

    DealRepository dealRepository = mock(DealRepository.class);
    ShopService shopService = spy(new ShopService(dealRepository));

    @Test
    void makeDeal() {
        Product product = DefaultEntityFactory.getProduct();
        Customer customer = DefaultEntityFactory.getCustomer();

        doNothing().when(dealRepository).create(any());

        assertDoesNotThrow(() -> shopService.makeDeal(product, customer));

        verify(dealRepository).create(any());

    }

    @Test
    void getAllDealsInfo() {
        when(dealRepository.getAllDeals()).thenReturn(new ArrayList<>());
        assertNotNull(shopService.getAllDealsInfo());
        verify(dealRepository).getAllDeals();
    }
}