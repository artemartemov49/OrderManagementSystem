package dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateProductDto {

    String name;
    String price;
}
