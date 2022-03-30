package util;

import java.io.IOException;
import java.util.Properties;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PropertiesUtil {

    private static final String APPLICATION_NAME = "application.properties";
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (var inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(APPLICATION_NAME)) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

}
