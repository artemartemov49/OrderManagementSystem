package mapper;

import dto.CreateProductDto;
import entity.Product;

public class CreateProductMapper implements Mapper<CreateProductDto, Product> {

    private static final CreateProductMapper INSTANCE = new CreateProductMapper();

    @Override
    public Product mapFrom(CreateProductDto object) {
        return Product.builder()
            .name(object.getName())
            .price(Integer.valueOf(object.getPrice()))
            .build();
    }

    public static CreateProductMapper getInstance() {
        return INSTANCE;
    }
}
