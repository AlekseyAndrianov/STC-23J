package part1.lesson08.task02.entities;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AnotherObject implements Serializable {

    private static final long serialVersionUID = -8828097670209564069L;

    private String name;
    private String locationName;
    private int size;
    private double weight;
    private SomeObject someObject;

}
