package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LocalDateFormatter {

    public static final String PATTERN = "yyyy-MM-dd";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN);

    public static LocalDate format(String date) {
        return LocalDate.parse(date, FORMATTER);
    }

    public static boolean isValid(String date) {
        try {
            return Optional.ofNullable(date)
                .map(LocalDateFormatter::format)
                .isPresent();
        } catch (DateTimeParseException exception) {
            return false;
        }
    }
}
