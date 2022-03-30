package validator;

import dto.CreateProductDto;
import service.ProductService;
import util.NumericUtils;
import util.ProductNameUtil;

public class CreateProductValidator implements Validator<CreateProductDto> {

    private static final CreateProductValidator INSTANCE = new CreateProductValidator();
    private final ProductService productService = ProductService.getInstance();

    @Override
    public ValidationResult isValid(CreateProductDto object) {
        var validationResult = new ValidationResult();
        if (object.getName().isEmpty()) {
            validationResult.add(Error.of("invalid.name.empty", "Name is empty"));
        }

        if (!ProductNameUtil.isUniqueName(object.getName())) {
            validationResult.add(Error.of("invalid.name.productAlreadyExist", "Product already exists"));
        }

        if (!NumericUtils.isNumeric(object.getPrice())) {
            validationResult.add(Error.of("invalid.price", "Price is invalid"));
        }

        return validationResult;
    }

    public static CreateProductValidator getInstance() {
        return INSTANCE;
    }
}
