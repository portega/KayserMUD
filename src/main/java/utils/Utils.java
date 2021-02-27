package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;

public class Utils {
  public static JsonNode loadJSON(Class caller, String filePath) {
    JsonNode result = null;
    try {
      InputStream is = caller.getClassLoader().getResourceAsStream(filePath);
      ObjectMapper mapper = new ObjectMapper();
      result = mapper.readTree(is);
    } catch(Exception ex) {
      System.out.println(ex);
    }
    return result;
  }
}
