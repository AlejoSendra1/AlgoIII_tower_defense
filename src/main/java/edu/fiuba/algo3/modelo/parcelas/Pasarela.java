package edu.fiuba.algo3.modelo.parcelas;

import edu.fiuba.algo3.modelo.miscelanea.Coordenada;
import edu.fiuba.algo3.modelo.Defensas.Defensa;
import edu.fiuba.algo3.modelo.Enemigos.Enemigo;

import java.util.ArrayList;

public class Pasarela extends Parcela {
    private TipoPasarela tipo;

    private Pasarela siguientePasarela;

    public Pasarela(Coordenada coordenada, Pasarela siguientePasarela, TipoPasarela tipo) {
        super(coordenada, new NoDisponible());
        this.siguientePasarela = siguientePasarela;
        this.tipo = tipo;
    }

    public Pasarela verSiguiente(){return siguientePasarela;};

    public Pasarela verSiguiente(int cantidadPasos){
        Pasarela pasarelaAux = this;

        if(cantidadPasos >= 0) { // Este caso no deberia pasar
            for(int i = 0; i < cantidadPasos; i++) {
                pasarelaAux = pasarelaAux.verSiguiente();
            }
        }

        return pasarelaAux;
    };

    public Pasarela actualizarPosicion(int desplazamientoEnemigo) {
        Pasarela proximaPasarela = this.verSiguiente(desplazamientoEnemigo);
        return proximaPasarela;
    }

    public boolean estaEnRango(Coordenada otraCoordenada, int distancia) {
        return coordenada.estaEnRango(otraCoordenada, distancia);
    }

    public void construirDefensa(Defensa defensa){}

    public boolean equals(Pasarela pasarela){
        return pasarela.verificarPosicion(this.coordenada);
    }

    public boolean ocupada(){
        return !(construible.puedeConstruir());
    }
}