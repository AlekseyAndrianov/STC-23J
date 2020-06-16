package part1.lesson15.task01.service;

import part1.lesson15.task01.domain.Customer;
import part1.lesson15.task01.domain.Deal;
import part1.lesson15.task01.domain.Product;
import part1.lesson15.task01.repository.*;

import java.util.List;

public class ShopService {

    private DealRepository dealRepository;

    public ShopService(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }

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
