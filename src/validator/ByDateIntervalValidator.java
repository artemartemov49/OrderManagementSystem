package validator;

import java.util.List;
import util.LocalDateFormatter;

public class ByDateIntervalValidator implements Validator<List<String>> {

    private static final ByDateIntervalValidator INSTANCE = new ByDateIntervalValidator();
    private static final int REQUIRED_COUNT = 2;

    @Override
    public ValidationResult isValid(List<String> object) {
        var validationResult = new ValidationResult();
        if (object.stream()
            .filter(LocalDateFormatter::isValid)
            .count() < REQUIRED_COUNT) {
            validationResult.add(Error.of("incorrect.date.input", "Your input less than two dates"));
        }

        return validationResult;
    }

    public static ByDateIntervalValidator getInstance() {
        return INSTANCE;
    }
}
