package edu.fiuba.algo3.modelo.juego;

import edu.fiuba.algo3.modelo.Defensas.Defensa;
import edu.fiuba.algo3.modelo.Enemigos.Enemigo;
import edu.fiuba.algo3.modelo.miscelanea.Coordenada;
import edu.fiuba.algo3.modelo.parcelas.Parcela;
import edu.fiuba.algo3.vista.VistaSprays;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

public class Juego extends Observable {
    private static final Juego INSTANCE = new Juego();
    Jugador jugador;
    EstadoJuego estadoJuego;
    // Se me ocurre que reciba la lista de observers para las entidades (en un principio será solo para sprays)
    // ArrayList<Observer> observersParaEntidades;
    VistaSprays vistaSprays;
    Mapa mapa;
    Observer observerParaDefensas;

    private Juego() {
        super();
        this.jugador = new Jugador();
        this.mapa = new Mapa();
        estadoJuego = new Jugando();
    }

    public static Juego getInstance() {
        return INSTANCE;
    }

    public void notificar(String tipo) {
        this.notifyObservers(tipo);
        jugador.notifyObservers();
        this.estadoJuego.notificar();
    }

    public void cargarObserverParaDefensas(Observer observer){
        this.observerParaDefensas = observer;
    }

    public void cargarObserverParaJugador(Observer observer){
        jugador.addObserver(observer);
    }
    public void daniarAlJugador(int unNumero){
        jugador.recibirDanio(unNumero);
    }

    public void comprarDefensa(String unaDefensa, Coordenada coordenada) {
        Defensa nuevaDefensa = jugador.comprar(unaDefensa);
        nuevaDefensa.asignarPosicion(coordenada);
        nuevaDefensa.addObserver( observerParaDefensas);
        estadoJuego.introducirDefensa(nuevaDefensa);
        setChanged();
        mapa.ver(coordenada).construirDefensa();
    }

    public boolean esInicioOFinal(Parcela pasarela) {return mapa.esInicioOFinal(pasarela); }

    public void nuevoEnemigo(Enemigo nuevoEnemigo) {
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

    //Esto claramente esta mal, lo pongo para el observer, deberia devolver informacion relevante sobre el estado del juego pero sin
    //dar a conocer el estado total
    public EstadoJuego obtenerNuevoEstado(){
        return this.estadoJuego;
    }

    public void recompensarJugador(int unNumero) {
        jugador.recompensar(unNumero);
    }

    public void descontarCreditosAlJugador(Credito cantidadACobrar) {
        jugador.descontarCreditos(cantidadACobrar);
    }

    public ArrayList<String> verificarConstruccionesPosibles() {
        return jugador.verificarConstruccionesPosibles();
    }

    public void setNombreDelJugador(String unString) {
        jugador.setNombre(unString);
    }

    public void agregarARachaDeHormigas() {
        jugador.agregarARachaDeHormigas();
    }

    public String getNombreDelJugador() {
        return jugador.getNombre();
    }

    public int getVidaDelJugador() {
        return jugador.getVida();
    }

    public boolean jugadorVivo() {
        return jugador.estaVivo();
    }

    public void comprar(String nombreDeLaDefensa) {
        jugador.comprar(nombreDeLaDefensa);
    }

    public void reestablecerJuego(){
        jugador = new Jugador();
        estadoJuego = new Jugando();
    }

    public void descontarCreditos(Credito creditoADescontar) {
        jugador.descontarCreditos(creditoADescontar);
    }

    public void setMapa(Mapa mapa){
        this.mapa = mapa;
        estadoJuego = new Jugando(mapa);
    }

    public void setOleadasDelNivel(LinkedList<ArrayList<Enemigo>> enemigos){
        mapa.cargarOleadas(enemigos);
    }

    public void destruirDefensaMasAntigua() {
        estadoJuego.destruirDefensaMasAntigua();
    }

    public int getCreditosDelJugador() { return jugador.getCreditos(); }

    public Parcela verParcelaEn(Coordenada coordenada) { return mapa.ver(coordenada); }
}