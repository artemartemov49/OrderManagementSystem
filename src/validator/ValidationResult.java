package validator;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class ValidationResult {

    @Getter
    private final List<Error> errors = new ArrayList<>();

    public void add(Error error) {
        this.errors.add(error);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }
}
