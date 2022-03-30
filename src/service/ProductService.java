package service;

import dao.ProductDao;
import dto.CreateProductDto;
import exception.ValidationException;
import lombok.SneakyThrows;
import mapper.CreateProductMapper;
import validator.CreateProductValidator;

public class ProductService {

    private static final ProductService INSTANCE = new ProductService();
    private final CreateProductValidator createProductValidator = CreateProductValidator.getInstance();
    private final CreateProductMapper createProductMapper = CreateProductMapper.getInstance();
    private final ProductDao productDao = ProductDao.getInstance();

    @SneakyThrows
    public void create(CreateProductDto productDto) {
        var validationResult = createProductValidator.isValid(productDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var product = createProductMapper.mapFrom(productDto);
        productDao.create(product);
    }

    public static ProductService getInstance() {
        return INSTANCE;
    }
}
