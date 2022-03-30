package dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateItemDto {

    String product_id;
    String count;
}
