package world;

import java.util.Optional;
import lombok.Data;
import lombok.EqualsAndHashCode;
import server.Constants;
import server.Control;

@EqualsAndHashCode(callSuper = true)
@Data
public class Player extends Template {

  private String name;
  private String title;
  public enum rank {NOVATO,APRENDIZ,ESCUDERO,CABALLERO,HEROE,INMORTAL,CREADOR};		// antic nivel, s?ha de fer constants per facilitar l?ús?
  private Species.Type specie;	// definirà quines parts del cos cal fer un rename o algo? he canviat coses que van després :/
  public enum gender {M,F};	// no sé si cal una classe, per ara afecta a al llenguatge només ?cansad"+p.genero=M ?"o":"a"
  private Race race;		// serà una mica com species, donarà modificadors i definirà arbre de skills i spells
  public enum size {PEQUENYO,MEDIANO,GRANDE};		//
  private Body equipment;  // aquesta l'he deixat, traduitn, però encara haig d'aprendre com funciona
  private Container<Template> inventary;
  private Control control;
  private long lived;    // l'edat es calcula per hores jugades normalment
  private Constants.Estados position;  // muerto, durmiendo, descansando, de pie, luchando, encantado ....
          // pq aquesta és constant i les altres les defineixo aquí, ho haig de fer a constant els enums?
  private int health, maxHealth, mana, maxMana, stamina, maxStamina;
  private int strength, constitution, dexterity, intelligence, luck;
  private int armor;
  private int experience, gold, fame;
  private long bank; // per bank no sé si faria falta una tupla per a poder posar la quantitat, interés, si prèstec ...
  private int alignment; // no sé si es pot predefinir ja un rang alhora de crear-lo o es controla a posteriori
  private int hunger, thirst, drunken; // igual que adalt

  private Player victim;
  private int damage;  // és el mal extra que fas per equip, hi haurà una base per raça tamany (gender? :P) etc

  private List<Affect> affectList;  // aquesta és la que tinc més difusa pq passa una mica com a body()
  // public class Affect {
  //    public enum Type {INVIS, OCULTO, FUERZA, VIDA, BORRACHO, PROTEGIDO, PETRIFICADO... };
  //    es barregen tots els tipus?!? els que afecten a stats de la fitxa amb booleans??
  //    private int value; // temps que li queda, serà descomptar cada tick fins a 0, si és -1 permanent
  //    private int time;
  //}
  //
  // falta per definir, no sé com es fa,
  // commandlist (llista de comandes i si activa o no)
  // skilltree (arbre predefinit d'habilitats segons especie, raza, on cada skill tindrà varis atributs)
  // spelltree (igual que l'anterior però depen de la skill conjurar, doublecheck)


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
