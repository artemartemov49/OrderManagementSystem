package validator;

import dao.ProductDao;
import dto.CreateItemDto;
import java.util.List;
import util.NumericUtils;

public class CreateItemValidator implements Validator<List<CreateItemDto>> {

    private static final CreateItemValidator INSTANCE = new CreateItemValidator();
    private final ProductDao productDao = ProductDao.getInstance();

    @Override
    public ValidationResult isValid(List<CreateItemDto> itemDtos) {
        var validationResult = new ValidationResult();
        if (itemDtos.isEmpty()) {
            validationResult.add(Error.of("empty.item.list", "Item list is empty"));
        }
        for (CreateItemDto itemDto : itemDtos) {
            if (productDao.findById(Integer.valueOf(itemDto.getProduct_id())).isEmpty()) {
                validationResult.add(Error.of("product.not.found", "Product not found"));
            }
            if (!NumericUtils.isNumeric(itemDto.getCount())) {
                validationResult.add(Error.of("count.empty", "Count is empty"));
            }

        }

        return validationResult;
    }

    public static CreateItemValidator getInstance() {
        return INSTANCE;
    }
}
