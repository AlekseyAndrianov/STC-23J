package part1.lesson05.task01;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

/**
 * У каждого животного есть уникальный идентификационный номер, кличка, хозяин, вес.
 */
@Data
@ToString
@Builder
public class Animal {

    private UUID id;
    private String name;
    private Person owner;
    private double weight;

}
