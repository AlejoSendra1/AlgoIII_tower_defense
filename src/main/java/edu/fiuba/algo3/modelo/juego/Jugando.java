package edu.fiuba.algo3.modelo.juego;

import edu.fiuba.algo3.modelo.Enemigos.Enemigo;
import edu.fiuba.algo3.modelo.Excepciones.PasarelaInexistente;
import edu.fiuba.algo3.modelo.lectorJSON.Mapa;

import java.util.ArrayList;

public class Jugando implements EstadoJuego {

    ArrayList<Enemigo> enemigos;
    Mapa mapa;
    public Jugando(){
        this.enemigos = new ArrayList<>();
    }

    public Jugando(Mapa mapa) {
        this.mapa = mapa;
        enemigos = new ArrayList<>();
    }
    public EstadoJuego introducirEnemigo(Enemigo enemigo) {
        enemigos.add(enemigo);
        return this;
    }

    public boolean finalizado() {
        return false;
    }

    private EstadoJuego actualizarSegunEstadoDeJugador(boolean jugadorVivo) {
        if (!jugadorVivo)
            return new Perdido();
        else if (enemigos.stream().filter(enemigo -> enemigo.estaVivo()).count() == 0)
            return new Ganado();
        else
            return this;
    }
    public EstadoJuego jugarTurno(boolean jugadorVivo, int numeroTurno){
        mapa.agregarEnemigosDelTurno(this.enemigos);
        enemigos.forEach(enemigo -> {
            try {
                enemigo.avanzar();
            } catch (PasarelaInexistente e) {
                throw new RuntimeException(e);
            }
        });
        return actualizarSegunEstadoDeJugador(jugadorVivo);
    }
}