package part1.lesson08.task01.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AnotherPojoObject implements Serializable {

    private static final long serialVersionUID = 8380702004765930358L;

    private long id;
    private String name;
    private int size;
    private double weight;
    private double length;

}
