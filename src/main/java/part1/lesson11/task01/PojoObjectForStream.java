package part1.lesson11.task01;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class PojoObjectForStream {

    private UUID id;
    private String Name;
    private int size;
}
