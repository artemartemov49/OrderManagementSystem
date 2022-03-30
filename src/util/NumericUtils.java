package util;

import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;

@UtilityClass
public class NumericUtils {

    public static boolean isNumeric(String numberToParse) {
        String regex = "[0-9]+";

        return Pattern.matches(regex, numberToParse);

    }
}
