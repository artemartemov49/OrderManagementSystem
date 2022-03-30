package entity;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {

    Long id;
    LocalDate date;
    String customerFIO;
    Integer totalSum;
    List<Item> itemList;
}
