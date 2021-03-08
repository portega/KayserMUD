package world;

import static world.BodyPart.Position.LEFT;
import static world.BodyPart.Position.RIGHT;
import static world.BodyPart.Type.ARM;
import static world.BodyPart.Type.CHEST;
import static world.BodyPart.Type.FINGER;
import static world.BodyPart.Type.FOOT;
import static world.BodyPart.Type.HAND;
import static world.BodyPart.Type.HEAD;
import static world.BodyPart.Type.LEG;
import static world.BodyPart.Type.TAIL;

import lombok.Data;

@Data
public class Species {
  protected Body body;

  public enum Type {
      HUMANOID,
      REPTILIAN
  }

  protected Species() {
    body = new Body();
  }
  public static Species get(Type kind) {
    Species newObj = new Species();
    Body newBody = newObj.getBody();
    switch (kind) {
      case REPTILIAN:
        newBody.addPart(TAIL);
      case HUMANOID:
        newBody.addPart(HEAD)
          .addPart(CHEST)
          .addPart(ARM, RIGHT)
          .addPart(ARM, LEFT)
          .addPart(HAND, RIGHT)
          .addPart(HAND, LEFT)
          .addPart(FINGER, RIGHT)
          .addPart(FINGER, LEFT)
          .addPart(LEG, RIGHT)
          .addPart(LEG, LEFT)
          .addPart(FOOT, RIGHT)
          .addPart(FOOT, LEFT);
        break;
    }
    return newObj;
  }
}
