package edu.fiuba.algo3.modelo.Enemigos;

import edu.fiuba.algo3.modelo.Defensas.TipoDeDefensa;
import edu.fiuba.algo3.modelo.Defensas.Trampa;
import edu.fiuba.algo3.modelo.miscelanea.Coordenada;

public class Lechuza extends Enemigo{

    public Lechuza(){
        super(5, 0, 5);
        this.tipoMovimiento = new MovimientoCateto(this);
    }

    @Override
    public void recibirDanio(int danio) {
        super.recibirDanio(danio);
        if (vida.obtenerPorcentajeDadoInicial(5) <= 50 ){
            this.tipoMovimiento = new MovimientoHipotenusa(this);
        }
    }

    @Override
    public void daniarJugador() {
        super.daniarJugador();
        //se debe  agregar la destruccion de la torre
    }

    public boolean estaEnRango(Coordenada coordenada, int rangoAtaque, Trampa tipoDeDefensa){
        return false;
    }

    public void morir(){
    }

    public String representacionString() {
        return "Lechuza";
    }

}
