package part1.lesson15.task01.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Deal {

    private Long id;
    private Customer customer;
    private Product product;
    private Shop shop;
}
