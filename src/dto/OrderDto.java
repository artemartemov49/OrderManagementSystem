package dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDto {

    Long id;
    LocalDate date;
    String clientFIO;
    Integer totalSum;
    List<ItemDto> itemDtos;
}
