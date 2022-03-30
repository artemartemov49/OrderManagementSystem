package dao;

import entity.Product;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.SneakyThrows;
import util.ConnectionManager;

public class ProductDao implements Dao<Integer, Product> {

    private static final ProductDao INSTANCE = new ProductDao();
    private static final String CREATE_SQL = """
        INSERT INTO product(name, price)
        VALUES (?,?);
                    """;
    private static final String FIND_ALL = """
        SELECT id, name, price
        FROM product;
        """;
    private static final String FIND_BY_ID = """
        SELECT id, name, price
        FROM product
        WHERE  id=?;
        """;

    @Override
    @SneakyThrows
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL)) {
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                products.add(buildEntity(resultSet));
            }
            return products;
        }
    }

    @SneakyThrows
    public void create(Product entity) {

        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(CREATE_SQL)) {
            preparedStatement.setObject(1, entity.getName());
            preparedStatement.setObject(2, entity.getPrice());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
    public Optional<Product> findById(Integer id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setObject(1, id);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(buildEntity(resultSet));
            } else {
                return Optional.empty();
            }
        }
    }

    @SneakyThrows
    private Product buildEntity(ResultSet resultSet) {
        return Product.builder()
            .id(resultSet.getObject("id", Integer.class))
            .name(resultSet.getObject("name", String.class))
            .price(resultSet.getObject("price", Integer.class))
            .build();
    }

    public static ProductDao getInstance() {
        return INSTANCE;
    }
}

