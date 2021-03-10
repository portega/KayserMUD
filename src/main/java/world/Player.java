package world;

import java.util.Optional;
import lombok.Data;
import lombok.EqualsAndHashCode;
import server.Constants;
import server.Control;

@EqualsAndHashCode(callSuper = true)
@Data
public class Player extends Template {

  private Race raza;
  private Gender sexo;
  private int gold;
  private int nivel;
  private Body equipo;
  private Species.Type especie;
  private Container<Template> inventario;
  private Control control;
  private int vida, maxVida, mana, maxMana, move, maxMove;
  private Constants.Estados estado;
  private Player victim;
  private int damm;
  private long fecha_nacimiento;

  public Player() {
    super();
    inventario = new Container<>(this);
    estado = Constants.Estados.NORMAL;
    fecha_nacimiento = System.currentTimeMillis();
  }

  public int getEdad() {
    long ahora = System.currentTimeMillis();
    return (int) (fecha_nacimiento - ahora) / Constants.HORAS;
  }

  public void addInventario(Template obj) {
    inventario.add(obj);
  }

  public void removeInventario(Template obj) {
    inventario.remove(obj);
  }

  public String listInventario() {
    return inventario.list();
  }

  public Template findObjeto(String nombre) {
    return inventario.find(nombre);
  }

  @Override
  public void update() {
    // TODO Auto-generated method stub
    switch (estado) {
      case FIGHT:

        break;
    }
  }

  public void setEspecie(Species.Type species) {
    especie = species;
    equipo = Species.get(species);
  }

  // Metodos de equipo
  public void addEquipo(EquipmentObj obj) {
    getEquipo().wear(obj);
  }

  public void removeEquipo(EquipmentObj obj) {
    getEquipo().unwear(obj);
  }

  public String listEquipo() {
    return getEquipo().listEquipment();
  }

  public Optional<EquipmentObj> findEquipo(String nombre) {
    return getEquipo().findEquipment(nombre);
  }
}
