package edu.fiuba.algo3.modelo.juego;

import edu.fiuba.algo3.modelo.Enemigos.Enemigo;
import edu.fiuba.algo3.modelo.Observer.Observable;
import edu.fiuba.algo3.modelo.lectorJSON.Mapa;

import java.util.ArrayList;

public class Juego extends Observable {

    Jugador jugador;
    EstadoJuego estadoJuego;
    public Juego() {
        this.jugador = Jugador.getInstance();
        estadoJuego = new Jugando();
    }
    public Juego(Mapa mapa) {
        this.jugador = Jugador.getInstance();
        estadoJuego = new Jugando(mapa);
    }

    public void nuevoEnemigo(Enemigo nuevoEnemigo) {
        emisor.notificarSubscriptores("log", "Se agrega a la partida un nuevo enemigo " + nuevoEnemigo.representacionString());
        estadoJuego = this.estadoJuego.introducirEnemigo(nuevoEnemigo);
    }

    public boolean finalizado() {
        return this.estadoJuego.finalizado();
    }

    public void jugarTurno(int numeroTurno) {
        this.estadoJuego = estadoJuego.jugarTurno(jugador.estaVivo(), numeroTurno);
    }
}