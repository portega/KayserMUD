package world;

import lombok.Data;
import lombok.NonNull;

@Data
public class BodyPart {

  // KNEE?? FOREARM??
  public enum Type {
    HEAD,
    CHEST,
    ARM,
    HAND,
    LEG,
    FOOT,
    FINGER
  };

  public enum Position {
    LEFT,
    RIGHT,
    FRONT,
    BACK,
    CENTER  // default
  };

  private boolean disabled;
  private Type type;
  private Position position;
  private EquipmentObj wearing;

  public boolean isFree() {
    return (disabled == false && wearing == null);
  }

  public boolean isWearing(EquipmentObj item) {
    return (wearing != null && wearing.equals(item));
  }

  public void unwear() {
    wearing = null;
  }

  @Override
  public String toString() {
    return "<"+type+ (position != Position.CENTER ? " "+position : "") + "> "+(wearing != null ? wearing.getNombre() : "");
  }
}
