package world;

import lombok.Data;

@Data
public class EquipmentObj extends Item {
  private BodyPart.Type type;
}
