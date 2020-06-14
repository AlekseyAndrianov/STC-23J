package part1.lesson15.task01.service;

import part1.lesson15.task01.domain.Customer;
import part1.lesson15.task01.domain.Deal;
import part1.lesson15.task01.domain.Product;
import part1.lesson15.task01.repository.DealRepository;

import java.util.List;
import java.util.UUID;

public class ShopService {

    private final DealRepository dealRepository = new DealRepository();

    public void makeDeal(Product product, Customer customer) {
        Deal deal = Deal.builder()
                .customer(customer)
                .shop(product.getShop())
                .product(product)
                .build();
        dealRepository.create(deal);
    }

    public List<Deal> getAllDealsInfo() {
        return dealRepository.getAllDeals();
    }
}
