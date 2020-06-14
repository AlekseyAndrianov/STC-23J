package part1.lesson15.task01.domain;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Product {

    private UUID article;
    private double price;
    private String type;
    private Shop shop;
}
