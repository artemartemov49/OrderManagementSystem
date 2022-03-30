package dao;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

import dto.OrderDto;
import entity.Order;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.SneakyThrows;
import util.ConnectionManager;

public class OrderDao implements Dao<Long, OrderDto> {

    private static final OrderDao INSTANCE = new OrderDao();
    private final ItemDao itemDao = ItemDao.getInstance();

    private static final String UPDATE_NAME = """
        UPDATE orders
        SET customer_fio = ?
        WHERE id = ?;
                            """;
    private static final String UPDATE_TOTAL_SUM = """
        UPDATE orders
        SET total_sum = total_sum + ?
        WHERE id = ?;
                    """;
    private static final String DELETE_ORDER_BY_ID = """
        DELETE FROM orders
        WHERE id = ?;
        """;
    private static final String FIND_BY_ID = """
        SELECT DISTINCT o.id, o.date, o.customer_fio, o.total_sum
                                FROM items i
                                JOIN orders o on o.id = i.order_id
                                WHERE o.id = ?;
                    """;
    private static final String NULLIFY_TOTAL_SUM = """
        UPDATE orders
        SET total_sum = 0
        WHERE id = ?;
                    """;

    private static final String SAVE_SQL = """
        INSERT INTO orders(date, customer_fio, total_sum)
        VALUES ((SELECT now()::date), ?,?);
        """;

    private static final String FIND_ALL = """
                    SELECT DISTINCT o.id, o.date, o.customer_fio, o.total_sum
                    FROM items i
                    JOIN orders o on o.id = i.order_id
                    ORDER BY o.id;
                    
        """;
    private static final String FIND_BY_DATE_INTERVAL = """
        SELECT DISTINCT o.id, o.date, o.customer_fio, o.total_sum
        FROM items i
                 JOIN orders o on o.id = i.order_id
        WHERE o.date BETWEEN ? AND ?
         ORDER BY o.id;
                     """;

    @Override
    @SneakyThrows
    public List<OrderDto> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL)) {
            List<OrderDto> orders = new ArrayList<>();
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(buildEntity(resultSet));
            }
            return orders;
        }
    }

    @Override
    @SneakyThrows
    public Optional<OrderDto> findById(Long id) {
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
    public List<OrderDto> findByDateInterval(LocalDate startDate, LocalDate endDate) {
        List<OrderDto> orderDtos = new ArrayList<>();
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_DATE_INTERVAL)) {
            preparedStatement.setObject(1, startDate);
            preparedStatement.setObject(2, endDate);
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orderDtos.add(buildEntity(resultSet));
            }
            return orderDtos;
        }
    }

    @SneakyThrows
    public Order create(Order entity) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, entity.getCustomerFIO());
            preparedStatement.setObject(2, entity.getTotalSum());

            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();

            entity.setId(generatedKeys.getObject("id", Long.class));
            return entity;
        }
    }

    @SneakyThrows
    public Long updateOrderName(Long orderId, String customerFIO) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_NAME)) {
            preparedStatement.setObject(1, customerFIO);
            preparedStatement.setObject(2, orderId);
            preparedStatement.executeUpdate();
        }
        return orderId;
    }

    @SneakyThrows
    public Long updateTotalSum(Long orderId, Integer value) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_TOTAL_SUM)) {
            preparedStatement.setObject(1, value);
            preparedStatement.setObject(2, orderId);
            preparedStatement.executeUpdate();
        }
        return orderId;
    }

    @SneakyThrows
    public void deleteOrder(Long orderId) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(DELETE_ORDER_BY_ID)) {
            preparedStatement.setObject(1, orderId);
            preparedStatement.executeUpdate();
        }
    }

    @SneakyThrows
    public void nullifyTotalSum(Long orderId) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(NULLIFY_TOTAL_SUM)) {
            preparedStatement.setObject(1, orderId);
            preparedStatement.executeUpdate();
        }
    }

    @SneakyThrows
    private OrderDto buildEntity(ResultSet resultSet) {
        return OrderDto.builder()
            .id(resultSet.getObject("id", Long.class))
            .date(resultSet.getObject("date", Date.class).toLocalDate())
            .clientFIO(resultSet.getObject("customer_fio", String.class))
            .totalSum(resultSet.getObject("total_sum", Integer.class))
            .itemDtos(itemDao.findAllByOrderId(resultSet.getObject("id", Long.class)))
            .build();
    }

    public static OrderDao getInstance() {
        return INSTANCE;
    }
}
