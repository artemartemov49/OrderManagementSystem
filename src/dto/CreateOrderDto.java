package dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class CreateOrderDto {
    String userFIO;
    List<CreateItemDto> itemListDto;
}
