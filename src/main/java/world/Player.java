package world;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import lombok.Data;
import lombok.EqualsAndHashCode;
import server.Constants;
import server.Control;

@EqualsAndHashCode(callSuper = true)
@Data
public class Player extends Template {

  public enum Rank {NOVATO, APRENDIZ, ESCUDERO, CABALLERO, HEROE, INMORTAL, CREADOR};
  public enum Size {S, M, L};
  public enum Gender {M, F};	// per ara afecta a al llenguatge només ?cansad"+p.genero=M ?"o":"a"
  public enum StatType {HEALTH, MANA, STAMINA, STR, CON, DEX, INT, LUCK};

  private String title;
  private Species.Type species;
  private Race race;
  private Gender gender;
  private Size size;
  private Rank rank;
  private Body equipment;
  private Container<Template> inventory;
  private Control control;
  private long birthday, lived;
  private Constants.Estados status;
  private HashMap<StatType, Stat> stats;
  private int armor;
  private int experience, gold, fame;
  private long bank; // per bank no sé si faria falta una tupla per a poder posar la quantitat, interés, si prèstec ...
  private int alignment; // no sé si es pot predefinir ja un rang alhora de crear-lo o es controla a posteriori
  private Player victim;
  private int damage;

  private List<Affect> affects;



  public Player() {
    super();
    inventory = new Container<>(this);
    status = Constants.Estados.NORMAL;
    birthday = System.currentTimeMillis();
    rank = Rank.NOVATO;
    size = Size.M;
    stats = new HashMap<>();
    for (StatType st : StatType.values()) {
      stats.put(st, new Stat());
    }

  }

  public void addInventario(Template obj) {
    inventory.add(obj);
  }

  public void removeInventario(Template obj) {
    inventory.remove(obj);
  }

  public String listInventario() {
    return inventory.list();
  }

  public Template findObjeto(String nombre) {
    return inventory.find(nombre);
  }

  @Override
  public void update() {

  }

  public void setEspecie(Species.Type newSpecies) {
    species = newSpecies;
    equipment = Species.get(newSpecies);
  }

  // Metodos de equipo
  public void addEquipo(EquipmentObj obj) {
    getEquipment().wear(obj);
  }

  public void removeEquipo(EquipmentObj obj) {
    getEquipment().unwear(obj);
  }

  public String listEquipo() {
    return getEquipment().listEquipment();
  }

  public Optional<EquipmentObj> findEquipo(String nombre) {
    return getEquipment().findEquipment(nombre);
  }

  public void addStat(StatType key, int value) {
    Stat stat = stats.get(key);

    if (stat == null) return;

    if (stat.checkDelta(value)) {
      stat.add(value);
    }
  }

  public void setCurrent(StatType key, int value) {
    Stat stat = stats.get(key);

    if (stat == null) return;

    if (stat.checkValue(value)) {
      stat.setValue(value);
    }
  }

  public int getCurrent(StatType key) {
    return (stats.containsKey(key) ? stats.get(key).getValue() : null);
  }

  public int getMax(StatType key) {
    return (stats.containsKey(key) ? stats.get(key).getMax() : null);
  }

  public void setMax(StatType key, int max) {
    if (stats.containsKey(key)) {
      stats.get(key).setMax(max);
    }
  }
}
