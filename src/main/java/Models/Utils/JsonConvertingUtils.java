package Models.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConvertingUtils {

    private static ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static <T> T convertJsonToObjectFromString(Class<T> cl, String source)
    {
        try {
            return  mapper.readValue(source, cl);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
