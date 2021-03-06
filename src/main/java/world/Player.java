package world;

import java.util.Optional;
import lombok.Data;
import server.Constants;
import server.Control;

@Data
public class Player extends Template {

  private Race raza;
  private Gender sexo;
  private int gold;
  private int nivel;
  //private Container equipo;
  private Body equipo;
  private Container inventario;
  private Control control;
  private int vida, maxVida, mana, maxMana, move, maxMove;
  private Constants.Estados estado;
  private Player victim;
  private int damm;
  private long fecha_nacimiento;

  public Player() {
    inventario = new Container(this);
    equipo = new Body();
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

  // Metodos de equipo
  public void addEquipo(EquipmentObj obj) {
    equipo.wear(obj);
  }

  public void removeEquipo(EquipmentObj obj) {
    equipo.unwear(obj);
  }

  public String listEquipo() {
    return equipo.listEquipment();
  }

  public Optional<EquipmentObj> findEquipo(String nombre) {
    return equipo.findEquipment(nombre);
  }
}
