package part1.lesson15.task01.domain;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Shop {

    private UUID id;
    private String city;
    private String address;
}
