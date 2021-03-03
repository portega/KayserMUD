package server;

public class Constants {

    /** Direcciones del movimiento */
    public static enum Direcciones {

        NORTE, ESTE, OESTE, SUR, ARRIBA, ABAJO
    };

    /** Estados posibles para una puerta (abrir o cruzar) */
    public static enum PuertaEstado {

        ABIERTA, CERRADA, BLOQUEADA
    };

    /** Tipo de puerta */
    public static enum PuertaTipo {

        NO_PASS, INVIS, HIDDEN, PORTAL
    };

    /** Estados de un Player */
    public static enum Estados {

        NORMAL, FIGHT, REST, SLEEP, SICK, POISONED, CHARMED
    };

    /** Tipos de mobprog */
    public static enum MobProgs {

        ACT, SPEECH, RAND, FIGHT, GREET, ALL_GREET, ENTRY, GIVE, BRIBE, DEATH, HITPRCNT
    };
    /** Constante de utilidad para tratar con milisegundos */
    public static final int SEGUNDOS = 1000, MINUTOS = 60 * SEGUNDOS, HORAS = 60 * MINUTOS;
    /** Intervalo por defecto de espera para el script de un mob */
    public static final int DEFAULT_INTERVALO = 30 * SEGUNDOS;

    /** Salto de l&iacute;nea **/
    public final static String EOL = "\n\r";
    
    public final static String VERDE = "\u001b[32m";
    public final static String VERDE_256 = "\u001b[38;5;046m";
    public final static String RESET = "\u001b[0m";
}
