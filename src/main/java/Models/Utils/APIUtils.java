package Models.Utils;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class APIUtils {

    private static RestTemplate rt = new RestTemplate();

    public static ResponseEntity get(String URL)
    {
        ResponseEntity<String> responseEntity;
        try{
            responseEntity = rt.exchange(URL, HttpMethod.GET, null, String.class);
            return responseEntity;
        }
        catch(HttpClientErrorException.NotFound e)
        {
            responseEntity = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
            return responseEntity;
        }
    }

    public static  ResponseEntity post(String URL)
    {
        ResponseEntity<String> responseEntity = rt.postForEntity(URL, null, String.class);
        return responseEntity;
    }
}
