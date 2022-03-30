package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDto {

    Long id;
    Long orderId;
    Integer productId;
    String name;
    Integer price;
    Integer count;
    Integer sum;
}
