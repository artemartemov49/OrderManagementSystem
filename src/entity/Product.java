package entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {

    Integer id;
    String name;
    Integer price;
}
