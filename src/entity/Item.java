package entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Item {

    Long id;
    Long orderId;
    Integer productId;
    Integer count;
    Integer sum;
}
