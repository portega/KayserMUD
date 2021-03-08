package world;

import static world.BodyPart.Position.CENTER;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Data;
import server.Constants;
import server.Server;
import world.BodyPart.Position;
import world.BodyPart.Type;

@Data
public class Body {
  private EnumMap<Type, List<BodyPart>> parts;

  public Body() {
    parts = new EnumMap<>(Type.class);
  }

  public boolean isWearable(EquipmentObj item) {
    return parts.get(item.getType()) != null;
  }

  public boolean wear(EquipmentObj newItem) {
    BodyPart.Type where = newItem.getType();

    if (!isWearable(newItem)) {
      return false;
    }
    List<BodyPart> possiblePlaces = parts.get(where);

    Optional<BodyPart> candidate = possiblePlaces.stream().filter(p -> p.isFree() == true).findFirst();
    if (!candidate.isPresent()) {
      candidate = possiblePlaces.stream().filter(p -> p.isDisabled() == false).findFirst();
      // TODO: comparar properties en EquipObj y desvestir el peor
    }

    if (candidate.isPresent()) {
      candidate.get().setWearing(newItem);
      return true;
    }
    return false;
  }

  public boolean unwear(EquipmentObj item) {
    for (BodyPart bp : parts.get(item.getType())) {
      if (bp.isWearing(item)) {
        bp.unwear();
        return true;
      }
    }
    return false;
  }

  public List<EquipmentObj> getEquipment() {
    return parts.values().stream().flatMap(x -> x.stream()).filter(bp -> bp.getWearing()!= null)
        .map(bp -> bp.getWearing()).collect(Collectors.toList());
  }

  public List<BodyPart> listParts() {
    return parts.values().stream().flatMap(x -> x.stream()).collect(Collectors.toList());
  }

  public String listEquipment() {
    StringBuilder txt = new StringBuilder();
    for (BodyPart bp : listParts()) {
      txt.append(bp.toString()).append(Constants.EOL);
    }

    return txt.toString();
  }

  public Optional<EquipmentObj> findEquipment(String nombre) {
    for (EquipmentObj obj : getEquipment()) {
      if (obj.getNombre().equals(nombre)) {
        return Optional.of(obj);
      }
    }
    return Optional.empty();
  }

  public void add(BodyPart newPart) {
    parts.putIfAbsent(newPart.getType(), new ArrayList<>());
    parts.get(newPart.getType()).add(newPart);
  }

  public Body addPart(BodyPart.Type tipo) {
    return addPart(tipo, CENTER);
  }
    public Body addPart(BodyPart.Type tipo, BodyPart.Position posicion) {
    BodyPart bp = new BodyPart(Server.textosType, Server.textosPosition);
    bp.setPosition(posicion);
    bp.setType(tipo);

    add(bp);

    return this;
  }
}
