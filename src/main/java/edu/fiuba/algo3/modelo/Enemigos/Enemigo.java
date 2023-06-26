package edu.fiuba.algo3.modelo.Enemigos;
import edu.fiuba.algo3.modelo.Defensas.TipoDeDefensa;
import edu.fiuba.algo3.modelo.Excepciones.PasarelaInexistente;
import edu.fiuba.algo3.modelo.juego.Jugador;
import edu.fiuba.algo3.modelo.lectorJSON.Mapa;
import edu.fiuba.algo3.modelo.miscelanea.Coordenada;
import edu.fiuba.algo3.modelo.parcelas.Parcela;
import edu.fiuba.algo3.modelo.miscelanea.Vida;

import java.util.ArrayList;

public abstract class Enemigo extends Sprayable {
    protected Vida vida;
    protected int cantidadMovimientos;
    protected int poderAtaque;

    protected Efecto efectoEnemigo;
    protected Movimiento tipoMovimiento;

    public Enemigo( int puntosVida, int ataque, int cantidadMovimientos){
        super();
        this.vida = new Vida(puntosVida);
        this.poderAtaque = ataque;
        this.efectoEnemigo = new Ninguno();
        this.cantidadMovimientos = cantidadMovimientos;
    }
    public boolean estaVivo() {
        return vida.sigueVivo();
    }

    public void establecerInicioYMeta(Parcela inicial, Parcela pFinal){
        this.tipoMovimiento.actualizarPosicion(inicial, pFinal);
    }

    public void recibirDanio(int danio){
        this.vida.quitarVida(danio);

        if (!vida.sigueVivo()) {
            this.morir();
        }
    }

    public void actualizarPosicionActual(Parcela parcelaSiguiente) {
        this.tipoMovimiento.actualizarPosicion(parcelaSiguiente);
    }

    public void daniarJugador() {
        //this.emisor.notificarSubscriptores("log",this.representacionString() + " causo daño al jugador");

        if (this.estaVivo()){
            Jugador.getInstance().recibirDanio(this.poderAtaque);
        }

        this.vida = new Vida(0);
    }

    public abstract void morir();

    public abstract String representacionString();

    public void avanzar(Mapa mapa) throws PasarelaInexistente{
        this.efectoEnemigo = this.efectoEnemigo.avanzar(this.cantidadMovimientos, this.tipoMovimiento, mapa);
    }

    public boolean estaEnRango(Coordenada posicion, int distancia, TipoDeDefensa tipoDeDefensa){
        return this.tipoMovimiento.estaEnRango(posicion, distancia);
    }

    public String represtacionUbicacion(){
        return this.tipoMovimiento.representarUbicacion();
    }

    public void setEfectoEnemigo(Efecto nuevoEfecto){
        this.efectoEnemigo = nuevoEfecto;
    }

    @Override
    public ArrayList<String> ObtenerSprayIDYPosicion() {
        ArrayList<String> datos = new ArrayList<>();

        if(this.estaVivo()) {
            datos.add(this.representacionString());
            datos.add(this.represtacionUbicacion());
        }

        return datos;
    }
}