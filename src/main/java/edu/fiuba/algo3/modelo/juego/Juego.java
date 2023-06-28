package edu.fiuba.algo3.modelo.juego;

import edu.fiuba.algo3.modelo.Defensas.Defensa;
import edu.fiuba.algo3.modelo.Enemigos.Enemigo;
import edu.fiuba.algo3.modelo.lectorJSON.Mapa;
import edu.fiuba.algo3.modelo.miscelanea.Coordenada;
import edu.fiuba.algo3.modelo.parcelas.Parcela;
import edu.fiuba.algo3.vista.VistaSprays;

import java.util.Observable;

public class Juego extends Observable {

    Jugador jugador;
    EstadoJuego estadoJuego;
    // Se me ocurre que reciba la lista de observers para las entidades (en un principio será solo para sprays)
    // ArrayList<Observer> observersParaEntidades;
    VistaSprays vistaSprays;

    Mapa mapa;

    public Juego() {
        this.jugador = Jugador.getInstance();
        estadoJuego = new Jugando();
    }

    public Juego(Mapa mapa, VistaSprays vista) {
        this.jugador = Jugador.getInstance();

        estadoJuego = new Jugando(mapa);
        this.mapa = mapa;

        // De este modo sería correcto aunque falte que sea una lista de observers y no un tipo concreto,
        // con esta forma se puede lograr que la vista se comunique con la ventana
        this.vistaSprays = vista;
    }

    public void notificar() {
        this.notify();
        this.estadoJuego.notificar();
    }


    public void comprarDefensa(String unaDefensa, Coordenada coordenada) {
       Defensa nuevaDefensa = Jugador.getInstance().comprar(unaDefensa);
       nuevaDefensa.asignarPosicion(coordenada);
       nuevaDefensa.addObserver(vistaSprays);
       mapa.ver(coordenada).construirDefensa();
       estadoJuego.introducirDefensa(nuevaDefensa);
       System.out.println(String.format("Se ha agregado una Defensa %s en %s", unaDefensa, coordenada.representacionString()));
       setChanged();

       //nuevaDefensa.agregarSubscriptor(this.logger);
       //this.emisor.notificarSubscriptores("log", "Se agrega al juego una nueva defensa: " + unaDefensa + " en " + coordenada.representacionString());
    }

    public void nuevoEnemigo(Enemigo nuevoEnemigo) {
        nuevoEnemigo.addObserver(vistaSprays);
        estadoJuego = this.estadoJuego.introducirEnemigo(nuevoEnemigo);
        setChanged();

        //nuevoEnemigo.agregarSubscriptor(this.logger);
        //this.emisor.notificarSubscriptores("log", "Se agrega a la partida un nuevo enemigo " + nuevoEnemigo.representacionString());
    }

    public boolean finalizado() {
        return this.estadoJuego.finalizado();
    }

    public void jugarTurno(int numeroTurno) {
        this.estadoJuego = estadoJuego.jugarTurno(jugador.estaVivo(), numeroTurno);
        setChanged();
    }

    public Parcela verParcelaEn(Coordenada coordenada) { return mapa.ver(coordenada);}

    //Esto claramente esta mal, lo pongo para el observer, deberia devolver informacion relevante sobre el estado del juego pero sin
    //dar a conocer el estado total
    public EstadoJuego obtenerNuevoEstado(){
        return this.estadoJuego;
    }
}
