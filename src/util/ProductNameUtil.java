package util;

import dao.ProductDao;
import entity.Product;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductNameUtil {

    private final ProductDao productDao = ProductDao.getInstance();

    public boolean isUniqueName(String name) {
        return productDao.findAll().stream()
            .map(Product::getName)
            .filter(productName -> productName.equals(name))
            .toList().isEmpty();
    }
}
