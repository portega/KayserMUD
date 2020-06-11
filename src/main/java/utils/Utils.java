package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import java.net.URL;

public class Utils {
  public static JsonNode loadJSON(Class caller, String filePath) {
    JsonNode result = JsonNodeFactory.instance.nullNode();
    try {
      URL path = caller.getClassLoader().getResource(filePath);
      ObjectMapper mapper = new ObjectMapper();
      result = mapper.readTree(path);
    } catch(Exception ex) {
      System.out.println(ex);
    }
    return result;
  }
}
