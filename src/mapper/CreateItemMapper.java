package mapper;

import dto.CreateItemDto;
import entity.Item;

public class CreateItemMapper implements Mapper<CreateItemDto, Item> {

    private static final CreateItemMapper INSTANCE = new CreateItemMapper();

    @Override
    public Item mapFrom(CreateItemDto object) {
        return Item.builder()
            .productId(Integer.valueOf(object.getProduct_id()))
            .count(Integer.valueOf(object.getCount()))
            .build();
    }

    public static CreateItemMapper getInstance() {
        return INSTANCE;
    }
}
