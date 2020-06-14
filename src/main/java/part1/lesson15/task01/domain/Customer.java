package part1.lesson15.task01.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {

    private long id;
    private String fName;
    private String lName;
    private String phoneNumber;
    private String eMail;
}
