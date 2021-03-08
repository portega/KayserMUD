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

public class Humanoide extends Especie {

  @Override
  public void createBody() {
    body = new Body();

    body
        .addPart(HEAD)
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
  }
}
