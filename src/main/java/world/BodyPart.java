package world;

import java.util.EnumMap;
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
    FINGER,
    TAIL
  };

  public enum Position {
    LEFT,
    RIGHT,
    FRONT,
    BACK,
    CENTER  // default
  };

  EnumMap<Type, String> textosType;
  EnumMap<Position, String> textosPosition;

  private boolean disabled;
  private Type type;
  private Position position;
  private EquipmentObj wearing;


  public BodyPart(EnumMap<Type, String> textosType,  EnumMap<Position, String> textosPosition) {
    this.textosPosition = textosPosition;
    this.textosType = textosType;
  }

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
    return formatMe(25) + (wearing != null ? wearing.getNombre() : "");
  }

  private String formatMe(int length) {
    String txt = "<" + textosType.get(type) + formatPosition() + "> ";
    txt = String.format("%" + (-length) + "s", txt);
    return txt;
  }

  private String formatPosition() {
    String[] txt = textosPosition.get(position).split("#");
    String result;

    switch (type) {
      case ARM:
      case FOOT:
      case FINGER:
        result = " " +txt[1];
        break;
      case LEG:
      case HAND:
        result = " " + txt[0];
        break;
      default:
        result = "";
        break;
    }

    return result;
  }

}
