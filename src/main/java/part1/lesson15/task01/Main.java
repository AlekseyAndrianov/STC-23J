package part1.lesson15.task01;

import part1.lesson15.task01.domain.Customer;
import part1.lesson15.task01.domain.Deal;
import part1.lesson15.task01.domain.Product;
import part1.lesson15.task01.domain.Shop;
import part1.lesson15.task01.repository.CustomerRepository;
import part1.lesson15.task01.repository.ProductRepository;
import part1.lesson15.task01.repository.ShopRepository;
import part1.lesson15.task01.repository.Utils;
import part1.lesson15.task01.service.ShopService;

import java.util.List;
import java.util.UUID;

public class Main {

    private static final ShopService shopService = new ShopService();
    private static final UUID productID = UUID.fromString("d8095462-c062-45a4-8724-3a7200e3e977");

    public static void main(String[] args) {

        Utils.renewDB();

        ShopRepository shopRepository = new ShopRepository();
        CustomerRepository customerRepository = new CustomerRepository();
        ProductRepository productRepository = new ProductRepository();

        Shop shop = shopRepository.getDistinct();
        Customer customer = customerRepository.getDistinct();
        Product product = Product.builder()
                .article(productID)
                .type("New Type")
                .price(Math.random() * 1000)
                .shop(shop)
                .build();
        productRepository.create(product);

        shopService.makeDeal(product, customer);
        List<Deal> deals = shopService.getAllDealsInfo();

        deals.stream().forEach(System.out::println);

    }
}
