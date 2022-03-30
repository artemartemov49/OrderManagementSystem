package mapper;

import dao.ProductDao;
import dto.CreateOrderDto;
import entity.Order;

public class CreateOrderMapper implements Mapper<CreateOrderDto, Order> {

    private static final CreateOrderMapper INSTANCE = new CreateOrderMapper();
    private final CreateItemMapper createItemMapper = CreateItemMapper.getInstance();
    private final ProductDao productDao = ProductDao.getInstance();

    @Override
    public Order mapFrom(CreateOrderDto object) {
        var items = object.getItemListDto().stream()
            .map(createItemMapper::mapFrom)
            .toList();

        return Order.builder()
            .customerFIO(object.getUserFIO())
            .itemList(items)
            .totalSum(
                items.stream()
                    .mapToInt(item -> item.getCount() * productDao.findById(item.getProductId()).get().getPrice())
                    .sum()
            )
            .build();
    }

    public static CreateOrderMapper getInstance() {
        return INSTANCE;
    }
}
