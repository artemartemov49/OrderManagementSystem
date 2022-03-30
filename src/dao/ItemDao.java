package dao;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

import dto.ItemDto;
import entity.Item;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.SneakyThrows;
import util.ConnectionManager;

public class ItemDao implements Dao<Long, ItemDto> {

    private static final ItemDao INSTANCE = new ItemDao();
    private final OrderDao orderDao = OrderDao.getInstance();
    private static final String DELETE_ITEM = """
        DELETE FROM items
        WHERE id = ?;
        """;
    private static final String DELETE_ITEMS_BY_ORDER_ID = """
        DELETE FROM items
        where order_id = ?;
        """;
    private static final String UPDATE_COUNT = """
        UPDATE items
        SET count = ?
        WHERE id = ?;
        """;

    private static final String SAVE_SQL = """
        INSERT INTO items(order_id, product_id, count, sum)
        VALUES (?,?,?,(SELECT price
        FROM product p
        WHERE p.id = ?
        )*?);
        """;
    private static final String FIND_ALL_BY_ORDER_ID = """
        SELECT i.id, o.id, p.id, p.name, p.price, i.count, i.sum
        FROM items i
        JOIN product p on p.id = i.product_id
        JOIN orders o on o.id = i.order_id
        WHERE o.id = ?;
        """;

    private static final String FIND_ALL = """
        SELECT i.id, o.id, p.id, p.name, p.price, i.count, i.sum
        FROM items i
        JOIN product p on p.id = i.product_id
        JOIN orders o on o.id = i.order_id
        """;
    private static final String FIND_BY_ID = """
        SELECT i.id, o.id, p.id, p.name, p.price, i.count, i.sum
        FROM items i
        JOIN product p on p.id = i.product_id
        JOIN orders o on o.id = i.order_id
        WHERE i.id = ?;
                    """;
    private static final String d = """
        select *
        from items
                    """;

    public static ItemDao getInstance() {
        return INSTANCE;
    }

    @Override
    @SneakyThrows
    public List<ItemDto> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL)) {
            List<ItemDto> itemDtos = new ArrayList<>();
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                itemDtos.add(buildEntity(resultSet));
            }
            return itemDtos;
        }
    }

    @SneakyThrows
    public List<ItemDto> findAllByOrderId(Long orderId) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_BY_ORDER_ID)) {
            preparedStatement.setObject(1, orderId);
            List<ItemDto> itemDtos = new ArrayList<>();
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                itemDtos.add(buildEntity(resultSet));
            }
            return itemDtos;
        }
    }

    @SneakyThrows
    public Item create(Item entity) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, RETURN_GENERATED_KEYS)) {

            preparedStatement.setObject(1, entity.getOrderId());
            preparedStatement.setObject(2, entity.getProductId());
            preparedStatement.setObject(3, entity.getCount());
            preparedStatement.setObject(4, entity.getProductId());
            preparedStatement.setObject(5, entity.getCount());

            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();

            entity.setId(generatedKeys.getObject("id", Long.class));
            return entity;
        }
    }

    @SneakyThrows
    public Integer addItem(Long orderId, Item entity) {
        entity.setOrderId(orderId);
        var item = create(entity);
        var itemDto = findById(item.getId()).get();
        return itemDto.getCount() * itemDto.getPrice();

    }

    @SneakyThrows
    public void deleteItem(Long itemId) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(DELETE_ITEM)) {
            var itemDto = findById(itemId).get();
            var totalSum = itemDto.getCount() * itemDto.getPrice();
            orderDao.updateTotalSum(itemDto.getOrderId(), totalSum);

            preparedStatement.setObject(1, itemId);
            preparedStatement.executeUpdate();
        }
    }

    @SneakyThrows
    public void updateCount(Long itemId, Integer count) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_COUNT)) {
            preparedStatement.setObject(1, count);
            preparedStatement.setObject(2, itemId);
            preparedStatement.executeUpdate();
            var itemDto = findById(itemId).get();
            var totalSum = itemDto.getCount() * itemDto.getPrice();
            orderDao.updateTotalSum(itemDto.getOrderId(), totalSum);

        }
    }

    @SneakyThrows
    public void deleteOrderItems(Long orderId) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(DELETE_ITEMS_BY_ORDER_ID)) {
            preparedStatement.setObject(1, orderId);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
    public Optional<ItemDto> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setObject(1, id);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(buildEntity(resultSet));
            }
            return Optional.empty();
        }
    }

    @SneakyThrows
    private ItemDto buildEntity(ResultSet resultSet) {
        return ItemDto.builder()
            .id(resultSet.getObject(1, Long.class))
            .orderId(resultSet.getObject(2, Long.class))
            .productId(resultSet.getObject(3, Integer.class))
            .name(resultSet.getObject("name", String.class))
            .price(resultSet.getObject("price", Integer.class))
            .count(resultSet.getObject("count", Integer.class))
            .sum(resultSet.getObject("sum", Integer.class))
            .build();
    }
}
