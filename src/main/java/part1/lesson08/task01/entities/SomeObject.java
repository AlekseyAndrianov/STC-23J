package part1.lesson08.task01.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SomeObject implements Serializable {

    private static final long serialVersionUID = -5744892089176548042L;

    private long ind;
    private String name;
    private int size;
    private double weight;
    private AnotherPojoObject anotherPojoObject;

}
