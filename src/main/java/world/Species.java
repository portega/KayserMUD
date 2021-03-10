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

public class Species {

  public static Body humanoid() {
    Body newBody = new Body();
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

    return newBody;
  }

  public static Body reptilian() {
    return humanoid().addPart(TAIL);
  }

  public static Body get(Type type) {
    if (type == Type.REPTILIAN) {
      return reptilian();
    }
    return humanoid();
  }

  public enum Type {
    HUMANOID,
    REPTILIAN
  }
}
