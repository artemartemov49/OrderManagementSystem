package validator;

import dto.CreateOrderDto;

public class CreateOrderValidator implements Validator<CreateOrderDto> {

    private static final CreateOrderValidator INSTANCE = new CreateOrderValidator();
    private final CreateItemValidator createItemValidator = CreateItemValidator.getInstance();

    public static CreateOrderValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(CreateOrderDto object) {
        var validationResult = new ValidationResult();
        if (object.getUserFIO().isEmpty()) {
            validationResult.add(Error.of("empty.fio", "FIO is empty"));
        }
        if (object.getItemListDto().isEmpty()) {
            validationResult.add(Error.of("empty.item.list", "Item list is empty"));

        }
        var createItemValidator = this.createItemValidator.isValid(object.getItemListDto());
        createItemValidator.getErrors().forEach(validationResult::add);
        return validationResult;
    }
}
