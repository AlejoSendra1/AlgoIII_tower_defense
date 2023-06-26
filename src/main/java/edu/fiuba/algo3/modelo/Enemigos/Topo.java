package edu.fiuba.algo3.modelo.Enemigos;

import edu.fiuba.algo3.modelo.Defensas.Ataque;
import edu.fiuba.algo3.modelo.Defensas.Trampa;
import edu.fiuba.algo3.modelo.miscelanea.Coordenada;
import edu.fiuba.algo3.modelo.parcelas.Parcela;

public class Topo extends Enemigo{
    private int movimientosRealizados;

    public Topo(int turno){
        super(1, 0, 1);

        if (turno % 2 == 0){
            this.poderAtaque=2;
        } else {
            this.poderAtaque= 5;
        }

        this.tipoMovimiento = new MovimientoPasarela(this);
    }

    public void cambiarAtaque(){
        if (poderAtaque == 2){
            this.poderAtaque = 5;
        } else {
            this.poderAtaque = 2;
        }
    }

    @Override
    public void actualizarPosicionActual(Parcela parcelaSiguiente) {
        super.actualizarPosicionActual(parcelaSiguiente);
        cambiarAtaque();

        if (movimientosRealizados == 5){
            this.cantidadMovimientos = 2;
        } else if (movimientosRealizados == 10){
            this.cantidadMovimientos = 3;
        }

    }

    public boolean estaEnRango(Coordenada coordenada, int rangoAtaque, Ataque tipoDeDefensa){
        return false;
    }

    public void morir(){
    }

    public String representacionString() {
        return "Topo";
    }
}
