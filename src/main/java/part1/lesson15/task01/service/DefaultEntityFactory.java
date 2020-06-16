package part1.lesson15.task01.service;

import part1.lesson15.task01.domain.Customer;
import part1.lesson15.task01.domain.Deal;
import part1.lesson15.task01.domain.Product;
import part1.lesson15.task01.domain.Shop;

import java.util.UUID;

public class DefaultEntityFactory {

    public static Customer getCustomer(){
        return Customer.builder()
                .id(1L)
                .fName("fName")
                .lName("lName")
                .eMail("eMail")
                .phoneNumber("phNumber")
                .build();
    }

    public static Product getProduct(){
        return Product.builder()
                .article(UUID.randomUUID())
                .price(10.0)
                .type("Default type")
                .shop(getShop())
                .build();
    }

    public static Shop getShop(){
        return Shop.builder()
                .id(UUID.randomUUID())
                .city("Moscow")
                .address("Lermontova 1")
                .build();
    }

    public static Deal getDeal(){
        return Deal.builder()
                .id(1L)
                .product(getProduct())
                .shop(getShop())
                .customer(getCustomer())
                .build();
    }
}
