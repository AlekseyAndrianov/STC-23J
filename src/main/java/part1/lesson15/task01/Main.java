package part1.lesson15.task01;

import lombok.extern.log4j.Log4j2;
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

@Log4j2
public class Main {

    private static final ShopService shopService = new ShopService();
    private static final UUID productID = UUID.fromString("d8095462-c062-45a4-8724-3a7200e3e977");

    public static void main(String[] args) {

        Utils.renewDB();

        ShopRepository shopRepository = new ShopRepository();
        CustomerRepository customerRepository = new CustomerRepository();
        ProductRepository productRepository = new ProductRepository();

        Shop shop = shopRepository.getDistinct();
        log.info("Fetch shop from repository with id: " + shop.getId());

        Customer customer = customerRepository.getDistinct();
        log.info("Fetch customer from repository with id: " + customer.getId());

        Product product = Product.builder()
                .article(productID)
                .type("New Type")
                .price(Math.random() * 1000)
                .shop(shop)
                .build();
        productRepository.create(product);
        log.info("Push new product to the repository with article: " + product.getArticle());

        shopService.makeDeal(product, customer);
        log.debug("Make deal for product and customer");

        List<Deal> deals = shopService.getAllDealsInfo();

        deals.forEach(log::info);

    }
}
