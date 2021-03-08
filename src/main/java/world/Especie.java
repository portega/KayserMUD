package world;

import lombok.Data;

@Data
public abstract class Especie {
  protected Body body;

  public abstract void createBody();

  public Body getBody() {
    if (body == null) createBody();

    return body;
  }
}
