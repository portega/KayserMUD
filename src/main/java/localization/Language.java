package localization;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URL;
import utils.Utils;

public class Language {
  private String locale;
  private JsonNode properties;

  public Language(String locale) {
    this.locale = locale;
    loadLocale();
  }

  private void loadLocale() {
    properties = Utils.loadJSON(this.getClass(), "locales/" + this.locale + ".json");
  }

  public String get(String key) {
    return properties.get(key).asText();
  }

  public String getLocale() {
    return locale;
  }

  public void setLocale(String locale) {
    this.locale = locale;
  }
}
