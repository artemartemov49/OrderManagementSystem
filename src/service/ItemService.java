package service;

import dao.ItemDao;
import dao.OrderDao;
import dto.CreateItemDto;
import entity.Item;
import entity.Order;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class ItemService {

    private static final ItemService INSTANCE = new ItemService();
    private final ItemDao itemDao = ItemDao.getInstance();
    private final OrderDao orderDao = OrderDao.getInstance();

    public void create(Order order) {
        order.getItemList().forEach(item -> {
            item.setOrderId(order.getId());
            itemDao.create(item);
        });
    }

    public void updateItems(List<Item> items, Long orderId) {
        itemDao.deleteOrderItems(orderId);
        items.forEach(item -> {
            var totalSum = itemDao.addItem(orderId, item);
            orderDao.updateTotalSum(orderId, totalSum);
        });
    }

    public List<CreateItemDto> createItemDtoList(String[] productIds, HttpServletRequest req) {
        List<CreateItemDto> itemDtos = new ArrayList<>();

        if (productIds != null) {
            for (String productId : productIds) {
                var count = req.getParameter(productId);
                if (count != null) {
                    itemDtos.add(
                        CreateItemDto.builder()
                            .product_id(productId)
                            .count(count)
                            .build());
                }
            }
        }
        return itemDtos;
    }

    public static ItemService getInstance() {
        return INSTANCE;
    }
}
