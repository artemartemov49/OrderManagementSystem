package validator;

public class UpdateNameOrderValidator implements Validator<String> {

    private static final UpdateNameOrderValidator INSTANCE = new UpdateNameOrderValidator();

    @Override
    public ValidationResult isValid(String object) {
        var validationResult = new ValidationResult();
        if (object.isEmpty()) {
            validationResult.add(Error.of("name.empty", "Name is empty"));
        }
        return validationResult;
    }

    public static UpdateNameOrderValidator getInstance() {
        return INSTANCE;
    }
}
